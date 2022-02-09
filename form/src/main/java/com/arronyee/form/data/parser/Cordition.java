package com.arronyee.form.data.parser;

import android.text.TextUtils;

/**
 * Created by yeying on 2018/9/25.
 * 地理位置类型的解析器
 */

public class Cordition extends DataParser {

    public static final String TAG = "cordition";

    {
        type = TAG;
    }

    public Cordition() {
    }

    public Cordition(String typeStr) {
        super(typeStr);
    }

    @Override
    public boolean validate() {
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
}
