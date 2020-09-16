package com.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.leaderboard.Adapters.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        // set tabs
        getTabs();

        //UI
        submitBtn = findViewById(R.id.submit_activity_btn);



        //listeners
        submitBtn.setOnClickListener(this);
    }

    public void getTabs(){
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                viewPagerAdapter.addFragment(LearnerFragment.getInstance(),"Learning Leaders");
                viewPagerAdapter.addFragment(SkillFrament.getInstance(),"Skill IQ Leaders");

                viewPager.setAdapter(viewPagerAdapter);

                tabLayout.setupWithViewPager(viewPager);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == submitBtn){
            startActivity(new Intent(this,SubmitActivity.class));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}