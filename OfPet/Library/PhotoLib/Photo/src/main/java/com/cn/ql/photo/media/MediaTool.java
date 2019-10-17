package com.cn.ql.photo.media;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.cn.ql.photo.listener.ImageLoadListener;
import com.cn.ql.photo.media.type.MediaType;
import com.cn.ql.photo.media.type.MimeType;
import com.cn.ql.photo.model.MediaEntity;
import com.cn.ql.photo.model.MediaFolder;
import com.cn.ql.photo.utils.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author axw_an
 * @create on 2019/3/13
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/13:
 */
public class MediaTool {

    private long videoFilterTime = 0;
    private int mediaFilterSize = 0;
    private FragmentActivity activity;

    public MediaTool(FragmentActivity activity) {
        this.activity = activity;
    }

    private Uri ALL_QUERY_URI = MediaStore.Files.getContentUri("external");
    private String DURATION = "duration";
    private String SIZE = "_size";
    private String LATITUDE = "latitude";
    private String LONGITUDE = "longitude";

    /**
     * 全部媒体数据 - PROJECTION
     */
    private String[] ALL_PROJECTION = new String[]{MediaStore.Images.Media._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            LATITUDE,
            LONGITUDE,
            DURATION};

    /**
     * 全部媒体数据 - SELECTION
     */
    private String ALL_SELECTION =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                    + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO
                    + " AND "
                    + MediaStore.Files.FileColumns.SIZE + ">0"
                    + " AND "
                    + DURATION + ">0";

    /**
     * 图片 - PROJECTION
     */
    private String[] IMAGE_PROJECTION = new String[]{MediaStore.Images.Media._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            LATITUDE,
            LONGITUDE};

    /**
     * 图片 - SELECTION
     */
    private String IMAGE_SELECTION =
            MediaStore.Images.Media.MIME_TYPE + "=? or " +
                    MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " AND "
                    + MediaStore.MediaColumns.WIDTH +
                    ">0";

    /**
     * 图片 - SELECTION_ARGS
     */
    private String[] IMAGE_SELECTION_ARGS = new String[]{"image/jpeg", "image/png", "image/webp"};

    /**
     * 视频 - PROJECTION
     */
    private String[] VIDEO_PROJECTION = new String[]{MediaStore.Images.Media._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            MediaStore.MediaColumns.WIDTH,
            MediaStore.MediaColumns.HEIGHT,
            LATITUDE,
            LONGITUDE,
            DURATION};

    /**
     * 视频 - SELECTION
     */
    private String VIDEO_SELECTION =
            MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " AND "
                    + MediaStore.MediaColumns.WIDTH + ">0"
                    + " AND "
                    + DURATION + ">0";

    /**
     * 视频 - SELECTION_ARGS
     */
    private String[] VIDEO_SELECTION_ARGS = new String[]{"video/mp4"};

    /**
     * 音频 - PROJECTION
     */
    private String[] AUDIO_PROJECTION = new String[]{MediaStore.Images.Media._ID,
            MediaStore.MediaColumns.DATA,
            MediaStore.MediaColumns.DISPLAY_NAME,
            MediaStore.MediaColumns.DATE_ADDED,
            MediaStore.MediaColumns.MIME_TYPE,
            MediaStore.MediaColumns.SIZE,
            DURATION};

    /**
     * 音频 - SELECTION
     */
    private String AUDIO_SELECTION =
            MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " AND "
                    + DURATION + ">0";

    /**
     * 音频 - SELECTION_ARGS
     */
    private String[] AUDIO_SELECTION_ARGS = new String[]{"audio/mpeg"};

    /**
     * 获取全部图片和视频，但过滤掉gif图片
     */
    private String SELECTION_NOT_GIF =
                    MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " OR "
                    + MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " OR "
                    + MediaStore.Images.Media.MIME_TYPE + "=?"
                    + " OR "
                    + MediaStore.Files.FileColumns.MEDIA_TYPE + "=?"
                    + " AND "
                    + MediaStore.MediaColumns.SIZE + ">0"
                    + " AND "
                    + MediaStore.MediaColumns.WIDTH + ">0";

