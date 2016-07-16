
package com.oddfeel.awesomedribbble.ui.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.ui.MyLike.MyLikeActivity;
import com.oddfeel.awesomedribbble.ui.bucket.BucketActivity;
import com.oddfeel.awesomedribbble.ui.bucket.BucketShotsActivity;
import com.oddfeel.awesomedribbble.ui.bucket.UserBucketsActivity;
import com.oddfeel.awesomedribbble.ui.following.FollowingsActivity;
import com.oddfeel.awesomedribbble.ui.shot.UserShotsActivity;
import com.oddfeel.awesomedribbble.util.PatternUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends AppCompatActivity {

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

    private CircleImageView avatar;
    private TextView shots_username;
    private TextView shots_userbio;
    private TextView textView_location;
    private TextView textView_shots_count;
    private TextView textView_userlikes_count;
    private TextView textView_followings_count;
    private TextView textView_followers_count;
    private TextView textView_userbuckets_count;
    private TextView textView_teams_count;

    LinearLayout shots;
    LinearLayout likes;
    LinearLayout followings;
    LinearLayout followers;
    LinearLayout buckets;
    LinearLayout teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        getData();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        initView();
    }

    private void getData(){
        Bundle bundle = getIntent().getExtras();
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

        shots_username = (TextView)findViewById(R.id.shots_username);
        shots_username.setText(username);
        shots_userbio = (TextView)findViewById(R.id.shots_userbio);
        if (bio.isEmpty()){
            shots_userbio.setText("TA还没有填写简介哦");
        }else {
            shots_userbio.setText(PatternUtil.Nohtml(bio));
        }

        textView_location = (TextView)findViewById(R.id.location);
        if (location == null){
            textView_location.setText("TA还没有填写地址");
        }else {
            textView_location.setText(location);
        }

        textView_shots_count = (TextView)findViewById(R.id.shots_count);
        textView_shots_count.setText("作品数："+shots_count);
        shots = (LinearLayout) findViewById(R.id.shots);
        shots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserShotsActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        textView_userlikes_count = (TextView)findViewById(R.id.userlikes_count);
        textView_userlikes_count.setText("喜欢数："+userlikes_count);
        likes = (LinearLayout)findViewById(R.id.likes);
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserLikeActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        textView_followings_count = (TextView)findViewById(R.id.followings_count);
        textView_followings_count.setText("Ta的关注："+followings_count);
        followings = (LinearLayout)findViewById(R.id.followings);
        followings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, FollowingsActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        textView_followers_count = (TextView)findViewById(R.id.followers_count);
        textView_followers_count.setText("粉丝："+followers_count);
        followers = (LinearLayout)findViewById(R.id.followers);
        followers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        textView_userbuckets_count = (TextView)findViewById(R.id.userbuckets_count);
        textView_userbuckets_count.setText("收藏夹："+userbuckets_count);
        buckets = (LinearLayout)findViewById(R.id.buckets);
        buckets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, UserBucketsActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
            }
        });
        textView_teams_count = (TextView)findViewById(R.id.teams_count);
        textView_teams_count.setText("加入团队："+teams_count);
        teams = (LinearLayout)findViewById(R.id.teams);
        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(teams_count) == 0){
                    Snackbar.make(v, "你还没有加入任何团队", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else {

                }

            }
        });


    }
}
