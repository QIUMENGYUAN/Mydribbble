
package com.oddfeel.awesomedribbble.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.shots.Shots;
import com.oddfeel.awesomedribbble.presenter.shots.ShotsRetrofit;
import com.oddfeel.awesomedribbble.presenter.shots.ShotsService;

import java.util.List;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        init();
    }

    private void init() {
        image = (ImageView) findViewById(R.id.welcome_image);

        ShotsRetrofit.getRetrofit()
                .create(ShotsService.class)
                .getShots("popular","popularity","now",1,1,"035eddf79698cc57ebe0fc511f484739ec3b50a1a2d75fd92bf6bc9b21d10a78")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {
                        animateImage();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Glide.with(SplashActivity.this)
                                .load(R.drawable.wifi)
                                .into(image);
                        animateImage();
                    }

                    @Override
                    public void onNext(List<Shots> shotses) {
                        Glide.with(SplashActivity.this)
                                .load(shotses.get(0).getImages().getHidpi())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(image);
                    }
                });

    }

    private void animateImage() {
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.1f,1.0f,1.1f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(3000);
        image.startAnimation(scaleAnimation);

        //缩放动画监听
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                //startActivity(new Intent(SplashActivity.this,MainActivity.class));
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));

                overridePendingTransition(0,0);

                SplashActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
