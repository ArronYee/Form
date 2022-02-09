package com.arronyee.form.data.parser;

import android.text.TextUtils;

/**
 * Created by yeying on 2018/12/18.
 * （是否）类型的解析器
 */

public class BooleanSelect extends DataParser {
    public static final String TAG = "bool";

    {
        type = TAG;
    }

    public BooleanSelect(String typeStr) {
    }

    public String getShowTextFromValue() {
        if (TextUtils.equals(value, "0") || TextUtils.equals(value, "1")) {
            boolean result = TextUtils.equals(value, "0");
            return result ? "否" : "是";
        } else {
            return "";
        }
    }

    @Override
    public boolean validate() {
        if (required == 1 && TextUtils.isEmpty(value)) {
            validateError(type, lebel, name, "请选择" + lebel);
            return false;
        }
        return true;
    }

}