    private String[] SELECTION_NOT_GIF_ARGS = new String[]{"image/jpeg", "image/png", "image/webp", "video/mp4"};

    /**
     * 获取全部图片和视频
     */
    private String SELECTION_VIDEO_IMAGE =
            MediaStore.Files.FileColumns.MEDIA_TYPE + "="
            + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
            + " OR "
            + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
            + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
            + " AND "
            + MediaStore.Files.FileColumns.SIZE + ">0"
            + " AND "
            + DURATION + ">0";

    private String ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC";

    private String ORDER_DATA = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

    public void getMediaParentPaths(MediaType type, final ImageLoadListener listener) {
        LoaderManager.getInstance(activity).initLoader(type.get(), null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @NonNull
            @Override
            public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle bundle) {
                if (listener != null){
                    listener.loadStart();
                }

                CursorLoader cursorLoader = null;

                String durationCondition = videoFilterTime > 0L ? " AND " + DURATION + "<" + videoFilterTime : "";
                String sizeCondition = mediaFilterSize > 0 ? " AND " + SIZE + "<" + mediaFilterSize : "";

                MediaType mediaType = MediaType.matchOpCode(id);
                switch (mediaType) {
                    case ALL: {
                        cursorLoader = new CursorLoader(activity,
                                ALL_QUERY_URI,
                                ALL_PROJECTION,
                                ALL_SELECTION
                                        + durationCondition
                                        + sizeCondition,
                                null,
                                ORDER_DATA);
                    }
                    break;
                    case IMAGE: {
                        cursorLoader = new CursorLoader(activity,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                IMAGE_PROJECTION,
                                IMAGE_SELECTION
                                        + sizeCondition,
                                IMAGE_SELECTION_ARGS,
                                ORDER_DATA);
                    }
                    break;
                    case VIDEO: {
                        cursorLoader = new CursorLoader(activity,
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                VIDEO_PROJECTION,
                                VIDEO_SELECTION
                                        + durationCondition
                                        + sizeCondition,
                                VIDEO_SELECTION_ARGS,
                                ORDER_DATA);
                    }
                    break;
                    case AUDIO: {
                        cursorLoader = new CursorLoader(activity,
                                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                                AUDIO_PROJECTION,
                                AUDIO_SELECTION
                                        + sizeCondition,
                                AUDIO_SELECTION_ARGS,
                                ORDER_DATA);
                    }
                    break;
                    case VIDEO_IMAGE:{
                        cursorLoader = new CursorLoader(activity,
                                ALL_QUERY_URI,
                                ALL_PROJECTION,
                                SELECTION_VIDEO_IMAGE
                                        + durationCondition
                                        + sizeCondition,
                                null,
                                ORDER_DATA);
                    }
                    break;
                    default: {
                        cursorLoader = new CursorLoader(activity,
                                ALL_QUERY_URI,
                                ALL_PROJECTION,
                                ALL_SELECTION
                                        + durationCondition
                                        + sizeCondition,
                                null,
                                ORDER_DATA);
                    }
                    break;
                }

                return cursorLoader;
            }

            @Override
            public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
                if (cursor == null){
                    return;
                }
                try {
                    List<MediaFolder> imageFolders = new ArrayList<>();

                    MediaFolder allImageFolder = new MediaFolder();
                    List<MediaEntity> latelyImages = new ArrayList<>();

                    int count = cursor.getCount();
                    if (count > 0){
                        cursor.moveToFirst();
                        do {
                            String path = cursor.getString(cursor.getColumnIndexOrThrow(ALL_PROJECTION[1]));
                            if (TextUtils.isEmpty(path) || !new File(path).exists()){
                                continue;
                            }
                            String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(ALL_PROJECTION[4]));
                            int fileType = 0;
                            long duration = 0L;
                            if (mimeType.startsWith(Constant.AUDIO)) {
                                fileType = MimeType.ofAudio();
                            }else if(mimeType.startsWith(Constant.IMAGE)){
                                fileType = MimeType.ofImage();
                            }else if(mimeType.startsWith(Constant.VIDEO)){
                                fileType = MimeType.ofVideo();
                                duration = cursor.getLong(cursor.getColumnIndexOrThrow(ALL_PROJECTION[10]));
                            }

                            long size = cursor.getLong(cursor.getColumnIndexOrThrow(ALL_PROJECTION[5]));
                            int width = 0;
                            int height = 0;
                            double latitude = 0;
                            double longitude = 0;
                            if (!mimeType.startsWith(Constant.AUDIO)) {
                                width = cursor.getInt(cursor.getColumnIndexOrThrow(ALL_PROJECTION[6]));
                                height = cursor.getInt(cursor.getColumnIndexOrThrow(ALL_PROJECTION[7]));
                                latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ALL_PROJECTION[8]));
                                longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(ALL_PROJECTION[9]));
                            }

