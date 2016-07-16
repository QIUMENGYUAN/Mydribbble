
package com.oddfeel.awesomedribbble.ui.shot;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.adapter.shots.comments.CommentsAdapter;
import com.oddfeel.awesomedribbble.model.shots.Comments;
import com.oddfeel.awesomedribbble.model.shots.Like;
import com.oddfeel.awesomedribbble.presenter.comments.CommentsRetrofit;
import com.oddfeel.awesomedribbble.presenter.comments.CommentsService;
import com.oddfeel.awesomedribbble.presenter.comments.PostCommentsRetrofit;
import com.oddfeel.awesomedribbble.presenter.comments.PostCommentsService;
import com.oddfeel.awesomedribbble.presenter.shots.LikeRetrofit;
import com.oddfeel.awesomedribbble.presenter.shots.LikeService;
import com.oddfeel.awesomedribbble.ui.user.UserInfoActivity;
import com.oddfeel.awesomedribbble.util.PatternUtil;
import com.oddfeel.awesomedribbble.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShotDetailActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,RecyclerArrayAdapter.OnLoadMoreListener {

    private String sid;
    private String title;
    private String description;
    private String views_count;
    private String likes_count;
    private String updated_at;
    private String hidpi;
    private String comments_count;
    private String buckets_count;
    private String created_at;
    private String html_url;
    private String comments_url;
    private String likes_url;
    private String projects_url;
    private String userid;
    private String name;
    private String username;
    private String user_html_url;
    private String avatar_url;
    private String bio;
    private String location;
    private String userbuckets_count;
    private String followers_count;
    private String followings_count;
    private String userlikes_count;
    private String projects_count;
    private String shots_count;
    private String teams_count;
    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String userlikes_url;
    private String userprojects_url;
    private String shots_url;
    private String teams_url;
    private String usercreated_at;
    private String userupdated_at;

    private FloatingActionButton fab;
    private ImageView image_shots_detail;
    private TextView shots_title;
    private TextView shots_description;
    private TextView shots_updated_at;
    private ImageButton button_like_count;
    private ImageButton button_view_count;
    private ImageButton button_comment_count;
    private TextView text_like_count;
    private TextView text_view_count;
    private TextView text_comment_count;
    private CircleImageView avatar;
    private TextView shots_username;
    private TextView shots_userbio;

    private EditText edit_comment;
    private ImageButton send_comment;
    private String comment;
    private String like_time;

    private List<Comments> commentsList;
    private EasyRecyclerView recyclerView;
    private CommentsAdapter commentsAdapter;
    private boolean isNetWork = true;
    private int page = 1;
    private Handler handler = new Handler();

    private int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shot_detail);

        getData();
        initView();

    }

    private void getData(){
        Bundle bundle = getIntent().getExtras();
        sid = bundle.getString("id");
        title = bundle.getString("title");
        description = bundle.getString("description");
        hidpi = bundle.getString("hidpi");
        likes_count = bundle.getString("likes_count");
        views_count = bundle.getString("views_count");
        updated_at = bundle.getString("updated_at");
        comments_count = bundle.getString("comments_count");
        buckets_count = bundle.getString("buckets_count");
        created_at = bundle.getString("created_at");
        comments_url = bundle.getString("comments_url");
        html_url = bundle.getString("html_url");
        likes_url = bundle.getString("likes_url");
        projects_url = bundle.getString("projects_url");
        userid = bundle.getString("userid");
        name = bundle.getString("name");
        username = bundle.getString("username");
        user_html_url = bundle.getString("user_html_url");
        avatar_url = bundle.getString("avatar_url");
        bio = bundle.getString("bio");
        location = bundle.getString("location");
        userbuckets_count = bundle.getString("userbuckets_count");
        followers_count = bundle.getString("followers_count");
        followings_count = bundle.getString("followings_count");
        userlikes_count = bundle.getString("userlikes_count");
        projects_count = bundle.getString("projects_count");
        shots_count = bundle.getString("shots_count");
        teams_count = bundle.getString("teams_count");
        buckets_url = bundle.getString("buckets_url");
        followers_url = bundle.getString("followers_url");
        following_url = bundle.getString("following_url");
        userlikes_url = bundle.getString("userlikes_url");
        userprojects_url = bundle.getString("userprojects_url");
        shots_url = bundle.getString("shots_url");
        teams_url = bundle.getString("teams_url");
        usercreated_at = bundle.getString("usercreated_at");
        userupdated_at = bundle.getString("userupdated_at");
    }

    private void initView(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0){
                    fab.setImageResource(R.drawable.like);
                    LikeRetrofit.getRetrofit()
                            .create(LikeService.class)
                            .like(Long.parseLong(sid))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Like>() {
                                           @Override
                                           public void onCompleted() {

                                           }

                                           @Override
                                           public void onError(Throwable e) {

                                           }

                                           @Override
                                           public void onNext(Like like) {
                                                like_time = like.getCreated_at();
                                           }
                                       });
                    Snackbar.make(view, like_time, Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                    flag = 1;
                }else {
                    fab.setImageResource(R.drawable.unlike);
                    Snackbar.make(view, "cancel like", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    flag = 0;
                }

            }
        });

        image_shots_detail = (ImageView)findViewById(R.id.image_shots_detail);
        Glide.with(this)
                .load(hidpi)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        image_shots_detail.setImageBitmap(resource);
                    }
                });

        shots_title = (TextView)findViewById(R.id.shots_title);
        shots_title.setText(title);
        shots_updated_at = (TextView)findViewById(R.id.shots_updated_at);
        shots_updated_at.setText("Update: "+TimeUtil.getdribbbleFormatTimeEn(updated_at));
        shots_description = (TextView)findViewById(R.id.shots_description);
        shots_description.setText(description);
        text_like_count = (TextView)findViewById(R.id.text_like_count);
        text_like_count.setText(likes_count+" like");
        text_view_count = (TextView)findViewById(R.id.text_view_count);
        text_view_count.setText(views_count+" view");
        text_comment_count = (TextView)findViewById(R.id.text_comment_count);
        text_comment_count.setText(comments_count+" comment");
        avatar = (CircleImageView)findViewById(R.id.avatar);
        Glide.with(this)
                .load(avatar_url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        avatar.setImageBitmap(resource);
                    }
                });
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(ShotDetailActivity.this,Shots_authorActivity.class);
                Intent intent = new Intent(ShotDetailActivity.this,UserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("name",name);
                bundle.putString("username",username);
                bundle.putString("user_html_url",user_html_url);
                bundle.putString("avatar_url",avatar_url);
                bundle.putString("bio",bio);
                bundle.putString("location",location);
                bundle.putString("userbuckets_count",userbuckets_count);
                bundle.putString("followers_count",followers_count);
                bundle.putString("followings_count",followings_count);
                bundle.putString("userlikes_count",userlikes_count);
                bundle.putString("projects_count",projects_count);
                bundle.putString("shots_count",shots_count);
                bundle.putString("teams_count",teams_count);
                bundle.putString("buckets_url",buckets_url);
                bundle.putString("followers_url",followers_url);
                bundle.putString("following_url",following_url);
                bundle.putString("userlikes_url",userlikes_url);
                bundle.putString("userprojects_url",userprojects_url);
                bundle.putString("shots_url",shots_url);
                bundle.putString("teams_url",teams_url);
                bundle.putString("usercreated_at",usercreated_at);
                bundle.putString("userupdated_at",userupdated_at);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        shots_username = (TextView)findViewById(R.id.shots_username);
        shots_username.setText(username);
        shots_userbio = (TextView)findViewById(R.id.shots_userbio);
        if (bio.isEmpty()){
            shots_userbio.setText("bio is nothing");
        }else {
            shots_userbio.setText(PatternUtil.Nohtml(bio));
        }

        edit_comment = (EditText) findViewById(R.id.edit_comment);
        send_comment = (ImageButton)findViewById(R.id.send_comment);
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = edit_comment.getText().toString();
                if (comment.isEmpty()){
                    Snackbar.make(v, "comment is nothing", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {
                    PostCommentsRetrofit.getRetrofit()
                            .create(PostCommentsService.class)
                            .postComment(Long.parseLong(sid),comment,"528f65578de5e3abdb11c5217be892062a0ee74f2a49d24d316041dad23af685");
                    edit_comment.setText(null);
                }
            }
        });

        commentsList = new ArrayList<>();
        recyclerView = (EasyRecyclerView)findViewById(R.id.recyclerview_shots_comments);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        commentsAdapter = new CommentsAdapter(this);
        dealWithAdapter(commentsAdapter);

        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    private void dealWithAdapter(final RecyclerArrayAdapter<Comments> adapter){
        recyclerView.setAdapterWithProgress(adapter);
        adapter.setMore(R.layout.load_more_layout,this);
        adapter.setNoMore(R.layout.no_more_layout);
        adapter.setError(R.layout.error_layout);
    }

    private void getcomments(String id, int page, int per_page, String access_token){
        CommentsRetrofit.getRetrofit()
                .create(CommentsService.class)
                .getComments(id,page,per_page,access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Comments>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Comments> commentses) {
                        commentsList = commentses;
                        commentsAdapter.addAll(commentsList);
                    }
                });
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                commentsAdapter.clear();
                getcomments(sid,1,3,"035eddf79698cc57ebe0fc511f484739ec3b50a1a2d75fd92bf6bc9b21d10a78");
                page = 2;
            }
        },1000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getcomments(sid, page, 3, "035eddf79698cc57ebe0fc511f484739ec3b50a1a2d75fd92bf6bc9b21d10a78");
                page++;
            }
        },1000);
    }
}
