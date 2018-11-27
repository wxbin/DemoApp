package com.demo.demoapp.dagger;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

/**
 * Create by wxbin on 2018/11/20
 */
public class TestDaggerActivity extends Activity {
    @Inject  TestPresenter mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainComponent.builder().testModule(new TestModule("")).build().fill(this);
        Log.d("wxbin", mPresenter.hashCode() + "");
    }
}
