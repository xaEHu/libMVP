package com.xaehu.libmvp.base;

import com.xaehu.libmvp.mvp.IPresenter;
import com.xaehu.libmvp.mvp.IView;

import java.lang.ref.WeakReference;

public class BaseP<V extends IView> implements IPresenter<V> {

    private WeakReference<V> v;

    @Override
    public void attachView(V view) {
        v = new WeakReference<>(view);
    }

    @Override
    public void detachView() {
        if (v.get() != null) {
            v.clear();
        }
        v = null;
    }

    @Override
    public V getV() {
        if(v == null || v.get() == null){
            return null;
        }
        return v.get();
    }

    @Override
    public boolean isAttachedV() {
        return v != null && v.get() != null;
    }
}
