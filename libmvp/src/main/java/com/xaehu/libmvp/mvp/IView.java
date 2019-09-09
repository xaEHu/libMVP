package com.xaehu.libmvp.mvp;

import android.view.View;

public interface IView {

    int getLayoutId() ;

    void initView(View v);

    void initData();

    void initListener();

}
