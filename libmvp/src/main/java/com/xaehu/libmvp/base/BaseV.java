package com.xaehu.libmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xaehu.libmvp.mvp.IView;

import java.lang.reflect.ParameterizedType;

/**
 * @author xaeHu
 */
public abstract class BaseV<P extends BaseP> extends AppCompatActivity implements IView {

    private P p;
    final String Tag = getClass().toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(getWindow().getDecorView());
        initData();
        initListener();
    }

    /**
     * 获取Presenter
     */
    public P getP() {
        if (p == null) {
            //泛型形式实例化P层
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
            try {
                p = clazz.newInstance();
            } catch (Exception e) {
                Log.e(Tag, e.getMessage());
            }
        }
        if (p != null) {
            if (!p.isAttachedV()) {
                p.attachView(this);
            }
        }
        return p;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (p != null) {
            p.detachView();
            p = null;
        }
    }

}
