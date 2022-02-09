package com.arronyee.sample.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.arronyee.form.DataOperation;
import com.arronyee.form.LocalRender;
import com.arronyee.sample.activity.AddDataActivity;
import com.arronyee.sample.app.MvpPresenter;
import com.arronyee.form.data.ServerDataProvider;
import com.arronyee.form.data.bean.ServerAddField;
import com.arronyee.form.utils.StringUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

/**
 * Created by yeying on 2018/9/11.
 */

public class AddDataActivityPresenter extends MvpPresenter implements DataOperation {

    private AddDataActivity addDataActivity;
    private boolean isAdd;

    private LocalRender localRender;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        addDataActivity = (AddDataActivity) activity;
        isAdd = addDataActivity.getIntent().getBooleanExtra("isAdd",false);
        localRender = LocalRender.getInstance();
        setData();
    }

    //can replace from server
    public void setData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStreamReader isr = null;
                BufferedReader br = null;
                ServerAddField serverAddField = null;
                StringBuilder sb = new StringBuilder();
                String temp = null;
                try {
                    isr = new InputStreamReader(addDataActivity.getAssets().open("cxry.json"), "UTF-8");
                    br = new BufferedReader(isr);
                    while ((temp = br.readLine()) != null) {
                        sb.append(temp);
                    }
                    serverAddField = new Gson().fromJson(sb.toString(), ServerAddField.class);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (br != null) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (serverAddField == null) {
                    return;
                }
                addDataActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        localRender.bind(addDataActivity, (LinearLayout) addDataActivity.getContainer(),addDataActivity.getRenderConfig());
                        localRender.setData(sb.toString());
                    }
                });
            }
        }).start();
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        localRender.release();
    }




    /**
     * 通过服务器给的某条信息，参照字段名解析数据并更新（多选情况下的value值为数组，其他情况为字符串）
     * 该方法调用场景为：修改时通过id获取信息
     *
     * @param
     */
    public void fillField(JSONObject jsonObject) {
        Iterator<String> it = jsonObject.keys();
        while (it.hasNext()) {
            String key = it.next();
            Object value = null;
            try {
                value = jsonObject.get(key);
                if (value instanceof JSONArray) {
                    JSONArray jsonArray = (JSONArray) value;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        sb.append(jsonArray.getString(i));
                        if (i != jsonArray.length() - 1) {
                            sb.append(",");
                        }
                    }
                    if (!StringUtil.isNull((sb.toString()))) {
                        localRender.updateItem(key, "", (sb.toString()));
                    }
                } else {
                    if (!StringUtil.isNull((value.toString()))) {
                        localRender.updateItem(key, "", value.toString());
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
    }

    public ServerDataProvider getServerDataProvider() {
        return localRender.getServerDataProvider();
    }


    @Override
    public void submit() {
        if (localRender.getServerDataProvider().validateAll()) {
            String result = localRender.getServerDataProvider().getSubmitStr();
            Toast.makeText(addDataActivity,result,Toast.LENGTH_LONG).show();
            Log.d("yeying","submit result --- "+result);
        }
    }
}
