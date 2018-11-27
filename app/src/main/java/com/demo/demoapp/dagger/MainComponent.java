package com.demo.demoapp.dagger;


import dagger.Component;

/**
 * Create by wxbin on 2018/11/20
 */
@Component(modules = {TestModule.class})
public interface MainComponent {
    void fill(TestDaggerActivity activity);
}
