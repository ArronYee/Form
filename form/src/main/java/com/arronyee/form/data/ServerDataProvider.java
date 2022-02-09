package com.arronyee.form.data;

import android.text.TextUtils;

import com.arronyee.form.data.parser.Cordition;
import com.arronyee.form.data.parser.DataParser;
import com.arronyee.form.data.parser.MemoString;
import com.arronyee.form.data.bean.ServerAddField;
import com.arronyee.form.data.parser.BooleanSelect;
import com.arronyee.form.data.parser.DateSelect;
import com.arronyee.form.data.parser.ExtraFile;
import com.arronyee.form.data.parser.MultiSelect;
import com.arronyee.form.data.parser.Select;
import com.arronyee.form.data.parser.TextInteger;
import com.arronyee.form.data.parser.TextString;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yeying on 2018/9/11.
 */

public class ServerDataProvider {

    /**
     * 解析器类型
     */
    private static HashMap<String, Class<? extends DataParser>> mappers = new HashMap<>();

    static {
        mappers.put(TextString.TAG, TextString.class);
        mappers.put(TextInteger.TAG, TextInteger.class);
        mappers.put(MemoString.TAG, MemoString.class);
        mappers.put(Select.TAG, Select.class);
        mappers.put(DateSelect.TAG, DateSelect.class);
        mappers.put(MultiSelect.TAG, MultiSelect.class);
        mappers.put(Cordition.TAG, Cordition.class);
        mappers.put(BooleanSelect.TAG, BooleanSelect.class);
        mappers.put(ExtraFile.TAG, ExtraFile.class);
    }

    private boolean initComplete;

    private List<DataParser> dataParsers;

    public DataParser.OnValidateError onValidateError;

    public List<DataParser> getDataParsers() {
        return dataParsers;
    }

    /**
     * 解析完成后对数据做处理的回调
     */
    public InterceptorAfterParse interceptorAfterParse;

    public interface InterceptorAfterParse {
        void process(DataParser dataParser);
    }

    public InterceptorAfterParse getInterceptorAfterParse() {
        return interceptorAfterParse;
    }

    public void setInterceptorAfterParse(InterceptorAfterParse interceptorAfterParse) {
        this.interceptorAfterParse = interceptorAfterParse;
    }

    /**
     * 对本地filed数据进行解析处理/
     *
     * @param serverAddField
     * @param json
     */
    public void init(String json) {
        ServerAddField serverAddField = new Gson().fromJson(json,ServerAddField.class);
        dataParsers = new ArrayList<>();
        List<ServerAddField.FormdescBean.FieldsBean> fieldsBeens = serverAddField.getFormdesc().getMain().getFields();
        List<ServerAddField.FormdescBean.FieldsBean> extendsFieldsBeens = null;
        if (serverAddField.getFormdesc().getExtendsX() != null) {
            extendsFieldsBeens = serverAddField.getFormdesc().getExtendsX().getFields();
        }
        JSONObject domainObject = null;
        if (extendsFieldsBeens != null && extendsFieldsBeens.size() > 0) {
            fieldsBeens.addAll(extendsFieldsBeens);
        }
        try {
            domainObject = new JSONObject(json);
            domainObject = domainObject.getJSONObject("domains");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < fieldsBeens.size(); i++) {
            ServerAddField.FormdescBean.FieldsBean fieldsBean = fieldsBeens.get(i);
            DataParser dataParser = getDataParser(fieldsBean, domainObject);
            if (dataParser != null) {
                dataParsers.add(dataParser);
                if (onValidateError != null) {
                    dataParser.setOnValidateError(onValidateError);
                }
            }
        }
        initComplete = true;
    }

    public void cleanAll() {
        if (dataParsers != null) {
            dataParsers.clear();
        }
    }

    public void setOnValidateError(DataParser.OnValidateError onValidateError) {
        this.onValidateError = onValidateError;
        if (dataParsers != null && dataParsers.size() > 0) {
            for (DataParser dataParser : dataParsers) {
                dataParser.setOnValidateError(onValidateError);
            }
        }
    }

