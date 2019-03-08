package com.luxi96.animationdemo.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.luxi96.animationdemo.R;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LabFragment extends BaseFragment {

    @BindView(R.id.lab_selector)
    TextView selectorView;
    @BindView(R.id.lab_loading_text)
    TextView loadingText;

    String[] strings = {"loading"};

    QMUITipDialog mLoadingDialog;

    @Override
    public int getResId() {
        return R.layout.fragment_lab;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.lab_selector)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.lab_selector:
                QMUIDialog qmuiDialog = new QMUIDialog.MenuDialogBuilder(getActivity())
                        .addItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        rxLoading();
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;
                                    case 4:

                                        break;
                                }
                                selectorView.setText(strings[which]);
                                dialog.dismiss();
                            }
                        })
                        .create();
                qmuiDialog.show();
                break;
        }
    }

    /**
     * rx风格显示loading界面
     */
    void rxLoading(){

        loadingText.setVisibility(View.VISIBLE);
        showLoadingDialog(true);

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try{
                    Thread.sleep(1000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                emitter.onNext("sadaifiowaufbiu");
                if(!emitter.isDisposed()){
                    emitter.onComplete();
                }
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {

                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String strings) {
                        loadingText.setText("加载完成");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(mLoadingDialog.isShowing()){
                            showLoadingDialog(false);
                        }
                    }

                    @Override
                    public void onComplete() {
                        loadingText.setVisibility(View.INVISIBLE);
                        if(mLoadingDialog.isShowing()){
                            showLoadingDialog(false);
                        }
                        mDisposable.dispose();
                    }
                });

    }

    public void showLoadingDialog(boolean isShowing){
        if(isShowing){
            if(mLoadingDialog == null) {
                mLoadingDialog = new QMUITipDialog.Builder(getContext())
                        .setTipWord(getString(R.string.loading))
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .create();
            }
            mLoadingDialog.show();
        }else {
            if(mLoadingDialog != null){
                mLoadingDialog.dismiss();
            }
        }
    }

}
