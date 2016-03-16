package com.eric.service;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

public class MusicPlayer implements OnBufferingUpdateListener,
        OnCompletionListener, MediaPlayer.OnPreparedListener {
    public MediaPlayer mediaPlayer;
    private SeekBar skbProgress;
    private Timer mTimer = new Timer();
    private int position = 0;
    String TAG = "MusicPlayer";

    public MusicPlayer(SeekBar skbProgress) {

        this.skbProgress = skbProgress;

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
        mTimer.schedule(mTimerTask, 0, 1000);
    }

    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (mediaPlayer == null)
                return;
            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                handleProgress.sendEmptyMessage(0);
            }
        }
    };
    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {

            int position = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            if (duration > 0) {
                long pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int) pos);
            }
        }

        ;
    };

    public void playUrl(String audioUrl) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepare();
            // mediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void pause() {
        position = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();

        Log.i(TAG, "postion=" + position);
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(0);
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.e(TAG, "stop");
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        // TODO Auto-generated method stub
        mediaPlayer.release();
        Log.e(TAG, "onCompletion");
        Log.e(TAG, "release");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        // TODO Auto-generated method stub
        skbProgress.setSecondaryProgress(bufferingProgress);
        int currentProgress = skbProgress.getMax()
                * mediaPlayer.getCurrentPosition() / mediaPlayer.getDuration();
        Log.i(TAG, currentProgress + "% play"+bufferingProgress + "% buffer");
    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        // TODO Auto-generated method stub
        if (position > 0) {
            mediaPlayer.seekTo(position);
        }
        mediaPlayer.start();
        Log.i(TAG, "onPrepared");
    }

}
