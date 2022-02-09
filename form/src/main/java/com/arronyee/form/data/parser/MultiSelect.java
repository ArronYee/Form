package com.arronyee.form.data.parser;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeying on 2018/9/21.
 * 多选类型的解析器
 */

public class MultiSelect extends DataParser {
    public static final String TAG = "select_multi";
    private List<Element> domains;
    private String domainKey;
    private String domainType;
    private List<Integer> selectPoses = new ArrayList<>();

    public List<Integer> getSelectPoses() {
        return selectPoses;
    }

    public void setSelectPoses(List<Integer> selectPoses) {
        this.selectPoses = selectPoses;
    }

    {
        type = TAG;
    }

    public MultiSelect(String typeStr) {
        try {
            String[] infos = typeStr.split(":");
            String[] selectInfos = infos[1].split("/");
            domainKey = selectInfos[1];
            domainType = selectInfos[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                domains = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject temp = jsonArray.getJSONObject(i);
                    domains.add(new Element(temp.optString("value"), temp.optString("text")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public String getShowTextFromValue() {
        String showText = null;
        //多选控件的值要查找对应的显示的文字，","分割
        if (this.getDomains() != null && this.getDomains().size() > 0) {
            if (!TextUtils.isEmpty(value)) {
                if (value.contains(",")) {
                    String[] values = value.split(",");
                    List<Integer> selectPos = new ArrayList<>();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < values.length; i++) {
                        for (int j = 0; j < this.getDomains().size(); j++) {
                            if (values[i].equals(this.getDomains().get(j).getValue())) {
                                sb.append(this.getDomains().get(j).getText());
                                selectPos.add(j);
                                if (i != values.length - 1) {
                                    sb.append(",");
                                }
                                break;
                            }
                        }
                    }
                    showText = sb.toString();
                    if (this.getSelectPoses() != null) {
                        this.getSelectPoses().clear();
                        this.getSelectPoses().addAll(selectPos);
                    } else {
                        this.setSelectPoses(selectPos);
                    }
                } else {
                    List<Integer> selectPos = new ArrayList<>();
                    for (int j = 0; j < this.getDomains().size(); j++) {
                        if (value.equals(this.getDomains().get(j).getValue())) {
                            showText = getDomains().get(j).getText();
                            selectPos.add(j);
                            break;
                        }
                    }
                    if (this.getSelectPoses() != null) {
                        this.getSelectPoses().clear();
                        this.getSelectPoses().addAll(selectPos);
                    } else {
                        this.setSelectPoses(selectPos);
                    }
                }
            } else {
                showText = value;
            }
        }
        return showText;
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
        if (required == 1 && (selectPoses == null || selectPoses.size() == 0)) {
            validateError(type, lebel, name, "请选择" + lebel);
            return false;
        }
        return true;
    }


}
