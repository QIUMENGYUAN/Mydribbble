
package com.oddfeel.awesomedribbble.ui.following;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.adapter.shots.ShotsAdapter;
import com.oddfeel.awesomedribbble.model.shots.Shots;
import com.oddfeel.awesomedribbble.presenter.following.FollowingShotsRetrofit;
import com.oddfeel.awesomedribbble.presenter.following.FollowingShotsService;
import com.oddfeel.awesomedribbble.presenter.shots.ShotsRetrofit;
import com.oddfeel.awesomedribbble.presenter.shots.ShotsService;
import com.oddfeel.awesomedribbble.ui.shot.ShotDetailActivity;
import com.oddfeel.awesomedribbble.util.PatternUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FollowingShotsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener {

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private List<Shots> shotsList;
    private ShotsAdapter shotsAdapter;

    private int page = 1;
    private Handler handler = new Handler();

    private String token;
    private long userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following_shots);

        SharedPreferences sharedPreferences1 = getSharedPreferences("token",MODE_PRIVATE);
        token = sharedPreferences1.getString("access_token","");

        SharedPreferences sharedPreferences2 = getSharedPreferences("userid",MODE_PRIVATE);
        userid = sharedPreferences2.getLong("userid",1);

        initView();
    }

    private void initView() {
        shotsList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerview_main);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        shotsAdapter = new ShotsAdapter(this);

        dealWithAdapter(shotsAdapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Shots> adapter) {
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(FollowingShotsActivity.this, ShotDetailActivity.class);
                jumpActivity(intent,adapter,position);

            }
        });
    }

    private void jumpActivity(Intent intent, RecyclerArrayAdapter<Shots> adapter, int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id",String.valueOf(adapter.getItem(position).getId()));
        bundle.putString("title",adapter.getItem(position).getTitle());
        bundle.putString("description", PatternUtil.Nohtml(adapter.getItem(position).getDescription()));
        bundle.putString("hidpi",adapter.getItem(position).getImages().getHidpi());
        bundle.putString("views_count",String.valueOf(adapter.getItem(position).getViews_count()));
        bundle.putString("likes_count",String.valueOf(adapter.getItem(position).getLikes_count()));
        bundle.putString("comments_count",String.valueOf(adapter.getItem(position).getComments_count()));
        bundle.putString("buckets_count",String.valueOf(adapter.getItem(position).getBuckets_count()));
        bundle.putString("created_at",adapter.getItem(position).getCreated_at());
        bundle.putString("updated_at",adapter.getItem(position).getUpdated_at());
        bundle.putString("html_url",adapter.getItem(position).getHtml_url());
        bundle.putString("comments_url",adapter.getItem(position).getComments_url());
        bundle.putString("likes_url",adapter.getItem(position).getLikes_url());
        bundle.putString("projects_url",adapter.getItem(position).getProjects_url());
        bundle.putString("userid",String.valueOf(adapter.getItem(position).getUser().getId()));
        bundle.putString("name",adapter.getItem(position).getUser().getName());
        bundle.putString("username",adapter.getItem(position).getUser().getUsername());
        bundle.putString("user_html_url",adapter.getItem(position).getUser().getHtml_url());
        bundle.putString("avatar_url",adapter.getItem(position).getUser().getAvatar_url());
        bundle.putString("bio",adapter.getItem(position).getUser().getBio());
        bundle.putString("location",adapter.getItem(position).getUser().getLocation());
        bundle.putString("userbuckets_count",String.valueOf(adapter.getItem(position).getUser().getBuckets_count()));
        bundle.putString("followers_count",String.valueOf(adapter.getItem(position).getUser().getFollowers_count()));
        bundle.putString("followings_count",String.valueOf(adapter.getItem(position).getUser().getFollowings_count()));
        bundle.putString("userlikes_count",String.valueOf(adapter.getItem(position).getUser().getLikes_count()));
        bundle.putString("projects_count",String.valueOf(adapter.getItem(position).getUser().getProjects_url()));
        bundle.putString("shots_count",String.valueOf(adapter.getItem(position).getUser().getShots_count()));
        bundle.putString("teams_count",String.valueOf(adapter.getItem(position).getUser().getTeams_count()));
        bundle.putString("buckets_url",adapter.getItem(position).getUser().getBuckets_url());
        bundle.putString("followers_url",adapter.getItem(position).getUser().getFollowers_url());
        bundle.putString("following_url",adapter.getItem(position).getUser().getFollowing_url());
        bundle.putString("userlikes_url",adapter.getItem(position).getUser().getLikes_url());
        bundle.putString("userprojects_url",adapter.getItem(position).getUser().getProjects_url());
        bundle.putString("shots_url",adapter.getItem(position).getUser().getShots_url());
        bundle.putString("teams_url",adapter.getItem(position).getUser().getTeams_url());
        bundle.putString("usercreated_at",adapter.getItem(position).getUser().getCreated_at());
        bundle.putString("userupdated_at",adapter.getItem(position).getUser().getUpdated_at());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void getData(int page,int per_page,String access_token) {
        FollowingShotsRetrofit.getRetrofit()
                .create(FollowingShotsService.class)
                .getFollowingShots(page, per_page, access_token)
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
                getData(1,10,token);
                page = 2;
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(page,10,token);
                page++;
            }
        }, 1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.followings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_designers) {
            Intent intent = new Intent();
            intent.setClass(FollowingShotsActivity.this,FollowingsActivity.class);
            intent.putExtra("userid",String.valueOf(userid));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
