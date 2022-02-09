package com.arronyee.sample.activity;

import android.content.Context;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.arronyee.form.DataOperation;
import com.arronyee.form.LocalRenerConfig;
import com.arronyee.sample.R;
import com.arronyee.sample.app.MvpManager;
import com.arronyee.sample.presenter.AddDataActivityPresenter;



/**
 * Created by yeying on 2018/9/11.
 */

public class AddDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_main);
        findViewById(R.id.submit).setOnClickListener(v -> getDataOperation().submit());
    }


    public DataOperation getDataOperation() {
        AddDataActivityPresenter addDataActivityPresenter = (AddDataActivityPresenter) MvpManager.findPresenter(AddDataActivity.this);
        return addDataActivityPresenter;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MvpManager.bindPresenter(this, "com.arronyee.sample.presenter.AddDataActivityPresenter");
    }

    public ViewGroup getContainer(){
        return findViewById(R.id.container);
    }

    public LocalRenerConfig getRenderConfig() {
        LocalRenerConfig localRenerConfig = new LocalRenerConfig.Build().isAdd(true).build();
        return localRenerConfig;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
