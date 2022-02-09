package com.arronyee.form.data.parser;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by yeying on 2018/9/21.
 * 解析器
 */

public class DataParser {

    /**
     * {"label":"所属网格","name":"gId","type":"select:pagetree/grid","view":0,"required":1},
     */
    protected String type; //标记类型的字符串
    protected String lebel;//
    protected String name;
    protected int view;
    protected int required;
    protected String value;//提交时候的值
    protected String defaultValue;

    public OnValidateError onValidateError;//可以自定义校验器


    public interface OnValidateError {
        void validateError(String type, String label, String name, String message);
    }

    public DataParser() {
    }

    public DataParser(String typeStr) {

    }


    public void validateError(String type, String label, String name, String message) {
        if (onValidateError != null) {
            onValidateError.validateError(type, label, name, message);
        }
    }

    public void setOnValidateError(OnValidateError onValidateError) {
        this.onValidateError = onValidateError;
    }

    public String getShowTextFromValue() {
        return value;
    }

    public void fillIn(JSONObject jsonObject) {

    }

    public boolean validate() {
        return false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLebel() {
        return lebel;
    }

    public void setLebel(String lebel) {
        this.lebel = lebel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired(int required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public JSONArray getValueList() {
        JSONArray jsonArray = new JSONArray();
        if (!TextUtils.isEmpty(value)) {
            String[] arr = value.split(",");
            if (arr != null && arr.length > 0) {
                for (String str : arr) {
                    jsonArray.put(str);
                }
            }
        }
        return jsonArray;
    }

    @Override
    public String toString() {
        return "DataParser{" +
                "type='" + type + '\'' +
                ", lebel='" + lebel + '\'' +
                ", name='" + name + '\'' +
                ", view=" + view +
                ", required=" + required +
                ", value='" + value + '\'' +
                '}';
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}