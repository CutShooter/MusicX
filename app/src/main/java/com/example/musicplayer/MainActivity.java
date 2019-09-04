package com.example.musicplayer;

import android.Manifest;
import android.animation.Animator;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.transition.Fade;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("My Music Player");
        //60, 79, 230
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(74, 48, 219)));
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        loadFragment(new AllSongsFragment());
        if(!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }
    }

//    public void setNavigationVisibility(boolean show){
//        if(show){
//            animateNavView(1.0f,View.VISIBLE);
//        }else{
//            animateNavView(0.0f,View.GONE);
//        }
//    }
//
//    private void animateNavView(float alpha, final int visibility){
//        navView.animate().alpha(alpha).setDuration(300).setListener(new Animator.AnimatorListener() {
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                navView.setVisibility(visibility);
//            }
//
//            @Override
//            public void onAnimationStart(Animator animation) { }
//            @Override
//            public void onAnimationCancel(Animator animation) { }
//            @Override
//            public void onAnimationRepeat(Animator animation) { }
//        });
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 1){
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //do work
                loadFragment(new AllSongsFragment());
            }else{
                finish();
            }
        }
    }


    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()){
            case R.id.navigation_all_songs:
                setTitle("All Songs");
                fragment = new AllSongsFragment();
                break;

            case R.id.navigation_playlist:
                setTitle("Playlist");
                fragment = new PlaylistFragment();
                break;

            case  R.id.navigation_settings:
                setTitle("Settings");
                fragment = new SettingsFragment();
                break;
        }
        return loadFragment(fragment);
    }
}
