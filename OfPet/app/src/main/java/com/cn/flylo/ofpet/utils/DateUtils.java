package com.cn.flylo.ofpet.utils;

import com.cn.ql.frame.utils.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author axw_an
 * @create on 2019/10/24
 * @refer url：
 * @description:
 * @update: axw_an:2019/10/24:
 */
public class DateUtils {

    public static String getDate(long millis){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date(millis);
        String text = simpleDateFormat.format(date);
        return text;
    }

    public static String getDate(String dateText){
        if (StringUtils.isEmpty(dateText)){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = strToDateLong(dateText);
        String text = simpleDateFormat.format(date);
        return text;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        if (StringUtils.isEmpty(strDate)){
            return new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }
}
