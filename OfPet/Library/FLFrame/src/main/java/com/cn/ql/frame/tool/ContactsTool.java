package com.cn.ql.frame.tool;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import com.cn.ql.frame.bean.LocalContacts;
import com.cn.ql.frame.utils.py.PinyinUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author axw_an
 * @create on 2019/1/6
 * @refer url：
 * @description:
 * @update: axw_an:2019/1/6:
 */
public class ContactsTool {

    private String rawContacts = "content://com.android.contacts/raw_contacts";
    private String data = "content://com.android.contacts/data";
    private String phone = "vnd.android.cursor.item/phone_v2";
    private String name = "vnd.android.cursor.item/name";
    private String photo = "vnd.android.cursor.item/photo";

    private Uri rawContactsUri;
    private Uri dataUri;

    private ResultListener listener;

    public ContactsTool(ResultListener listener){
        this.listener = listener;
        rawContactsUri = Uri.parse(rawContacts);
        dataUri = Uri.parse(data);
    }

    /**
     *  System.out.println("得到的contact_id="+contactId);
     *  根据contact_id从data表中查询出相应的电话号码和联系人名称, 实际上查询的是视图view_data
     *
     * @param context
     */
    public List<LocalContacts> read(Context context){
        List<LocalContacts> list = new ArrayList<>();
        Cursor rawContactsCursor = context.getContentResolver().query(rawContactsUri,
                new String[] { "contact_id" }, null, null, null);
        if (rawContactsCursor != null) {
            while (rawContactsCursor.moveToNext()) {
                if (rawContactsCursor.getPosition() < 1){
                    continue;
                }
                String contactId = rawContactsCursor.getString(0);
                ContentResolver resolver = context.getContentResolver();
                if (resolver == null || TextUtils.isEmpty(contactId)){
                    continue;
                }
                Cursor dataCursor = context.getContentResolver().query(dataUri,
                        new String[] { "data1", "mimetype", "data15" }, "contact_id=?",
                        new String[] { contactId }, null);

                if (dataCursor != null) {
                    LocalContacts localContacts = new LocalContacts();
                    while (dataCursor.moveToNext()) {
                        String data1 = dataCursor.getString(0);
                        String mimetype = dataCursor.getString(1);
                        if (phone.equals(mimetype)) {//手机号码
                            localContacts.setPhone(data1);
                        } else if (name.equals(mimetype)) {//联系人名字
                            localContacts.setName(data1);

                            String letters = PinyinUtils.getFirstSpell(data1);
                            if (!TextUtils.isEmpty(letters)){
                                letters = letters.toUpperCase();
                                localContacts.setLetters(letters);
                            }
                        }else if(photo.equals(mimetype)){
                            byte[] bytes = dataCursor.getBlob(dataCursor.getColumnIndex("data15"));
                            if (bytes != null) {
                                Bitmap photoBm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                if (photoBm != null){
                                    localContacts.setPhoto(photoBm);
                                }
                            }
                        }
                    }
                    dataCursor.close();
                    if (!TextUtils.isEmpty(localContacts.getPhone())){
                        if (TextUtils.isEmpty(localContacts.getName())){
                            localContacts.setName(localContacts.getPhone());
                            localContacts.setLetters("");
                        }

                        list.add(localContacts);
                    }
                }
            }
            rawContactsCursor.close();
        }
        if (listener != null){
            listener.onResult(list);
        }

        return list;
    }

    public interface ResultListener{
        void onResult(List<LocalContacts> list);
    }
}
