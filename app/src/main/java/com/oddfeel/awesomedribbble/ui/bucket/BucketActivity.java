
package com.oddfeel.awesomedribbble.ui.bucket;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.adapter.bucket.BucketAdapter;
import com.oddfeel.awesomedribbble.model.bucket.Bucket;
import com.oddfeel.awesomedribbble.model.shots.Shots;
import com.oddfeel.awesomedribbble.presenter.bucket.BucketRetrofit;
import com.oddfeel.awesomedribbble.presenter.bucket.BucketService;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BucketActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener{

    private EasyRecyclerView recyclerView;
    private LinearLayout noWIFILayout;
    private int page = 1;
    private Handler handler = new Handler();
    private List<Bucket> bucketList;
    private BucketAdapter adapter;

    private String token;
    private long userid;

    private String[] items = {"编辑","删除"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket);

        SharedPreferences sharedPreferences1 = getSharedPreferences("token",MODE_PRIVATE);
        token = sharedPreferences1.getString("access_token","");

        SharedPreferences sharedPreferences2 = getSharedPreferences("userid",MODE_PRIVATE);
        userid = sharedPreferences2.getLong("userid",1);

        initView();

    }

    private void initView(){
        bucketList = new ArrayList<>();
        noWIFILayout = (LinearLayout) findViewById(R.id.no_network);
        recyclerView = (EasyRecyclerView) findViewById(R.id.recyclerview_buckets);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new BucketAdapter(this);
        dealWithAdapter(adapter);

        recyclerView.setRefreshListener(this);
        onRefresh();

    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Bucket> adapter){
        recyclerView.setAdapterWithProgress(adapter);

        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.setClass(BucketActivity.this,BucketShotsActivity.class);
                jumpActivity(intent,adapter,position);
            }
        });

        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        BucketActivity.this);
                //builder.setTitle("");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){

                        }else if(which == 1){
                            AlertDialog.Builder builder = new AlertDialog.Builder(
                                    BucketActivity.this);
                            builder.setMessage("删除了就失去了，请小心！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    private void jumpActivity(Intent intent, RecyclerArrayAdapter<Bucket> adapter, int position) {
        intent.putExtra("id",String.valueOf(adapter.getItem(position).getId()));
        intent.putExtra("bucketname",adapter.getItem(position).getName());
        startActivity(intent);
    }

    private void getData(long id,int page,int per_page,String token){
        BucketRetrofit.getRetrofit()
                .create(BucketService.class)
                .getBuckets(id, page, per_page, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Bucket>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Bucket> buckets) {
                        bucketList = buckets;
                        adapter.addAll(bucketList);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                getData(userid,1,6,token);
                page = 2;
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(userid,page,6,token);
                page++;
            }
        },1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.buckets, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_bucket) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
