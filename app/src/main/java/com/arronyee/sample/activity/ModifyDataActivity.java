package com.arronyee.sample.activity;

import android.content.Context;
import android.os.Bundle;

import com.arronyee.form.DataOperation;
import com.arronyee.form.LocalRenerConfig;
import com.arronyee.sample.app.MvpManager;
import com.arronyee.sample.presenter.AddDataActivityPresenter;
import com.arronyee.sample.presenter.ModifyDataActivityPresenter;

import java.lang.reflect.Proxy;

/**
 * Created by yeying on 2018/9/14.
 */

public class ModifyDataActivity extends AddDataActivity {

    private ModifyDataActivityPresenter modifyDataActivityPresenterProxy;
    private DataOperation dataOperation;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddDataActivityPresenter addDataActivityPresenter = (AddDataActivityPresenter) MvpManager.findPresenter(this);
        modifyDataActivityPresenterProxy = new ModifyDataActivityPresenter(addDataActivityPresenter, this);
        dataOperation = (DataOperation) Proxy.newProxyInstance(AddDataActivityPresenter.class.getClassLoader(), AddDataActivityPresenter.class.getInterfaces(), modifyDataActivityPresenterProxy);

    }

    /**
     * 代理创建的presenter执行部分除了创建工作以外的工作
     *
     * @return
     */
    public DataOperation getDataOperation() {
        return dataOperation;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        modifyDataActivityPresenterProxy = null;
        dataOperation = null;
    }


    public LocalRenerConfig getRenderConfig() {
        LocalRenerConfig localRenerConfig = new LocalRenerConfig.Build().isAdd(false).renderCallback(() -> {
            modifyDataActivityPresenterProxy.getPeopleDetailInfo();
        }).build();
        return localRenerConfig;
    }


    public void updateViewComplete() {
//        if (ModifyDataActivityPresenter != null) {
//            ModifyDataActivityPresenter.getPeopleDetailInfo();
//        }
    }
}
