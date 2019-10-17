package com.cn.ql.frame.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author  by AXW on 2018/4/23.
 * 注：字符串
 */
public class StringUtils {

    /**
     * 判断字符串是否为null
     *
     * @param s 字符串
     * @return
     */
    public static boolean isEmpty(String s) {
        if (s == null || s.length() == 0 || s.equals("null")) {
            return true;
        }
        return false;
    }

    /**
     * 保留两位小数
     * @param d
     * @return
     */
    public static String reserve2(double d) {
        if (d == 0){
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

    static Pattern p_10086 = Pattern
            .compile("^((13[4-9])|(147)|(15[0-2,7-9])|(178)|(18[2-4,7-8]))\\d{8}|(1705)\\d{7}$");
    static Pattern p_10010 = Pattern
            .compile("^((13[0-2])|(145)|(15[5-6])|(176)|(18[5,6]))\\d{8}|(1709)\\d{7}$");
    static Pattern p_10001 = Pattern
            .compile("^((133)|(153)|(177)|(173)|(18[0,1,9])|(19[0,1,9]))\\d{8}$");
    static Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]{9}$");
    /**
     * 判断手机格式是否正确
     *
     * @param mobile 手机号码
     * @return 是/否
     */
    public static boolean isChinaMobile(String mobile) {
        Matcher m_10086 = p_10086.matcher(mobile);
        Matcher m_10010 = p_10010.matcher(mobile);
        Matcher m_10001 = p_10001.matcher(mobile);
        Matcher m = p.matcher(mobile);
        if (m_10086.matches() || m_10010.matches() || m_10001.matches() || m.matches()) {
            return true;
        }
        return false;
    }

    public static boolean mobile(String areaCode, String mobile){
        boolean result = true;
        if (StringUtils.isEmpty(mobile)){
            result = false;
        }else {
            if (areaCode.equals("86")) { // 中国号码
                if (StringUtils.isChinaMobile(mobile)) {

                } else {
                    result = false;
                }
            } else { // 国际号码
                if (mobile.length() > 6 && mobile.length() < 12) {

                }else{
                    result = false;
                }
            }
        }
        return result;
    }
}
