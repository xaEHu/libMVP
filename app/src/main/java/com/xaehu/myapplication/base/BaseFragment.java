package com.xaehu.myapplication.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xaehu.libmvp.base.BaseF;
import com.xaehu.libmvp.base.BaseP;
import com.xaehu.myapplication.R;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * author : xaeHu
 * e-mail : hsfemail@qq.com
 * date   : 2019/9/9 14:41
 * desc   :
 */
public abstract class BaseFragment<P extends BaseP> extends BaseF<P> {
    private View emptyView;

    /**
     * 错误处理
     */
    public void onError(Throwable e){
        if (e instanceof UnknownHostException) {
            showLongToast("请打开网络");
        } else if (e instanceof SocketTimeoutException) {
            showLongToast("请求超时");
        } else if (e instanceof ConnectException) {
            showLongToast("连接失败");
        } else if (e instanceof HttpException) {
            showLongToast("请求超时");
        }else {
            showLongToast("请求失败:"+e.getMessage());
        }
    }

    public void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void showLongToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 设置适配器的空布局
     * @param adapter 适配器
     * @param msg 空布局文字提示
     * @param imgResId 空布局图片资源，若isLoad为true则不生效
     * @param isLoad 是否是加载中
     */
    public void setAdapterEmptyView(BaseQuickAdapter adapter, String msg, int imgResId, boolean isLoad){
        if(emptyView == null){
            emptyView = getLayoutInflater().inflate(R.layout.view_empty, null);
        }
        ((TextView)emptyView.findViewById(R.id.textView_msg)).setText(msg);
        if(isLoad){
            emptyView.findViewById(R.id.imageView_img).setVisibility(View.GONE);
            emptyView.findViewById(R.id.progressBar_loading).setVisibility(View.VISIBLE);
        }else{
            ((ImageView)emptyView.findViewById(R.id.imageView_img)).setImageResource(imgResId);
            emptyView.findViewById(R.id.imageView_img).setVisibility(View.VISIBLE);
            emptyView.findViewById(R.id.progressBar_loading).setVisibility(View.GONE);
        }
        adapter.getData().clear();
        adapter.setEmptyView(emptyView);
        adapter.notifyDataSetChanged();
    }

    /**
     * 显示错误布局
     * @param adapter recyclerView的适配器
     * @param msg 错误提示信息
     */
    public void showErrorView(BaseQuickAdapter adapter,String msg){
        //// TODO: 2019/6/5 替换错误布局图片
        setAdapterEmptyView(adapter,msg, R.mipmap.ic_launcher,false);
    }

    /**
     * 显示空布局
     * @param adapter recyclerView的适配器
     */
    public void showEmptyView(BaseQuickAdapter adapter){
        //// TODO: 2019/6/5 替换空布局图片
        setAdapterEmptyView(adapter,"空空如也",R.mipmap.ic_launcher,false);
    }

    /**
     * 显示加载中布局
     * @param adapter recyclerView的适配器
     */
    public void showLoadView(BaseQuickAdapter adapter){
        setAdapterEmptyView(adapter,"加载中……",0,true);
    }
}
