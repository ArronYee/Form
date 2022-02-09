package com.arronyee.form.data.parser;

import android.text.TextUtils;


/**
 * Created by yeying on 2018/9/21.
 * 字符串类型的解析器
 */

public class TextString extends DataParser {
    public int start;
    public int end;
    public static final String TAG = "string";

    {
        type = TAG;
    }

    private TextString() {
    }

    public TextString(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            String[] textStringInfos = infos[1].split("/");
            setStart(Integer.parseInt(textStringInfos[0]));
            setEnd(Integer.parseInt(textStringInfos[1]));
        } catch (Exception e) {
            e.printStackTrace();
            setStart(0);
            setEnd(100);
        }
    }

    public boolean validate() {
        if (TextUtils.equals(name, "safeCheckObj")) return true;//平安检查直接放行
        if (TextUtils.isEmpty(value)) {
            if (required == 1) {
                validateError(type, lebel, name, lebel + "为必填项");
                return false;
            } else {
                return true;
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