    /**
     * 根据type生成不同的数据解析器
     *
     * @param fieldsBean
     * @param domainObject
     * @return
     */
    public DataParser getDataParser(ServerAddField.FormdescBean.FieldsBean fieldsBean, JSONObject domainObject) {
        String typeStr = fieldsBean.getType();
        if (!TextUtils.isEmpty(typeStr)) {
            DataParser dataParser = null;
            String[] infos = typeStr.split(":");
            if (mappers.get(infos[0]) != null) {
                Class<? extends DataParser> dataParserClass = mappers.get(infos[0]);
                try {
                    Constructor<? extends DataParser> constructor = dataParserClass.getDeclaredConstructor(new Class[]{String.class});
                    dataParser = constructor.newInstance(typeStr);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            if (dataParser != null) {
                dataParser.setLebel(fieldsBean.getLabel());
                dataParser.setName(fieldsBean.getName());
                dataParser.setRequired(fieldsBean.getRequired());
                dataParser.setView(fieldsBean.getView());
                dataParser.fillIn(domainObject);
                dataParser.setDefaultValue(fieldsBean.getDefalutValue());
                if (interceptorAfterParse != null) {
                    interceptorAfterParse.process(dataParser);
                }
                return dataParser;
            }
        }
        return null;
    }

    /**
     * 校验全部校验器
     *
     * @return
     */
    public boolean validateAll() {
        if (dataParsers != null && dataParsers.size() > 0) {
            for (int i = 0; i < dataParsers.size(); i++) {
                if (!dataParsers.get(i).validate()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }


    public DataParser getDataParser(String name) {
        List<DataParser> dataParsers = getDataParsers();
        DataParser dataParser = null;
        if (dataParsers != null) {
            for (DataParser temp : dataParsers) {
                if (temp.getName().equals(name)) {
                    dataParser = temp;
                    break;
                }
            }
        }
        return dataParser;
    }

    /**
     * 更新某个item的value值
     *
     * @param name
     * @param value
     * @return
     */
    public DataParser updateItemValue(String name, String value) {
        List<DataParser> dataParsers = getDataParsers();
        DataParser dataParser = null;
        if (dataParsers == null || dataParsers.size() == 0) {
            return null;
        }
        for (DataParser temp : dataParsers) {
            if (temp.getName().equals(name)) {
                dataParser = temp;
                break;
            }
        }
        if (dataParser == null) {
            return null;
        }
        dataParser.setValue(value);
        return dataParser;
    }


    /**
     * 将本地数据生成json方便提交
     *
     * @return
     */
    public String getSubmitStr() {
        List<DataParser> dataParsers = getDataParsers();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < dataParsers.size(); i++) {
            try {
                if (dataParsers.get(i).getType().equals(MultiSelect.TAG)) {
                    MultiSelect multiSelect = (MultiSelect) dataParsers.get(i);
                    String value = multiSelect.getValue();
                    JSONArray jsonArray = new JSONArray();
                    if (!TextUtils.isEmpty(value) && value.contains(",")) {
                        String[] values = value.split(",");
                        for (int j = 0; j < values.length; j++) {
                            jsonArray.put(values[j]);
                        }
                        jsonObject.put(dataParsers.get(i).getName(), jsonArray);
                    } else {
                        jsonArray.put(value);
                        jsonObject.put(dataParsers.get(i).getName(), jsonArray);
                    }
                } else {
                    jsonObject.put(dataParsers.get(i).getName(), dataParsers.get(i).getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }


    public JSONObject getSubmitobject() {
        List<DataParser> dataParsers = getDataParsers();
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < dataParsers.size(); i++) {
            try {
                if (dataParsers.get(i).getType().equals(MultiSelect.TAG)) {
                    MultiSelect multiSelect = (MultiSelect) dataParsers.get(i);
                    String value = multiSelect.getValue();
                    JSONArray jsonArray = new JSONArray();
                    if (!TextUtils.isEmpty(value) && value.contains(",")) {
                        String[] values = value.split(",");
                        for (int j = 0; j < values.length; j++) {
                            jsonArray.put(values[j]);
                        }
                        jsonObject.put(dataParsers.get(i).getName(), jsonArray);
                    } else {
                        jsonArray.put(value);
                        jsonObject.put(dataParsers.get(i).getName(), jsonArray);
                    }
                } else {
                    jsonObject.put(dataParsers.get(i).getName(), dataParsers.get(i).getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 将本地数据生成json方便提交
     *
     * @return
     */
    public String getSubmitStr(String pId, String id) {
        List<DataParser> dataParsers = getDataParsers();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pId", pId);
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < dataParsers.size(); i++) {
            try {
                if (dataParsers.get(i).getType().equals(MultiSelect.TAG)) {
                    //","分割改为数组提交
                    MultiSelect multiSelect = (MultiSelect) dataParsers.get(i);
                    String value = multiSelect.getValue();
                    JSONArray jsonArray = new JSONArray();
                    if (!TextUtils.isEmpty(value) && value.contains(",")) {
                        String[] values = value.split(",");
                        for (int j = 0; j < values.length; j++) {
                            jsonArray.put(values[j]);
                        }
                        jsonObject.put(dataParsers.get(i).getName(), jsonArray);
                    } else {
                        jsonArray.put(value);
                        jsonObject.put(dataParsers.get(i).getName(), jsonArray);
                    }
                } else {
                    jsonObject.put(dataParsers.get(i).getName(), dataParsers.get(i).getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject.toString();
    }

    public JSONObject getJsonObjectValue() {
        if (dataParsers != null && dataParsers.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < dataParsers.size(); i++) {
                DataParser dataParser = dataParsers.get(i);
                if (TextUtils.equals("files", dataParser.getName())) {
                    try {
                        jsonObject.put("imageId", ((ExtraFile) dataParser).getValue());
                        jsonObject.put("image", ((ExtraFile) dataParser).getImageUrl());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (!TextUtils.isEmpty(dataParser.getValue())) {
                    try {
                        if (TextUtils.equals("departmentId", dataParser.getName())) {
                            if (!TextUtils.isEmpty(((Select) dataParser).getExtra())) {
                                jsonObject.put("areaName", ((Select) dataParser).getExtra());
                            }
                        }

                        if (TextUtils.equals("placeTypeMax", dataParser.getName())) {
                            jsonObject.put("placeTypeMax", ((Select) dataParser).getType1Value());
                            jsonObject.put("placeType", ((Select) dataParser).getType2Value());
                            jsonObject.put("placeTypeMaxName", ((Select) dataParser).getType1Name());
                            jsonObject.put("placeTypeName", ((Select) dataParser).getType2Name());
                        }
                        jsonObject.put(dataParser.getName(), dataParser.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jsonObject;
        }
        return new JSONObject();
    }

}
