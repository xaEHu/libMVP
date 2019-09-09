package com.xaehu.myapplication.base;

import com.xaehu.libmvp.base.BaseP;
import com.xaehu.libmvp.mvp.IView;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author : xaeHu
 * e-mail : hsfemail@qq.com
 * date   : 2019/9/9 14:56
 * desc   :
 */
public class BasePresenter<V extends IView> extends BaseP<V> {

    /**
     * 网络请求的封装
     */
    protected <M> void request(Observable<M> api, final OnRespListener<M> listener){
        api.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<M>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(M value) {
                        if(getV() != null) {
                            listener.onSuccess(value);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(getV() != null){
                            listener.onFailed(e);
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
    public interface OnRespListener<M>{
        void onSuccess(M value);
        void onFailed(Throwable e);
    }

}
