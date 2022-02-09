package com.arronyee.form.data.parser;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeying on 2018/9/21.
 * 选择器类型的解析器
 */

public class Select extends DataParser {
    public static final String TAG = "select";
    public static final String DOMAIN_TYPE_PAGETREE = "pagetree";
    public static final String DOMAIN_TYPE_DOMAIN = "domain";

    private List<Element> domains;
    private String domainKey;
    private String domainType;
    private int selectPos;
    private String extra;

    private String type1Value;
    private String type2Value;

    private String type1Name = "";
    private String type2Name = "";

    public interface CustomValidate {
        boolean validate();
    }

    private CustomValidate customValidate;


    {
        type = TAG;
    }

    public Select(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            String[] selectInfos = infos[1].split("/");
            domainKey = selectInfos[1];
            domainType = selectInfos[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CustomValidate getCustomValidate() {
        return customValidate;
    }

    public void setCustomValidate(CustomValidate customValidate) {
        this.customValidate = customValidate;
    }

    public String getShowTextFromValue() {
        String showText = null;
        if (TextUtils.equals("domain", getDomainType())) {
            for (int i = 0; i < this.getDomains().size(); i++) {
                Element element = this.getDomains().get(i);
                if (element.getValue().equals(value)) {
                    showText = element.getText();
                    this.setSelectPos(i);
                    break;
                }
            }
        } else if (TextUtils.equals("pagetree", getDomainType())) {
            showText = this.getExtra();
        } else if (TextUtils.equals("jumpPage", getDomainType())) {
            showText = type1Name + "-" + type2Name;
        } else {
            showText = type1Name + "-" + type2Name;
        }
        return showText;
    }

    public String getType1Value() {
        return type1Value;
    }

    public void setType1Value(String type1Value) {
        this.type1Value = type1Value;
    }

    public String getType2Value() {
        return type2Value;
    }

    public void setType2Value(String type2Value) {
        this.type2Value = type2Value;
    }

    public String getType1Name() {
        return type1Name;
    }

    public void setType1Name(String type1Name) {
        this.type1Name = type1Name;
    }

    public String getType2Name() {
        return type2Name;
    }

    public void setType2Name(String type2Name) {
        this.type2Name = type2Name;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getSelectPos() {
        return selectPos;
    }

    public void setSelectPos(int selectPos) {
        this.selectPos = selectPos;
    }

    public String getDomainKey() {
        return domainKey;
    }

    public void setDomainKey(String domainKey) {
        this.domainKey = domainKey;
    }

    public String getDomainType() {
        return domainType;
    }

    public void setDomainType(String domainType) {
        this.domainType = domainType;
    }

    @Override
    public void fillIn(JSONObject jsonObject) {
        super.fillIn(jsonObject);
        if (TextUtils.equals("domain", domainType)) {
            try {
                JSONArray jsonArray = jsonObject.getJSONArray(domainKey);
                domains = new ArrayList<Element>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    domains.add(new Element(temp.getString("value"), temp.getString("text")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Element> getDomains() {
        return domains;
    }

    public void setDomains(List<Element> domains) {
        this.domains = domains;
    }

    public class Element {
        private String value;
        private String text;

        public Element(String value, String text) {
            this.value = value;
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @Override
    public boolean validate() {
        if (customValidate != null) {
            return customValidate.validate();
        }
        if (required == 1 && TextUtils.isEmpty(value)) {
            validateError(type, lebel, name, "请选择" + lebel);
            return false;
        }
        return true;
    }
}
