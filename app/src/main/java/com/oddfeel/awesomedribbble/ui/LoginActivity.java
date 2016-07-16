
package com.oddfeel.awesomedribbble.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.data.Constant;
import com.oddfeel.awesomedribbble.model.user.AccessToken;
import com.oddfeel.awesomedribbble.model.user.Bomb_User;
import com.oddfeel.awesomedribbble.model.user.User;
import com.oddfeel.awesomedribbble.presenter.dribbbleAuth.DribbbleAuthRetrofit;
import com.oddfeel.awesomedribbble.presenter.dribbbleAuth.DribbbleAuthService;
import com.oddfeel.awesomedribbble.presenter.user.UserRetrofit;
import com.oddfeel.awesomedribbble.presenter.user.UserService;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    private WebView webView;
    private String url;
    private FrameLayout avLoadingView;
    private String code;
    private String access_token;
    Bomb_User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bmob.initialize(this, Constant.BOMBAPPID);
        user = new Bomb_User();

        init();
    }

    private void init() {
        initData();
        initView();

    }

    private void initData() {
        url = "https://dribbble.com/oauth/authorize?client_id=41836cd29ccfb08c015b8fc884d9ae7bab31e586902a167e32092348b7ace060&scope=public+write+comment+upload";
    }

    private void initView() {
        //AVLoadingIndicatorView
        avLoadingView = (FrameLayout) findViewById(R.id.av_loading);
        //WebView
        webView = (WebView) findViewById(R.id.login_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100){//进度1-100
                    avLoadingView.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //转义必须\\,先把\转义成普通\再用普通\匹配特殊字符
                code = url.replaceAll("http://www\\.oddfeel\\.com/\\?code=","");

                SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
                if (sharedPreferences.getString("access_token","").isEmpty()){
                    getData("41836cd29ccfb08c015b8fc884d9ae7bab31e586902a167e32092348b7ace060","599a3c70e35f18b5f44d22189b2845cc6e47e6a7be7570cdb81f151316b95782",code);

                }else {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
                super.onPageFinished(view, url);
            }
        });
        webView.loadUrl(url);
    }

    private void getData(String client_id,String client_secret,String code){
        DribbbleAuthRetrofit.getRetrofit()
                .create(DribbbleAuthService.class)
                .getAccessToken(client_id,client_secret,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<AccessToken>() {
                    @Override
                    public void onCompleted() {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AccessToken accessToken) {
                        access_token = accessToken.getAccess_token();
                        SharedPreferences.Editor editor = getSharedPreferences("token",MODE_PRIVATE).edit();
                        editor.putString("access_token",access_token);
                        editor.apply();
                        user.setAccesstoken(access_token);
                        user.save(getApplicationContext());
                    }
                });
    }


/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(webView.canGoBack())
            {
                webView.goBack();//返回上一页面
                return true;
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
        return super.onKeyDown(keyCode, event);
    }*/

}
