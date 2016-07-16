
package com.oddfeel.awesomedribbble.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.oddfeel.awesomedribbble.R;
import com.oddfeel.awesomedribbble.model.user.User;
import com.oddfeel.awesomedribbble.presenter.user.UserRetrofit;
import com.oddfeel.awesomedribbble.presenter.user.UserService;
import com.oddfeel.awesomedribbble.ui.MyLike.MyLikeActivity;
import com.oddfeel.awesomedribbble.ui.about.AboutAPPActivity;
import com.oddfeel.awesomedribbble.ui.bucket.BucketActivity;
import com.oddfeel.awesomedribbble.ui.following.FollowingShotsActivity;
import com.oddfeel.awesomedribbble.ui.settings.SettingsActivity;
import com.oddfeel.awesomedribbble.ui.shot.MainFragment;
import com.oddfeel.awesomedribbble.ui.shot.UserShotsActivity;
import com.oddfeel.awesomedribbble.ui.user.UserInfoActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    View headerview;
    TextView username;
    TextView bio;
    CircleImageView headerimg;
    private List<Fragment> fragments;
    private String[] titles = {"Popular","Teams","Animated","Debuts"};

    Realm realm;
    private String access_token;
    private long id;
    private long exitTime = 0;

    private String userid;
    private String name;
    private String user_name;
    private String userbio;
    private String user_html_url;
    private String avatar_url;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("token",MODE_PRIVATE);
        access_token = sharedPreferences.getString("access_token","");

        SharedPreferences sharedPreferences1 = getSharedPreferences("userid",MODE_PRIVATE);
        id = sharedPreferences1.getLong("userid",1);

        initView();
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //List
        fragments = new ArrayList<>();
        for (String title : titles){
            fragments.add(MainFragment.getInstance(title));
        }

        //ViewPager
        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        //TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        //headerview
        headerview = navigationView.getHeaderView(0);//需要转化成一个view
        username = (TextView)headerview.findViewById(R.id.textView_username);
        bio = (TextView)headerview.findViewById(R.id.textView_bio);
        headerimg = (CircleImageView) headerview.findViewById(R.id.nav_headerimg);

        getData(access_token);
        headerimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, UserInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",userid);
                bundle.putString("name",name);
                bundle.putString("username",user_name);
                bundle.putString("user_html_url",user_html_url);
                bundle.putString("avatar_url",avatar_url);
                bundle.putString("bio",userbio);
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
    }

    private void getData(String access_token) {
        UserRetrofit.getRetrofit()
                .create(UserService.class)
                .getUserInfo(access_token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(User user) {
                        Glide.with(getApplicationContext())
                                .load(user.getAvatar_url())
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                .into(headerimg);
                        username.setText(user.getUsername());
                        if (user.getBio().isEmpty()){
                            bio.setText("你还没有填写简介哦");
                        }else {
                            bio.setText(user.getBio());
                        }

                        userid = String.valueOf(user.getId());
                        name = user.getName();
                        user_name = user.getUsername();
                        if (user.getBio() == null){
                            userbio = "你还没有填写简介哦";
                        }else {
                            userbio = user.getBio();
                        }
                        user_html_url = user.getHtml_url();
                        avatar_url = user.getAvatar_url();
                        if (user.getLocation() == null){
                            location = "你还没有填写地址";
                        }else {
                            location = user.getLocation();
                        }
                        userbuckets_count = String.valueOf(user.getBuckets_count());
                        followers_count = String.valueOf(user.getFollowers_count());
                        followings_count = String.valueOf(user.getFollowings_count());
                        userlikes_count = String.valueOf(user.getLikes_count());
                        projects_count = String.valueOf(user.getProjects_url());
                        shots_count = String.valueOf(user.getShots_count());
                        teams_count = String.valueOf(user.getTeams_count());
                        buckets_url = user.getBuckets_url();
                        followers_url = user.getFollowers_url();
                        following_url = user.getFollowing_url();
                        userlikes_url = user.getLikes_url();
                        userprojects_url = user.getProjects_url();
                        shots_url = user.getShots_url();
                        teams_url = user.getTeams_url();
                        usercreated_at = user.getCreated_at();
                        userupdated_at = user.getUpdated_at();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shots) {
        } else if (id == R.id.nav_designers) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, FollowingShotsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_myshots) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, UserShotsActivity.class);
            intent.putExtra("userid",String.valueOf(id));
            startActivity(intent);
        } else if (id == R.id.nav_buckets) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, BucketActivity.class);
            startActivity(intent);
        } /*else if (id == R.id.nav_teams) {

        } else if (id == R.id.nav_project) {

        } */else if (id == R.id.nav_likes) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, MyLikeActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_settings) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, SettingsActivity.class);
            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (id == R.id.nav_aboutApp) {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, AboutAPPActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        }else {
            finish();//直接退出
        }
    }


}
