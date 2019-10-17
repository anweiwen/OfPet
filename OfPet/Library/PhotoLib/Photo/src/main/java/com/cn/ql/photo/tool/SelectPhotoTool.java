package com.cn.ql.photo.tool;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.cn.ql.photo.tool.compression.CompressionPredicate;
import com.cn.ql.photo.tool.compression.CompressionTool;
import com.cn.ql.photo.tool.compression.OnCompressListener;

import java.io.File;

/**
 * Created by AXW on 2018/4/13.
 * 注：
 */

public class SelectPhotoTool {

    public static final int TAKE_PHOTO = 0x1001; // 拍照
    public static final int SELECT_PHOTO = 0x1002; // 选择图片
    public static final int ZOOM_PHOTO = 0x1003; // 剪切图片

    // 上下文
    private Activity act;
    // 拍照或剪切后图片的存放位置(参考file_provider_paths.xml中的路径)
    private String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
    //FileProvider的主机名：一般是包名+".fileprovider"，严格上是build.gradle中defaultConfig{}中applicationId对应的值+".fileprovider"
    private String AUTHORITIES = "packageName" + ".fileprovider";
    //是否要裁剪（默认不裁剪）
    private boolean isCrop = false;

    private Uri mOutputUri;
    private File mInputFile;
    private File mOutputFile;

    //剪裁图片宽高比
    private int mAspectX = 1;
    private int mAspectY = 1;
    //剪裁图片大小
    private int mOutputX = 500;
    private int mOutputY = 500;


    public void Init(Activity act, boolean isCrop, PhotoSelectListener listener){
        this.act = act;
        this.listener = listener;
        this.isCrop = isCrop;
        AUTHORITIES = act.getPackageName() + ".fileprovider";
        imgPath = generateImgePath();
    }

    /**
     * 可以拍照或从图库选取照片后裁剪的比例及宽高
     *
     * @param act 上下文
     * @param aspectX  图片裁剪时的宽度比例
     * @param aspectY  图片裁剪时的高度比例
     * @param outputX  图片裁剪后的宽度
     * @param outputY  图片裁剪后的高度
     * @param listener 选取图片监听
     */
    public void Init(Activity act, int aspectX, int aspectY, int outputX, int outputY, PhotoSelectListener listener) {
        Init(act, true, listener);
        mAspectX = aspectX;
        mAspectY = aspectY;
        mOutputX = outputX;
        mOutputY = outputY;
    }

    /**
     * 设置FileProvider的主机名：一般是包名+".fileprovider"，严格上是build.gradle中defaultConfig{}中applicationId对应的值+".fileprovider"
     * <p>
     * 该工具默认是应用的包名+".fileprovider"，如项目build.gradle中defaultConfig{}中applicationId不是包名，则必须调用此方法对FileProvider的主机名进行设置，否则Android7.0以上使用异常
     *
     * @param authorities FileProvider的主机名
     */
    public void setAuthorities(String authorities) {
        this.AUTHORITIES = authorities;
    }

    /**
     * 修改图片的存储路径（默认的图片存储路径是SD卡上 Android/data/应用包名/时间戳.jpg）
     *
     * @param imgPath 图片的存储路径（包括文件名和后缀）
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * 拍照获取
     */
    public void TakePhoto() {
        File imgFile = new File(imgPath);
        if (!imgFile.getParentFile().exists()) {
            imgFile.getParentFile().mkdirs();
        }
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT < 24) {
            // 从文件中创建uri
            imgUri = Uri.fromFile(imgFile);
        } else {
            //兼容android7.0 使用共享文件的形式
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, imgFile.getAbsolutePath());
            imgUri = act.getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        act.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 从图库获取
     */
    public void SelectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        act.startActivityForResult(intent, SELECT_PHOTO);
    }

    private void Crop(File inputFile, File outputFile) {
        File parentFile = outputFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getImageContentUri(act, inputFile), "image/*");
        } else {
            intent.setDataAndType(Uri.fromFile(inputFile), "image/*");
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        intent.putExtra("crop", "true");
        //设置剪裁图片宽高比
        intent.putExtra("aspectX", mAspectX);
        intent.putExtra("aspectY", mAspectY);
        //设置剪裁图片大小
//        intent.putExtra("outputX", mOutputX);
//        intent.putExtra("outputY", mOutputY);
        // 是否返回uri
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        intent.putExtra("scale",true);
        intent.putExtra("scaleUpIfNeeded", true);

        act.startActivityForResult(intent, ZOOM_PHOTO);
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    private Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 产生图片的路径，带文件夹和文件名，文件名为当前毫秒数
     */
    private String generateImgePath() {
        return getExternalStoragePath() + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }

    /**
     * 获取SD下的应用目录
     */
    public String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        String ROOT_DIR = "Android/data/" + act.getPackageName();
        sb.append(ROOT_DIR);
        sb.append(File.separator);

        String path = sb.toString();
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        return path;
    }

    public void ActivityForResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO://拍照
                    mInputFile = new File(imgPath);
                    if (isCrop) {//裁剪
                        mOutputFile = new File(generateImgePath());
                        mOutputUri = Uri.fromFile(mOutputFile);
                        Crop(mInputFile, mOutputFile);
                    } else {//不裁剪
                        mOutputUri = Uri.fromFile(mInputFile);
                        if (listener != null) {
                            listener.onFinish(mInputFile, mOutputUri);
                            Compression(mInputFile);
                        }

                    }
                    break;
                case SELECT_PHOTO://图库
                    if (data != null) {
                        Uri sourceUri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = act.managedQuery(sourceUri, proj, null, null, null);
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(columnIndex);
                        mInputFile = new File(imgPath);

                        if (isCrop) {//裁剪
                            mOutputFile = new File(generateImgePath());
                            mOutputUri = Uri.fromFile(mOutputFile);
                            Crop(mInputFile, mOutputFile);
                        } else {//不裁剪
                            mOutputUri = Uri.fromFile(mInputFile);
                            if (listener != null) {
                                listener.onFinish(mInputFile, mOutputUri);

                                Compression(mInputFile);
                            }
                        }
                    }
                    break;
                case ZOOM_PHOTO://裁剪
                    if (data != null) {
                        if (mOutputUri != null) {
                            //删除拍照的临时照片
                            File tmpFile = new File(imgPath);
                            if (tmpFile.exists())
                                tmpFile.delete();
                            if (listener != null) {
                                listener.onFinish(mOutputFile, mOutputUri);
                            }

                            Compression(mOutputFile);
                        }
                    }
                    break;
            }
        }
    }


    /**
     * 返回图片监听
     */
    private PhotoSelectListener listener;
    public interface PhotoSelectListener {
        void onFinish(File outputFile, Uri outputUri);
        void onCompressionFinish(File outputFile);
    }


    public void Compression(File file){
        CompressionTool.with(act)
                .load(file)
                .ignoreBy(100)
                .setTargetDir(getExternalStoragePath())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        System.out.println("error");
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        if (listener != null){
                            listener.onCompressionFinish(file);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        System.out.println("error");
                    }
                }).launch();
    }
}
