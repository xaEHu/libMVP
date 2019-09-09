package com.xaehu.myapplication.presenter;

import com.xaehu.myapplication.activity.SearchActivity;
import com.xaehu.myapplication.api.Api;
import com.xaehu.myapplication.base.BaseConstant;
import com.xaehu.myapplication.base.BasePresenter;
import com.xaehu.myapplication.bean.KugouSearch;

import java.util.HashMap;
import java.util.Map;

public class SearchPresenter extends BasePresenter<SearchActivity> {

    public void search(String name,final int page){
        if(name.isEmpty()){
            getV().showToast("内容不能为空");
            return;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("keyword",name);
        map.put("page",page);
        map.put("pagesize", BaseConstant.PAGE_SIZE);
        request(Api.getApiService().searchKugou(map), new OnRespListener<KugouSearch>() {
            @Override
            public void onSuccess(KugouSearch value) {
                getV().showData(value);
            }

            @Override
            public void onFailed(Throwable e) {
                getV().onError(e);
                if(page == 1){
                    getV().showError(e.getMessage());
                }else{
                    getV().loadFail();
                }
            }
        });
    }
}
