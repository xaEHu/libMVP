package com.xaehu.myapplication.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xaehu.myapplication.R;
import com.xaehu.myapplication.adapter.SearchAdapter;
import com.xaehu.myapplication.base.BaseConstant;
import com.xaehu.myapplication.base.BaseFragment;
import com.xaehu.myapplication.bean.KugouSearch;
import com.xaehu.myapplication.presenter.SearchP;
import com.xaehu.myapplication.utils.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : xaeHu
 * @e-mail : hsfemail@qq.com
 * @date : 2019/7/9 10:40
 * @desc : 搜索的Fragment
 */
public class SearchFragment extends BaseFragment<SearchP> implements View.OnClickListener {
    private EditText editText;
    private Button button;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<KugouSearch.DataBean.InfoBean> list;
    private String name;
    private int page;
    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_list);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);
    }

    @Override
    public int getLayoutId() {
        return R.layout.acticity_search;
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        adapter = new SearchAdapter(list);
        ActivityUtils.getInstance().bindToRecyclerView(adapter,recyclerView);
        //打开可上拉加载
        adapter.setEnableLoadMore(true);
        //默认第一次加载会进入loadMore回调，如果不需要可以配置：
        adapter.disableLoadMoreIfNotFullPage();
        //滑动动画
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        //The default animation for each item only once, if you want to repeat the animation method can be called at
        adapter.isFirstOnly(false);
        //前4条不显示动画
        adapter.setNotDoAnimationCount(4);
    }

    @Override
    public void initListener() {
        button.setOnClickListener(this);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getP().search(name,page);
            }
        },recyclerView);
    }

    @Override
    public void onClick(View v) {
        page = 1;
        name = editText.getText().toString();
        showLoading();
        getP().search(name,page);
    }

    /**
     * 请求完成填充数据
     * @param kugouSearch 搜索到的内容
     */
    public void showData(KugouSearch kugouSearch) {
        if(kugouSearch.getErrcode() == 0){
            if(page == 1){
                int count = kugouSearch.getData().getTotal();
                adapter.setHeaderView(getHeaderView(count));
            }else{
                adapter.loadMoreEnd();
            }
            if(kugouSearch.getData().getInfo()!=null){
                int size = kugouSearch.getData().getInfo().size();
                if(size != 0){
                    if(page == 1){
                        list.clear();
                        page++;
                    }else{
                        if(size< BaseConstant.PAGE_SIZE){
                            adapter.loadMoreEnd();
                        }else {
                            adapter.loadMoreComplete();
                            page++;
                        }
                    }
                    adapter.addData(kugouSearch.getData().getInfo());
                    adapter.notifyDataSetChanged();
                }else{
                    adapter.loadMoreEnd();
                    showEmpty();
                }
            }else{
                if(page == 1){
                    showEmpty();
                }
            }
        }else{
            if(page != 1){
                adapter.loadMoreFail();
            }
            showToast("错误码："+kugouSearch.getErrcode());
        }
    }

    @SuppressLint("DefaultLocale")
    private View getHeaderView(int count) {
        TextView header = (TextView) getLayoutInflater().inflate(android.R.layout.simple_dropdown_item_1line,null);
        header.setText(String.format("搜索到歌曲总数量：%d",count));
        return header;
    }

    /**
     * 显示空布局（主要针对首次搜索的时候）
     */
    public void showEmpty(){
        showEmptyView(adapter);
    }
    /**
     * 显示加载布局（主要针对首次搜索的时候）
     */
    public void showLoading(){
        showLoadView(adapter);
    }

    public void loadFail() {
        adapter.loadMoreFail();
    }

    public void showError(String msg) {
        showErrorView(adapter,msg);
    }
}
