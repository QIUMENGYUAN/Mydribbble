

package com.oddfeel.awesomedribbble.ui.shot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.adapter.like.LikeShotsAdapter;
import com.oddfeel.awesomedribbble.adapter.shots.ShotsAdapter;
import com.oddfeel.awesomedribbble.model.like.Like_Shots;
import com.oddfeel.awesomedribbble.model.shots.Shots;
import com.oddfeel.awesomedribbble.presenter.shots.UserShotsRetrofit;
import com.oddfeel.awesomedribbble.presenter.shots.UserShotsService;
import com.oddfeel.awesomedribbble.util.PatternUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserShotsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener{

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private int page = 1;
    private Handler handler = new Handler();
    private List<Shots> shotsList;
    private ShotsAdapter shotsAdapter;

    private String token;
    private long userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shots);

        initView();

    }

    private void initView(){
        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
        token = sharedPreferences.getString("access_token","");

        Intent intent = getIntent();
        userid = Long.parseLong(intent.getStringExtra("userid"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("shots");

        shotsList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerview_usershots);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        shotsAdapter = new ShotsAdapter(this);
        dealWithAdapter(shotsAdapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Shots> adapter){
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
    }



    private void getData(long id,int page,int per_page,String access_token){
        UserShotsRetrofit.getRetrofit()
                .create(UserShotsService.class)
                .getShots(id, page, per_page, access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Shots>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Shots> shotses) {
                        shotsList = shotses;
                        shotsAdapter.addAll(shotsList);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                shotsAdapter.clear();
                getData(userid,1,10,token);
                page = 2;
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(userid,page,10,token);
                page++;
            }
        },1000);
    }
}
