package com.arronyee.form.utils;

import android.text.TextUtils;

/**
 * Created by zhoujun on 2017/8/1.
 */

public class StringUtil {

    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    public static String notNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    public static String notNull(String str, String defaultString) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "null")) {
            return defaultString;
        } else {
            return str;
        }
    }

    public static String notNullAndNullStr(String str, String unit) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "null")) {
            return "";
        } else {
            return str + unit;
        }
    }

    public static String notNullAndNullStr(String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, "null")) {
            return "";
        } else {
            return str;
        }
    }

    public static boolean isNull(String str) {
        return TextUtils.isEmpty(str) || TextUtils.equals(str, "null");
    }

    /**
     * 判断是否是纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumRic(String str) {
        return str.matches("[0-9]*");
    }


}
