package com.arronyee.form.data.parser;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yeying on 2018/9/21.
 * 日期类型的解析器
 */

public class DateSelect extends DataParser {
    public static final String TAG = "date";

    private String dateFormatStr;

    {
        type = TAG;
    }

    public DateSelect() {
    }

    public DateSelect(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            dateFormatStr = infos[1];
        } catch (Exception e) {
            e.printStackTrace();
            dateFormatStr = "yyyy-MM-dd";
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

    @Override
    public void setValue(String value) {
        super.setValue(value);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(value);
            format = new SimpleDateFormat(dateFormatStr);
            String result = format.format(date);
            if (!TextUtils.isEmpty(result)) {
                setValue(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getDateFormatStr() {
        return dateFormatStr;
    }

    public void setDateFormatStr(String dateFormatStr) {
        this.dateFormatStr = dateFormatStr;
    }
}
