package com.kymjs.arty.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kymjs.arty.BaseActivity;
import com.kymjs.arty.R;
import com.kymjs.arty.module.login.LoginActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void skip(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
