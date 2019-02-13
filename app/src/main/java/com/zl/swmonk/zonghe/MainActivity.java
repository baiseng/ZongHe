package com.zl.swmonk.zonghe;

import android.Manifest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zl.swmonk.zonghe.audio.AudioFragment;
import com.zl.swmonk.zonghe.base.ui.BaseActivity;
import com.zl.swmonk.zonghe.joke.JokeFragment;
import com.zl.swmonk.zonghe.video.VideoFragment;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.navigation)
    BottomNavigationView bottomNavigationView;

    Fragment videoFragment;
    Fragment jokeFragment;
    Fragment audioFragment;
    Fragment[] fragmentList;
    int lastShowFragment=0;

    Disposable disPermission;


    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        jokeFragment=new JokeFragment();
        videoFragment=new VideoFragment();
        audioFragment=new AudioFragment();
        fragmentList=new Fragment[]{jokeFragment,videoFragment,audioFragment};


        setSupportActionBar(toolbar);

        fab.setOnClickListener((view) ->{
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener((menuItem)-> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    switchFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    switchFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    switchFragment(2);
                    return true;
            }
            return false;
        });



        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragment,jokeFragment)
                .commit();

    }


    @Override
    protected void initData() {

        disPermission=new RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted->{
                    if (granted){
                        //Toast.makeText(this,"同意读取内存",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"不同意权限",Toast.LENGTH_SHORT).show();
                    }
                });
    }



    public void switchFragment(int newIndex){
        if(lastShowFragment!=newIndex){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.hide(fragmentList[lastShowFragment]);
            if (!fragmentList[newIndex].isAdded()){
                transaction.add(R.id.content_fragment,fragmentList[newIndex]);
            }
            transaction.show(fragmentList[newIndex]);
            transaction.commitAllowingStateLoss();
            lastShowFragment=newIndex;
        }
    }


    @Override
    public void onBackPressed() {
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disPermission.dispose();
    }
}
