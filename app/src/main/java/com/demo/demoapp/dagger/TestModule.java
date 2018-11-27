package com.demo.demoapp.dagger;

import dagger.Module;
import dagger.Provides;

/**
 * Create by wxbin on 2018/11/20
 */
@Module
public class TestModule {
    private String str;
    public TestModule(String str){
        this.str = str;
    }
    @Provides
    public TestPresenter getPresenter(){
        return new TestPresenter();
    }
}
