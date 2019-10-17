package com.cn.flylo.ofpet.url;

import com.cn.ql.frame.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author axw_an
 * @create on 2019/1/9
 * @refer url：
 * @description:
 * @update: axw_an:2019/1/9:
 */
public class ObjParams {

    public static Map<String, String> getObjToMap(Object obj) {
        return getObjToMap("", obj);
    }

    public static Map<String, String> getObjToMap(String key_add, Object obj) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String key = fields[i].getName();
            try {
                Object value = fields[i].get(obj);
                if (value != null) {
                    map.put(key_add+key, value.toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static HashMap<String, String> getObjToMapNotNull(Object obj) {
        HashMap<String, String> map = new HashMap<>();
        Class cls = obj.getClass();

        Class superCls = cls.getSuperclass();
        Field[] fields = cls.getDeclaredFields();

        getFields(obj, map, fields);
        if (superCls != null) {
            Field[] superFields = superCls.getDeclaredFields();
            if (superFields != null) {
                getFields(obj, map, superFields);
            }
        }

        return map;
    }

    public static Map<String, String> getFields(Object obj, Map<String, String> map, Field[] fields){
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String key = fields[i].getName();
            try {
                Object value = fields[i].get(obj);
                if (value != null) {
                    String str = value.toString();
                    if (!StringUtils.isEmpty(str)) {
                        if (!str.equals("-1")) {
                            map.put(key, str);
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static Map<String, Object> getObjectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            String key = fields[i].getName();
            try {
                Object value = fields[i].get(obj);
                if (value != null) {
                    map.put(key, value.toString());
                } else {
                    map.put(key, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
