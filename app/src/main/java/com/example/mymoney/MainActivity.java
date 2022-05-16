package com.example.mymoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    private double plus=0, minus=0, ballans=0;

    private TextView textBallans;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        textBallans = findViewById(R.id.textBallans);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        minus = BD.totalExpence(this);
        plus = BD.totalIncome(this);
        ballans = plus -minus;



        BD.allExpence(this);
        Log.d("money777", "общие траты");
        Log.d("money777", minus + "");
        Log.d("money777", "общие доходы");
        Log.d("money777", plus + "");
        Log.d("money777", "балланс");
        Log.d("money777", ballans + "");
        textBallans.setText("" + ballans);

    }

    public void vvodExpence(View view) {
        startActivity(new Intent(MainActivity.this, VvodExpenceActivity.class));
    }
}