package com.arronyee.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arronyee.sample.R;


public class MainActivity extends AppCompatActivity {
    private Button btnCreate;
    private Button btnModify;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreate = (Button) findViewById(R.id.btn_create);
        btnModify = (Button) findViewById(R.id.btn_modify);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddDataActivity.class));
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ModifyDataActivity.class);
                intent.putExtra("isAdd",false);
                startActivity(intent);

            }
        });
    }



}
