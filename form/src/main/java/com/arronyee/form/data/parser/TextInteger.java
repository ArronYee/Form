package com.arronyee.form.data.parser;

import android.text.TextUtils;


/**
 * Created by yeying on 2018/11/15.
 * 数字类型的解析器
 */

public class TextInteger extends DataParser {
    public int start;
    public int end;
    public static final String TAG = "integer";

    {
        type = TAG;
    }

    private TextInteger() {
    }

    public TextInteger(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            String[] textStringInfos = infos[1].split("/");
            setStart(Integer.parseInt(textStringInfos[0]));
            int end = Integer.parseInt(textStringInfos[1]);
            if (end == 0) {
                setEnd(9999);
            } else {
                setEnd(end);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setStart(0);
            setEnd(100);
        }
    }

    public boolean validate() {
        if (TextUtils.isEmpty(value)) {
            if (required == 1) {
                validateError(type, lebel, name, lebel + "为必填项");
                return false;
            } else {
                return true;
            }
        } else {
            if (value.length() < start || value.length() > end) {
                validateError(type, lebel, name, lebel + "长度为" + start + "-" + end);
            }
        }
        return true;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
}


