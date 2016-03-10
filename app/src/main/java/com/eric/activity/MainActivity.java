package com.eric.activity;

import java.util.ArrayList;

import com.eric.adapter.MyFragmentAdapter;
import com.eric.entity.MusicInfo;
import com.eric.entity.VideoInfo;
import com.eric.fragment.MusicFragment;
import com.eric.fragment.SettingFragment;
import com.eric.fragment.VideoFragment;
import com.eric.mutiplayer.R;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
        MusicFragment.OnMusicListener, VideoFragment.OnVideoListener {
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private TextView mTxtMusic, mTxtVideo, mTxtSetting;
    private int currIndex;// 当前页
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();


        InitTextView();
        InitViewPager();
        // InitImage();
    }

    /*
     * 初始化标签
     */
    public void InitTextView() {
        mTxtMusic = (TextView) findViewById(R.id.tv_tab_music);
        mTxtVideo = (TextView) findViewById(R.id.tv_tab_video);
        mTxtSetting = (TextView) findViewById(R.id.tv_tab_setting);

        mTxtMusic.setOnClickListener(new txListener(0));
        mTxtVideo.setOnClickListener(new txListener(1));
        mTxtSetting.setOnClickListener(new txListener(2));
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPager.setCurrentItem(index);
        }
    }

    /*
     * 初始化ViewPager
     */
    public void InitViewPager() {
        mPager = (ViewPager) findViewById(R.id.vPager);
        fragmentList = new ArrayList<Fragment>();
        Fragment musicFragment = new MusicFragment();
        Fragment videoFragment = new VideoFragment();
        Fragment settingFragment = new SettingFragment();

        fragmentList.add(musicFragment);
        fragmentList.add(videoFragment);
        fragmentList.add(settingFragment);

        mPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(),
                fragmentList));
        mPager.setCurrentItem(0);
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());

    }


    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            //arg0=1表示在滑动、arg0=2表示滑动完成、arg0=0表示什么都没做
        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            // 滑动后改变标签颜色
            switch (arg0) {
                case 0:
                    if (currIndex == 1) {
                        mTxtVideo.setTextColor(resources
                                .getColor(R.color.lightwhite));
                    } else if (currIndex == 2) {
                        mTxtSetting.setTextColor(resources
                                .getColor(R.color.lightwhite));
                    }
                    mTxtMusic.setTextColor(resources.getColor(R.color.white));
                    break;

                case 1:
                    if (currIndex == 0) {
                        mTxtMusic.setTextColor(resources
                                .getColor(R.color.lightwhite));
                    } else if (currIndex == 2) {
                        mTxtSetting.setTextColor(resources
                                .getColor(R.color.lightwhite));
                    }
                    mTxtVideo.setTextColor(resources.getColor(R.color.white));
                    break;

                case 2:
                    if (currIndex == 0) {
                        mTxtMusic.setTextColor(resources
                                .getColor(R.color.lightwhite));
                    } else if (currIndex == 1) {
                        mTxtVideo.setTextColor(resources
                                .getColor(R.color.lightwhite));
                    }
                    mTxtSetting.setTextColor(resources.getColor(R.color.white));
                    break;
            }
            currIndex = arg0;
        }
    }

    @Override
    public void onChanged(MusicInfo info) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, AudioActivity.class);
        intent.putExtra("title", info.getTitle());
        intent.putExtra("url", info.getUrl());
        intent.putExtra("artist", info.getArtist());
        intent.putExtra("listPosition", info.getListPosition());
        intent.putExtra("currentTime", info.getCurrentTime());

        startActivity(intent);
    }

    @Override
    public void onChanged(VideoInfo info) {
        // TODO Auto-generated method stub
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("title", info.getTitle());
        intent.putExtra("url", info.getUrl());
        intent.putExtra("artist", info.getArtist());
        intent.putExtra("listPosition", info.getListPosition());
        intent.putExtra("currentTime", info.getCurrentTime());

        startActivity(intent);
    }

}