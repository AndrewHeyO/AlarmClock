package com.andrew.alarmclock.pager.presentation;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.andrew.alarmclock.R;
import com.andrew.alarmclock.data.repository.sharedPreferences.ISharedPreferencesRepository;
import com.andrew.alarmclock.di.InjectHolder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PagerActivity extends AppCompatActivity {

    @Inject
    ISharedPreferencesRepository repository;

    @BindView(R.id.pager_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.pager_view_pager)
    ViewPager viewPager;

    private final static int[] TAB_ICONS = {
            R.drawable.ic_tab_alarm_clock,
            R.drawable.ic_tab_news,
            R.drawable.ic_tab_settings
    };

    private final static int[] TAB_ICONS_FOCUS = {
            R.drawable.ic_tab_alarm_clock_focus,
            R.drawable.ic_tab_news_focus,
            R.drawable.ic_tab_settings_focus
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        InjectHolder.getInstance().buildPagerComponent().inject(this);
        repository.isDarkTheme().subscribe(val -> {
            if(val) {
                setTheme(R.style.AppTheme_Dark);
            } else {
                setTheme(R.style.AppTheme_Light);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ButterKnife.bind(this);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        setupTabLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InjectHolder.getInstance().clearPagerComponent();
    }

    private void setupTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(TAB_ICONS_FOCUS[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(TAB_ICONS[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.setIcon(TAB_ICONS_FOCUS[tab.getPosition()]);
            }
        });

        tabLayout.getTabAt(0).setIcon(TAB_ICONS[0]);
        tabLayout.getTabAt(1).setIcon(TAB_ICONS[1]);
        tabLayout.getTabAt(2).setIcon(TAB_ICONS[2]);

        tabLayout.getTabAt(0).select();
    }
}
