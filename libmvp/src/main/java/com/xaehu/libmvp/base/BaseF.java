package com.xaehu.libmvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xaehu.libmvp.mvp.IView;

import java.lang.reflect.ParameterizedType;

public abstract class BaseF<P extends BaseP> extends Fragment implements IView {

    private P p;
    private final String TAG = getClass().getName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        initListener();
    }

    public P getP(){
        if(p == null){
            //实例化P层，类似于p = new P();
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            Class<P> clazz = (Class<P>) parameterizedType.getActualTypeArguments()[0];
            try {
                p = clazz.newInstance();
            }catch (Exception e){
                Log.e(TAG,e.getMessage());
            }
        }
        if(p != null){
            if(!p.isAttachedV()){
                p.attachView(this);
            }
        }
        return p;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(p != null){
            p.detachView();
            p = null;
        }
    }
}
