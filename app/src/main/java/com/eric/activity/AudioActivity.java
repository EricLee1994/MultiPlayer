package com.eric.activity;

import com.eric.common.MPCommon;
import com.eric.entity.Music;
import com.eric.service.MusicPlayer;
import com.eric.mutiplayer.R;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class AudioActivity extends Activity implements MediaPlayer.OnCompletionListener {

    private ImageView infoOperatingIV;
    private TextView tvMusicTitle = null;
    private TextView tvMusicArtist = null;
    private Button previousBtn;
    private Button repeatBtn;
    private Button playBtn;
    private Button shuffleBtn;
    private Button nextBtn;
    private Button backBtn;
    private SeekBar music_progressBar;
    private boolean isPlaying;
    private boolean isFirstTime = true;

    private String title;
    private String artist;
    private String url;
    private int listPosition;
    private int currentTime;
    private int repeatState = 1;
    private int status = 3;
    private final int isCurrentRepeat = 1;//单曲循环
    private final int isAllRepeat = 2;//全部循环
    private boolean isNoneShuffle = true; // 顺序播放
    private boolean isShuffle = false;//随机播放

    private MusicPlayer musicPlayer;
    private String TAG = "AudioActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_activity_layout);

        getMusicData();
        findAudioViewById();
        setViewOnclickListener();
        setAnimation();
        initView();
        playMusic();

    }

    private void playMusic() {
        // TODO Auto-generated method stub
        music_progressBar.setOnSeekBarChangeListener(new mSeekBarChangeEvent());
        musicPlayer = new MusicPlayer(music_progressBar);
        playBtn.setBackgroundResource(R.drawable.pause_selector);
        isPlaying = true;
        musicPlayer.playUrl(url);
    }

    private void initView() {
        // TODO Auto-generated method stub
        tvMusicTitle.setText(title);
        tvMusicArtist.setText(artist);

    }

    private void getMusicData() {
        // TODO Auto-generated method stub
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        title = bundle.getString("title");
        artist = bundle.getString("artist");
        url = bundle.getString("url");
        listPosition = bundle.getInt("listPosition");
        currentTime = bundle.getInt("currentTime");
    }

    private void findAudioViewById() {
        // TODO Auto-generated method stub
        tvMusicTitle = (TextView) findViewById(R.id.musicTitle);
        tvMusicArtist = (TextView) findViewById(R.id.musicArtist);
        previousBtn = (Button) findViewById(R.id.previous_music);
        repeatBtn = (Button) findViewById(R.id.repeat_music);
        playBtn = (Button) findViewById(R.id.play_music);
        shuffleBtn = (Button) findViewById(R.id.shuffle_music);
        nextBtn = (Button) findViewById(R.id.next_music);
        backBtn = (Button) findViewById(R.id.btnBack);
        music_progressBar = (SeekBar) findViewById(R.id.audioTrack);
        infoOperatingIV = (ImageView) findViewById(R.id.infoOperating);
    }

    private void setViewOnclickListener() {
        ViewOnClickListener viewOnClickListener = new ViewOnClickListener();
        playBtn.setOnClickListener(viewOnClickListener);
        nextBtn.setOnClickListener(viewOnClickListener);
        shuffleBtn.setOnClickListener(viewOnClickListener);
        backBtn.setOnClickListener(viewOnClickListener);
        repeatBtn.setOnClickListener(viewOnClickListener);
        previousBtn.setOnClickListener(viewOnClickListener);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if (status == 1) {
            mediaPlayer.start();
        } else if (status == 2) {
            listPosition++;
            if (listPosition > MPCommon.getMusicList().size() - 1) {
                listPosition = 0;
            }
            Music mp3info = MPCommon.getMusicList().get(listPosition);
            musicPlayer.playUrl(mp3info.getUrl());
        } else if (status == 4) {
            listPosition = getRandomIndex(MPCommon.getMusicList().size() - 1);
            Music mp3info = MPCommon.getMusicList().get(listPosition);
            musicPlayer.playUrl(mp3info.getUrl());
        }
    }

    private int getRandomIndex(int end) {
        int index = (int) (Math.random() * end);
        return index;
    }

    private class ViewOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.play_music:
                    if (isFirstTime) {
                        musicPlayer.playUrl(url);
                        isFirstTime = false;
                        isPlaying = true;
                    }
                    if (isPlaying) {
                        playBtn.setBackgroundResource(R.drawable.play_selector);
                        musicPlayer.pause();
                        isPlaying = false;
                        infoOperatingIV.clearAnimation();
                        Log.i(TAG, "pause");
                    } else {
                        playBtn.setBackgroundResource(R.drawable.pause_selector);
                        // musicPlayer.play();
                        Music mp3info = MPCommon.getMusicList().get(listPosition);
                        musicPlayer.playUrl(mp3info.getUrl());
                        isPlaying = true;
                        setAnimation();
                        Log.i(TAG, "play");
                    }
                    break;
                case R.id.next_music:
                    playBtn.setBackgroundResource(R.drawable.pause_selector);
                    isFirstTime = false;
                    nextMusic();
                    isPlaying = true;
                    Log.i(TAG, "next");
                    break;
                case R.id.previous_music:
                    playBtn.setBackgroundResource(R.drawable.pause_selector);
                    isFirstTime = false;
                    previousMusic();
                    isPlaying = true;
                    Log.i(TAG, "previous");
                    break;
                case R.id.repeat_music:
                    if (repeatState == isAllRepeat) {
                        status = 1;
                        repeatState = isCurrentRepeat;
                        repeatBtn.setBackgroundResource(R.drawable.repeat_current_selector);
                        Log.i(TAG, "isCurrentRepeat");
                    } else if (repeatState == isCurrentRepeat) {
                        status = 2;
                        repeatState = isAllRepeat;
                        repeatBtn.setBackgroundResource(R.drawable.repeat_all_selector);
                        Log.i(TAG, "isAllRepeat");
                    }
                    break;
                case R.id.shuffle_music:
                    if (isNoneShuffle) {
                        shuffleBtn.setBackgroundResource(R.drawable.shuffle_selector);
                        Toast.makeText(AudioActivity.this, R.string.shuffle, Toast.LENGTH_SHORT).show();
                        isShuffle = true;
                        isNoneShuffle = false;
                        status = 3;
                        repeatBtn.setClickable(false);
                        Log.i(TAG, "随机播放");
                    } else if (isShuffle) {
                        shuffleBtn.setBackgroundResource(R.drawable.shuffle_none_selector);
                        Toast.makeText(AudioActivity.this, R.string.shuffle_none, Toast.LENGTH_SHORT).show();
                        isShuffle = false;
                        isNoneShuffle = true;
                        status = 4;
                        repeatBtn.setClickable(true);
                        Log.i(TAG, "正常播放");
                    }
                    break;
                case R.id.btnBack:
                    musicPlayer.stop();
                    Log.i(TAG, "stop");
                    finish();
                    break;
            }
        }
    }


    private void previousMusic() {
        // TODO Auto-generated method stub
        listPosition = listPosition - 1;
        if (listPosition >= 0) {
            Music mp3info = MPCommon.getMusicList().get(listPosition);
            musicPlayer.playUrl(mp3info.getUrl());
            tvMusicTitle.setText(mp3info.getTitle());
            tvMusicArtist.setText(mp3info.getArtist());
        } else {
            listPosition = 0;
            Toast.makeText(AudioActivity.this, "没有前一首", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void nextMusic() {
        // TODO Auto-generated method stub
        listPosition = listPosition + 1;
        if (listPosition <= MPCommon.getMusicList().size() - 1) {
            Music mp3info = MPCommon.getMusicList().get(listPosition);
            musicPlayer.playUrl(mp3info.getUrl());
            tvMusicTitle.setText(mp3info.getTitle());
            tvMusicArtist.setText(mp3info.getArtist());
        } else {
            listPosition = MPCommon.getMusicList().size() - 1;
            Toast.makeText(AudioActivity.this, "没有下一首", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    private void repeat_one() {

    }

    private void setAnimation() {
        // TODO Auto-generated method stub
        Animation operatingAnim = AnimationUtils.loadAnimation(this,
                R.anim.tip);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        if (operatingAnim != null) {
            infoOperatingIV.startAnimation(operatingAnim);
        }
    }

    class mSeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

            this.progress = progress * musicPlayer.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

            musicPlayer.mediaPlayer.seekTo(progress);
        }
    }
}
