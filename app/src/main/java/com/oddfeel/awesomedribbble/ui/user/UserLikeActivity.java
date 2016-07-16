
package com.oddfeel.awesomedribbble.ui.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.adapter.like.LikeShotsAdapter;
import com.oddfeel.awesomedribbble.model.like.Like_Shots;
import com.oddfeel.awesomedribbble.model.user.User;
import com.oddfeel.awesomedribbble.presenter.like.LikeShotsRetrofit;
import com.oddfeel.awesomedribbble.presenter.like.LikeShotsService;
import com.oddfeel.awesomedribbble.presenter.user.UserRetrofit;
import com.oddfeel.awesomedribbble.presenter.user.UserService;
import com.oddfeel.awesomedribbble.ui.shot.ShotDetailActivity;
import com.oddfeel.awesomedribbble.util.PatternUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserLikeActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener{

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private int page = 1;
    private Handler handler = new Handler();
    private List<Like_Shots> like_shotsList;
    private LikeShotsAdapter likeShotsAdapter;

    private String token;
    private long userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_like);

        initView();

    }

    private void initView(){
        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
        token = sharedPreferences.getString("access_token","");

        Intent intent = getIntent();
        userid = Long.parseLong(intent.getStringExtra("userid"));

        like_shotsList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerview_likeshots);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        likeShotsAdapter = new LikeShotsAdapter(this);
        dealWithAdapter(likeShotsAdapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Like_Shots> adapter){
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(UserLikeActivity.this, ShotDetailActivity.class);
                jumpActivity(intent,adapter,position);
            }
        });
    }

    private void jumpActivity(Intent intent, RecyclerArrayAdapter<Like_Shots> adapter, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id",String.valueOf(adapter.getItem(position).getShot().getId()));
        bundle.putString("title",adapter.getItem(position).getShot().getTitle());
        bundle.putString("description", PatternUtil.Nohtml(adapter.getItem(position).getShot().getDescription()));
        bundle.putString("hidpi",adapter.getItem(position).getShot().getImages().getHidpi());
        bundle.putString("views_count",String.valueOf(adapter.getItem(position).getShot().getViews_count()));
        bundle.putString("likes_count",String.valueOf(adapter.getItem(position).getShot().getLikes_count()));
        bundle.putString("comments_count",String.valueOf(adapter.getItem(position).getShot().getComments_count()));
        bundle.putString("buckets_count",String.valueOf(adapter.getItem(position).getShot().getBuckets_count()));
        bundle.putString("created_at",adapter.getItem(position).getShot().getCreated_at());
        bundle.putString("updated_at",adapter.getItem(position).getShot().getUpdated_at());
        bundle.putString("html_url",adapter.getItem(position).getShot().getHtml_url());
        bundle.putString("comments_url",adapter.getItem(position).getShot().getComments_url());
        bundle.putString("likes_url",adapter.getItem(position).getShot().getLikes_url());
        bundle.putString("projects_url",adapter.getItem(position).getShot().getProjects_url());
        bundle.putString("userid",String.valueOf(adapter.getItem(position).getShot().getUser().getId()));
        bundle.putString("name",adapter.getItem(position).getShot().getUser().getName());
        bundle.putString("username",adapter.getItem(position).getShot().getUser().getUsername());
        bundle.putString("user_html_url",adapter.getItem(position).getShot().getUser().getHtml_url());
        bundle.putString("avatar_url",adapter.getItem(position).getShot().getUser().getAvatar_url());
        bundle.putString("bio",adapter.getItem(position).getShot().getUser().getBio());
        bundle.putString("location",adapter.getItem(position).getShot().getUser().getLocation());
        bundle.putString("userbuckets_count",String.valueOf(adapter.getItem(position).getShot().getUser().getBuckets_count()));
        bundle.putString("followers_count",String.valueOf(adapter.getItem(position).getShot().getUser().getFollowers_count()));
        bundle.putString("followings_count",String.valueOf(adapter.getItem(position).getShot().getUser().getFollowings_count()));
        bundle.putString("userlikes_count",String.valueOf(adapter.getItem(position).getShot().getUser().getLikes_count()));
        bundle.putString("projects_count",String.valueOf(adapter.getItem(position).getShot().getUser().getProjects_url()));
        bundle.putString("shots_count",String.valueOf(adapter.getItem(position).getShot().getUser().getShots_count()));
        bundle.putString("teams_count",String.valueOf(adapter.getItem(position).getShot().getUser().getTeams_count()));
        bundle.putString("buckets_url",adapter.getItem(position).getShot().getUser().getBuckets_url());
        bundle.putString("followers_url",adapter.getItem(position).getShot().getUser().getFollowers_url());
        bundle.putString("following_url",adapter.getItem(position).getShot().getUser().getFollowing_url());
        bundle.putString("userlikes_url",adapter.getItem(position).getShot().getUser().getLikes_url());
        bundle.putString("userprojects_url",adapter.getItem(position).getShot().getUser().getProjects_url());
        bundle.putString("shots_url",adapter.getItem(position).getShot().getUser().getShots_url());
        bundle.putString("teams_url",adapter.getItem(position).getShot().getUser().getTeams_url());
        bundle.putString("usercreated_at",adapter.getItem(position).getShot().getUser().getCreated_at());
        bundle.putString("userupdated_at",adapter.getItem(position).getShot().getUser().getUpdated_at());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getData(long id,int page,int per_page,String access_token){
        LikeShotsRetrofit.getRetrofit()
                .create(LikeShotsService.class)
                .getLikeShots(id,page,per_page,access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Like_Shots>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Like_Shots> like_shotses) {
                        like_shotsList = like_shotses;
                        likeShotsAdapter.addAll(like_shotsList);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                likeShotsAdapter.clear();
                getData(userid,1,3,token);
                page = 2;
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(userid,page,3,token);
                page++;
            }
        },1000);
    }

}
