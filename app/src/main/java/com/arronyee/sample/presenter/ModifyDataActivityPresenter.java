package com.arronyee.sample.presenter;

import android.util.Log;
import android.widget.Toast;

import com.arronyee.sample.activity.ModifyDataActivity;
import com.arronyee.form.data.ServerDataProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Created by yeying on 2018/9/17.
 */

public class ModifyDataActivityPresenter implements InvocationHandler {

    private AddDataActivityPresenter addDataActivityPresenter;
    private ServerDataProvider serverDataProvider;
    private ModifyDataActivity mActivity;

    public ModifyDataActivityPresenter(AddDataActivityPresenter addDataActivityPresenter, ModifyDataActivity context) {
        this.addDataActivityPresenter = addDataActivityPresenter;
        serverDataProvider = addDataActivityPresenter.getServerDataProvider();
        mActivity = context;
    }

    /**
     * 代理了部分接口实现修改的业务逻辑
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("submit")) {
            //由创建请求替换成修改请求
            update();
        }else {
            //不做调整
            return method.invoke(addDataActivityPresenter, args);
        }
        return null;
    }

    /**
     * 获取人员具体信息,詳情接口
     */
    public void getPeopleDetailInfo() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name","測試");
            jsonObject.put("personType","1");
            jsonObject.put("csType","1,2");
            jsonObject.put("isYes","0");
            jsonObject.put("date","2020-01-01");
            jsonObject.put("remark","測試備注");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addDataActivityPresenter.fillField(jsonObject);

    }


    /**
     * 调用更新接口
     */
    public void update() {
        if (serverDataProvider.validateAll()) {
            String result = serverDataProvider.getSubmitStr();
            Toast.makeText(mActivity,result,Toast.LENGTH_LONG).show();
            Log.d("yeying","update result --- "+result);
        }
    }
}
