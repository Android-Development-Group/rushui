package com.pupu.rushui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pupu.rushui.R;
import com.pupu.rushui.base.BaseActivity;
import com.pupu.rushui.contract.MainContract;
import com.pupu.rushui.presenter.MainPresenter;
import com.pupu.rushui.service.SleepService;
import com.pupu.rushui.widget.SlideAlphaView;
import com.pupu.rushui.widget.TimeDiskView;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    @Bind(R.id.btn_startSleep)
    Button btn_startSleep;
    Animation breathAnim;
    @Bind(R.id.layout_bottomBtn)
    View layout_bottomBtn;
    @Bind(R.id.layout_sleeping)
    SlideAlphaView layout_sleeping;
    @Bind(R.id.iv_playController)
    ImageView iv_playController;
    @Bind(R.id.layout_timeController)
    View layout_timeController;
    @Bind(R.id.tv_timeController)
    TextView tv_timeController;
    @Bind(R.id.tdv)
    TimeDiskView tdv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        getPresenter().initSleep();
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new MainPresenter();
        }
        return (MainContract.Presenter) this.presenter;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.btn_startSleep, R.id.iv_mine, R.id.iv_setting, R.id.iv_playController})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_playController:
                getPresenter().controlPlay();
                break;
            case R.id.btn_startSleep:
                getPresenter().startSleep();
                break;
            case R.id.iv_mine:
                break;
            case R.id.iv_setting:
                start2Activity(SettingActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void startBtnSleepAnim() {
        if (breathAnim != null) {
            breathAnim.cancel();
            breathAnim = null;
        }
        breathAnim = new ScaleAnimation(1.1f, 1f, 1.1f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        breathAnim.setRepeatMode(Animation.REVERSE);
        breathAnim.setRepeatCount(Animation.INFINITE);
        breathAnim.setDuration(2500);
        breathAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        btn_startSleep.startAnimation(breathAnim);
    }

    @Override
    public void initSleep() {
        tv_title.setText(R.string.app_name);

        startBtnSleepAnim();

        layout_sleeping.setOnSlideLisenter(new SlideAlphaView.OnSlideLisenter() {
            @Override
            public void onSlideOver(int deta) {
                if (deta >= 600) {
                    //停止睡眠
                    getPresenter().stopSleep();
                }
            }
        });
        tdv.start();
    }

    @Override
    public void startSleep() {
        //隐藏底部三个按钮
        btn_startSleep.clearAnimation();
        Animation alphaAnim = new AlphaAnimation(1f, 0f);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layout_bottomBtn.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        alphaAnim.setDuration(1500);
        layout_bottomBtn.startAnimation(alphaAnim);

        //显示播放按钮
        Animation alphaAnim2 = new AlphaAnimation(0f, 1f);
        alphaAnim2.setDuration(1500);
        alphaAnim2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layout_sleeping.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        layout_sleeping.startAnimation(alphaAnim2);

        startService(new Intent(this, SleepService.class));
    }

    @Override
    public void stopSleep() {
        layout_bottomBtn.setVisibility(View.VISIBLE);
        start2Activity(SleepResultActivity.class);
    }

    @Override
    public void pausePlay() {
        iv_playController.setImageResource(R.mipmap.img_pause);
    }

    @Override
    public void resumePlay() {
        iv_playController.setImageResource(R.mipmap.img_play);
    }

}