                            MediaEntity image = MediaEntity.newBuilder()
                                    .localPath(path)
                                    .duration(duration)
                                    .fileType(fileType)
                                    .mimeType(mimeType)
                                    .size(size)
                                    .width(width)
                                    .height(height)
                                    .latitude(latitude)
                                    .longitude(longitude)
                                    .build();

                            MediaFolder folder = getImageFolder(path, imageFolders);
                            List<MediaEntity> images = folder.getImages();
                            if (images == null){
                                images = new ArrayList<>();
                            }
                            images.add(image);
                            folder.setImageNumber(folder.getImageNumber()+1);
                            latelyImages.add(image);
                            int imageNum = allImageFolder.getImageNumber();
                            allImageFolder.setImageNumber(imageNum+1);
                        }while (cursor.moveToNext());

                        if (latelyImages.size() > 0){
                            sortFolder(imageFolders);
                            imageFolders.add(0, allImageFolder);
                            allImageFolder.setFirstImagePath(latelyImages.get(0).getLocalPath());

                            allImageFolder.setImages(latelyImages);
                        }
                    }

                    if (listener != null){
                        listener.loadComplete(latelyImages, imageFolders);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (listener != null){
                        listener.loadEnd();
                    }
                }

            }

            @Override
            public void onLoaderReset(@NonNull Loader<Cursor> loader) {

            }
        });
    }

    private MediaFolder getImageFolder(String path, List<MediaFolder> imageFolders) {
        File imageFile = new File(path);
        File folderFile = imageFile.getParentFile();

        for (int i=0; i < imageFolders.size(); i++) {
            MediaFolder folder = imageFolders.get(i);
            if (folder.getName().equals(folderFile.getName())) {
                return folder;
            }
        }
        MediaFolder newFolder = new MediaFolder(folderFile.getName(), folderFile.getAbsolutePath(), path, 0, 0, true, new ArrayList<MediaEntity>());
        imageFolders.add(newFolder);
        return newFolder;
    }

    private void sortFolder(List<MediaFolder> imageFolders) {
        // 文件夹按图片数量排序
        Collections.sort(imageFolders, new Comparator<MediaFolder>() {
            @Override
            public int compare(MediaFolder o1, MediaFolder o2) {
                if (o1.getImages() == null || o2.getImages() == null) {
                    return 0;
                }
                if (o1.getImages().size() == 0 || o2.getImages().size() == 0){
                    return 0;
                }
                int o1Size = o1.getImageNumber();
                int o2Size = o2.getImageNumber();

                if (o1Size == o2Size){
                    return 0;
                }else if(o1Size < o2Size){
                    return 1;
                }
                return -1;
            }
        });
    }
}
