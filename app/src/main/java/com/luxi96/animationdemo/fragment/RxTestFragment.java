package com.luxi96.animationdemo.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.luxi96.animationdemo.R;
import com.luxi96.animationdemo.util.LogUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class RxTestFragment extends BaseFragment {

    @BindView(R.id.rx_type_select)
    TextView select_tv;
    @BindView(R.id.countdown_tv)
    TextView countdown_tv;
    @BindView(R.id.rx_progress)
    SeekBar seekBar;
    @BindView(R.id.rx_et)
    EditText editText;
    @BindView(R.id.rx_hint_tv)
    TextView hint_tv;

    String[] strings = {"定时","倒计时","模拟下载","计算平均值","输入联想","缓存"};

    PublishSubject<Double> mComputePublishSubject;
    CompositeDisposable computeDisposable;

    PublishSubject inputSubject;


    Handler sendNumHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            double temp = Math.random() * 25 + 5;
            LogUtils.d( "温度测量结果：" + temp);
            mComputePublishSubject.onNext(temp);

            sendNumHandler.sendEmptyMessageDelayed(0,250 + (long)(250 * Math.random()));
            return false;
        }
    });

    @OnClick(R.id.rx_type_select)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rx_type_select:
                QMUIDialog qmuiDialog = new QMUIDialog.MenuDialogBuilder(getActivity())
                        .addItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        setTimer();
                                        break;
                                    case 1:
                                        countDown();
                                    break;
                                    case 2:
                                        startDownload();
                                        break;
                                    case 3:
                                        computeAverageValue();
                                        break;
                                    case 4:
                                        inputAssociate();
                                        break;
                                    case 5:
                                        cache();
                                        break;
                                }
                                select_tv.setText(strings[which]);
                                dialog.dismiss();
                            }
                        })
                        .create();
                qmuiDialog.show();
                break;
        }
    }

    @Override
    public int getResId() {
        return R.layout.fragment_rx_test;
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }


    /**
     * 定时
     */
    void setTimer(){

        final long start = System.currentTimeMillis();

        final QMUITipDialog dialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("定时")
                .create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();

        LogUtils.d("start" + start);
        Observable.timer(10 * 1000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {

                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        LogUtils.d("onNext " + System.currentTimeMillis());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mDisposable.dispose();
                        if(dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        LogUtils.d("cost:" + (System.currentTimeMillis() - start));
                    }
                });
    }

    /**
     * 倒计时
     */
    void countDown(){
        countdown_tv.setVisibility(View.VISIBLE);
        final int count_time = 10;
        Observable.interval(0,1,TimeUnit.SECONDS)
                .take(count_time)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        countdown_tv.setText(String.valueOf(count_time - aLong));
                        return count_time - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    Disposable mDisposable;
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {}

                    @Override
                    public void onError(Throwable e) {}

                    @Override
                    public void onComplete() {
                        countdown_tv.setVisibility(View.GONE);
                        mDisposable.dispose();
                    }
                });
    }

    /**
     * 模拟下载进度
     */
    void downloadSimulation(){
        seekBar.setVisibility(View.VISIBLE);
        countdown_tv.setVisibility(View.VISIBLE);

        Observable<Integer> observable = Observable.create(
                new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for(int i = 0; i<= 120 ;i++){
                    if(i % 20 == 0){
                        try{
                            if(i == 120){
                                Thread.sleep(2000);
                            } else {
                                Thread.sleep(500);
                            }
                        }catch (InterruptedException exception){
                            if(!emitter.isDisposed()){
                                emitter.onError(exception);
                            }
                        }
                        emitter.onNext(i);
                    }
                }
                emitter.onComplete();
            }
        });

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                LogUtils.d("download onNext:" + integer);
                seekBar.setProgress(integer);
                if(integer == 100) {
                    countdown_tv.setText("Download onComplete");
                }else {
                    countdown_tv.setText(integer + " %");
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d( "onError=" + e);
                countdown_tv.setText("Download Error");
            }

            @Override
            public void onComplete() {
                LogUtils.d( "onComplete");

                seekBar.setVisibility(View.INVISIBLE);
                countdown_tv.setVisibility(View.INVISIBLE);
            }
        });
    }

    // 模拟下载的操作
    private void startDownload() {

        seekBar.setVisibility(View.VISIBLE);
        countdown_tv.setVisibility(View.VISIBLE);

        final Observable<Integer> observable = Observable.create(
                new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i <= 120; i++) {
                    if (i % 20 == 0) {
                        try {
                            if(i == 120){
                                Thread.sleep(2000);
                            } else {
                                Thread.sleep(500);
                            }
                        } catch (InterruptedException exception) {
                            if (!e.isDisposed()) {
                                e.onError(exception);
                            }
                        }
                        e.onNext(i);
                    }
                }
                e.onComplete();
            }

        });
        DisposableObserver<Integer> disposableObserver = new DisposableObserver<Integer>() {

            @Override
            public void onNext(Integer value) {
                LogUtils.d("onNext=" + value);
                seekBar.setProgress(value);
                if(value == 100) {
                    countdown_tv.setText("Download onComplete");
                }else {
                    countdown_tv.setText(value + " %");
                }
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.d( "onError=" + e);
                countdown_tv.setText("Download Error");
            }

            @Override
            public void onComplete() {
                LogUtils.d( "onComplete");

                seekBar.setVisibility(View.INVISIBLE);
                countdown_tv.setVisibility(View.INVISIBLE);

            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
//        mCompositeDisposable.add(disposableObserver);
    }

    /**
     * 计算一段时间内平均值
     */
    void computeAverageValue(){
        final QMUITipDialog dialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("tip")
                .create();

        final QMUIDialog dialog1 = new QMUIDialog.MessageDialogBuilder(getContext())
                .setMessage("message")
                .setTitle("平均值")
                .addAction(new QMUIDialogAction(getContext(), "取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        sendNumHandler.removeCallbacksAndMessages(null);
                        computeDisposable.clear();
                        dialog.dismiss();
                    }
                }))
                .create();

        mComputePublishSubject = PublishSubject.create();
        DisposableObserver<List<Double>> disposableObserver = new DisposableObserver<List<Double>>() {
            @Override
            public void onNext(List<Double> doubles) {
                double result = 0;
                if(doubles.size() > 0){
                    for(Double d : doubles){
                        result += d;
                    }
                    result = result / doubles.size();
                    toast("过去3秒收到了" + doubles.size() + "个数据， 平均温度为：" + result);
                    if (!dialog1.isShowing()) {
                        dialog1.show();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        mComputePublishSubject.buffer(3000,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);

        computeDisposable = new CompositeDisposable();
        computeDisposable.add(disposableObserver);
        sendNumHandler.sendEmptyMessage(0);

    }

    /**
     * 输入联想
     */
    @SuppressLint("CheckResult")
    void inputAssociate(){
        editText.setVisibility(View.VISIBLE);
        hint_tv.setVisibility(View.VISIBLE);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(inputSubject != null) {
                    inputSubject.onNext(s);
                }
            }
        });

        inputSubject = PublishSubject.create();
        inputSubject.debounce(200,TimeUnit.MILLISECONDS)
                .filter(new Predicate<String>(){
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.length() > 0;
                    }
                })
                .switchMap(new Function<String,ObservableSource<String>>(){
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return getSearchObsevable(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    void cache(){

    }

    Observable<String> getSearchObsevable(final String query){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LogUtils.d("开始搜索:" + query);
                try{
                    Thread.sleep(100 + (long)(Math.random() * 500));
                }catch (InterruptedException  e){
                    if(!emitter.isDisposed()){
                        emitter.onError(e);
                    }
                }
                LogUtils.d("结束搜索:" + query);
                emitter.onNext("完成搜索:" + query);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
    }

}
