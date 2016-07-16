package com.oddfeel.awesomedribbble.ui.following;

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
import com.oddfeel.awesomedribbble.adapter.following.FollowingsAdapter;
import com.oddfeel.awesomedribbble.adapter.shots.ShotsAdapter;
import com.oddfeel.awesomedribbble.model.following.Followings;
import com.oddfeel.awesomedribbble.model.shots.Shots;
import com.oddfeel.awesomedribbble.presenter.following.FollowingsRetrofit;
import com.oddfeel.awesomedribbble.presenter.following.FollowingsService;
import com.oddfeel.awesomedribbble.ui.shot.ShotDetailActivity;
import com.oddfeel.awesomedribbble.ui.shot.UserShotsActivity;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowingsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener {

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<Followings> followingsList;
    private FollowingsAdapter followingsAdapter;

    private int page = 1;
    private Handler handler = new Handler();

    private long userid;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followings);

        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
        token = sharedPreferences.getString("access_token","");

        Intent intent = getIntent();
        userid = Long.parseLong(intent.getStringExtra("userid"));

        initView();
    }

    private void initView(){
        followingsList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerview_followings);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        followingsAdapter = new FollowingsAdapter(this);

        dealWithAdapter(followingsAdapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Followings> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(FollowingsActivity.this, UserShotsActivity.class);
                jumpActivity(intent,adapter,position);

            }
        });
    }

    private void jumpActivity(Intent intent, RecyclerArrayAdapter<Followings> adapter, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("userid",String.valueOf(adapter.getItem(position).getFollowee().getId()));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getData(long id,int page,int per_page,String access_token){
        FollowingsRetrofit.getRetrofit()
                .create(FollowingsService.class)
                .getFollowings(id, page, per_page, access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Followings>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Followings> followingses) {
                        followingsList = followingses;
                        followingsAdapter.addAll(followingsList);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                followingsAdapter.clear();
                getData(userid,1,10,token);
                page = 2;
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(userid,page,10,token);
                page++;
            }
        }, 1000);
    }
}
