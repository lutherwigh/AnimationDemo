package com.luxi96.animationdemo.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.luxi96.animationdemo.R;
import com.luxi96.animationdemo.view.RollingProgressView;
import com.luxi96.animationdemo.view.RoundView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AdvancePropertyFragment extends BaseFragment {

    @BindView(R.id.view1)
    RoundView view1;
    @BindView(R.id.view2)
    RollingProgressView view2;
    @BindView(R.id.title)
    TextView textView;

    String[] strings = {"颜色渐变","弹跳","缩放","缩小后平移","进度条回滚"};

    List<Animator> animators = new ArrayList<>();

    AnimatorSet animatorSet = new AnimatorSet();

    @OnClick(R.id.selector)
    public void onClick(final View view){
        switch (view.getId()){
            case R.id.selector:
                QMUIDialog qmuiDialog = new QMUIDialog.MenuDialogBuilder(getActivity())
                        .addItems(strings, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0:
                                        discoloration();
                                        break;
                                    case 1:
                                        drop();
                                        break;
                                    case 2:
                                        scacle();
                                        break;
                                    case 3:
                                        scaleAndMove();
                                        break;
                                    case 4:
                                        view1.setVisibility(View.GONE);
                                        view2.setVisibility(View.VISIBLE);
                                        rollback();
                                        break;
                                }
                                textView.setText(strings[which]);
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
        return R.layout.fragment_advance_propert;
    }

    @Override
    public void init(Bundle savedInstanceState) {

        initAnimators();

    }

    void initAnimators(){

        {
            ObjectAnimator animator = ObjectAnimator.ofArgb(view1,"color",0xffff0000, 0xff00ff00);
            animator.setDuration(2000);
            animator.setRepeatMode(ObjectAnimator.REVERSE);
            animator.setRepeatCount(100);
            animators.add(animator);
        }

        {
            ObjectAnimator animator = ObjectAnimator.ofObject(view1,"position",
                    new android.animation.PointFEvaluator(),new PointF(0,-100),
                    new PointF(0,100));
            animator.setDuration(2000);
            animator.setInterpolator(new BounceInterpolator());
            animator.setRepeatMode(ObjectAnimator.REVERSE);
            animator.setRepeatCount(100);
            animators.add(animator);
        }

        {
            PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("scaleX",1);
            PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("scaleY",1);
            PropertyValuesHolder holder3 = PropertyValuesHolder.ofFloat("alpha",1);
            ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view1,holder1,holder2,holder3);
            animators.add(animator);
        }

        animatorSet.setDuration(1000);
    }

    void switchView(int which){

    }

    public void discoloration(){
        animatorSet.cancel();
        animatorSet.playSequentially(animators);
    }

    public void drop(){
        animators.get(1).start();
    }

    public void scacle(){
        animators.get(2).start();
    }

    public void scaleAndMove(){
        // 缩小
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view1,"scaleX",1,0.3f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view1,"scaleY",1,0.3f);
        // 平移
        ObjectAnimator animator3 = ObjectAnimator.ofObject(view1,"position",
                new android.animation.PointFEvaluator(),new PointF(0,0), new PointF(400,0));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(2000);
        animatorSet.play(animator1).with(animator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
    }

    void rollback(){
        Keyframe keyframe1 = Keyframe.ofFloat(0,0);
        Keyframe keyframe2 = Keyframe.ofFloat(0.5f,100);
        Keyframe keyframe3 = Keyframe.ofFloat(1,80);
        PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("progress",
                keyframe1,keyframe2,keyframe3);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view2,holder);
        animator.setDuration(2000);
        animator.start();
    }

}
