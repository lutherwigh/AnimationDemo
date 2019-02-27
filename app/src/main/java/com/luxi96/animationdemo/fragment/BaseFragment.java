package com.luxi96.animationdemo.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luxi96.animationdemo.util.ToastUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private Unbinder mUnbinder;
    public Activity mContext;
    private long lastClickTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        mContext = this.getActivity();
        return mRootView;
    }

    public abstract int getResId();

    public abstract void init(Bundle savedInstanceState);

    public boolean isFastDoubleClick() {
        long cur = System.currentTimeMillis();
        if( cur - lastClickTime < 500){
            return true;
        }
        lastClickTime = cur;
        return false;
    }

    /**
     * 公共方法：toast short
     * @param msg
     */
    public void toast(String msg){
        ToastUtil.showShort(mContext,msg);
    }

    /**
     * 公共方法：通过tag即Fragment包名.类名 找到Fragment
     * @param tag
     * @return
     */
    public Fragment findByTag(String tag){
        return getActivity().getSupportFragmentManager().findFragmentByTag(tag);
    }

    /**
     * 公共方法：栈中所有Fragment
     * @return
     */
    public List<Fragment> findAll(){
        return getActivity().getSupportFragmentManager().getFragments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
