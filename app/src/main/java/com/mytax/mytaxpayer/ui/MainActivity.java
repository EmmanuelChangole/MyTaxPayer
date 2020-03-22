package com.mytax.mytaxpayer.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.mytax.mytaxpayer.R;
import com.mytax.mytaxpayer.ui.LoginFragment;
import com.mytax.mytaxpayer.ui.SignUpFragment;
import com.mytax.mytaxpayer.utils.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        initViews();
    }

    private void initViews()
    {
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new LoginFragment(),"Login");
        adapter.AddFragment(new SignUpFragment(),"SignUp");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initWidgets()
    {
        tabLayout=(TabLayout)findViewById(R.id.tab_layout);
        appBarLayout=(AppBarLayout)findViewById(R.id.app_bar_layout);
        viewPager=(ViewPager)findViewById(R.id.viewer_page);
    }
}
