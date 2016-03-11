package com.eric.service;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class VideoPlayer implements MediaPlayer.OnBufferingUpdateListener,
        SurfaceHolder.Callback, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {
    public MediaPlayer mediaPlayer;
    private ProgressBar skbProgress;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private SurfaceHolder surfaceHolder;
    private int postion = 0;
    public long pos = 0;
    public int seek = 0;
    private int videoWidth;
    private int videoHeight;
    private float screenWidth;
    private float screenHeight;
    private String TAG = "VideoPlayer";
    private String mVideoUrl;
    private SurfaceView surfaceView;

    public VideoPlayer(SurfaceView surfaceView, ProgressBar skbProgress) {
        this.skbProgress = skbProgress;
        this.surfaceView = surfaceView;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setTimeTask();
    }

    public VideoPlayer(SurfaceView surfaceView, ProgressBar skbProgress, int screenWidth, int screenHeight) {
        this.skbProgress = skbProgress;
        this.surfaceView = surfaceView;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        setTimeTask();
    }

    //    TimerTask mTimerTask = new TimerTask() {
//
//        @Override
//        public void run() {
//            // TODO Auto-generated method stub
//            if (mediaPlayer == null) {
//                Log.e(TAG, "mediaPlayer is null");
//                return;
//            }
//            Log.i(TAG, "state: " + mediaPlayer.isPlaying() + ", skbstate: " + skbProgress.isPressed());
//            if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
//                handleProgress.sendEmptyMessage(0);
//            }
//        }
//    };
    public void setTimeTask() {
        mTimer = new Timer();

        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                if (mediaPlayer == null) {
                    Log.e(TAG, "mediaPlayer is null");
                    return;
                }
                Log.i(TAG, "state: " + mediaPlayer.isPlaying() + ", skbstate: " + skbProgress.isPressed());
                if (mediaPlayer.isPlaying() && skbProgress.isPressed() == false) {
                    handleProgress.sendEmptyMessage(0);
                }
            }
        };
        mTimer.schedule(mTimerTask, 0, 500);
    }

    Handler handleProgress = new Handler() {
        public void handleMessage(Message msg) {
            int position = mediaPlayer.getCurrentPosition() / 1000;
            int duration = mediaPlayer.getDuration() / 1000;
            Log.e(TAG, "p=" + position + "d=" + duration);
            if (duration > 0) {
                pos = skbProgress.getMax() * position / duration;
                skbProgress.setProgress((int) pos);
            }
        }
    };

    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
            Log.e(TAG, "Play");
        }
    }

    public void replay(String videoUrl) {
        Log.i("TAG", "mTimer");
        if (mTimer == null) {
            setTimeTask();
        }
        if (mediaPlayer != null) {
            try {
                Log.e(TAG, "videoUrl111" + videoUrl);
                mediaPlayer.reset();
                mediaPlayer.setDataSource(videoUrl);
                mediaPlayer.prepareAsync();
                Log.e(TAG, "videoUrl222" + videoUrl);
            } catch (Exception e) {

            }
        }
    }

    public void playUrl(String videoUrl) {
        Log.e(TAG, "videoUrl11" + videoUrl);

        mVideoUrl = videoUrl;

    }

    public void playUrl(String videoUrl, int seek) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(videoUrl);
                mediaPlayer.prepareAsync();
                this.seek = seek * 1000;
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void seek(int seekTo) {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();
            mediaPlayer.seekTo(seekTo * 1000);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        Log.e("TAG", "surfaceCreated");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDisplay(surfaceHolder);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setLooping(false);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);

        try {
            Log.e(TAG, "videoUrl222" + mVideoUrl);
            mediaPlayer.setDataSource(mVideoUrl);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
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

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        // TODO Auto-generated method stub
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.i("TAG", "surfaceDestroyed");
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimerTask.cancel();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        // TODO Auto-generated method stub
        videoWidth = mediaPlayer.getVideoWidth();
        videoHeight =mediaPlayer.getVideoHeight();
        Log.i(TAG, "width =" + videoWidth);
        Log.i(TAG, "height =" + videoHeight);
        if (videoWidth > screenWidth || videoHeight > screenHeight) {
            float heightRatio = (float)videoHeight / screenHeight;
            float widthRatio = (float)videoWidth / screenWidth;
            if (heightRatio > 1 || widthRatio > 1) {
                if (heightRatio > widthRatio) {
                    videoHeight = (int) Math.ceil((float) videoHeight / heightRatio);
                    videoWidth = (int) Math.ceil((float) videoWidth / heightRatio);
                } else {
                    videoHeight = (int) Math.ceil((float) videoHeight / widthRatio);
                    videoWidth = (int) Math.ceil((float) videoWidth / widthRatio);
                }
            }
        }
        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = videoWidth;
        lp.height = videoHeight;
        surfaceView.setLayoutParams(lp);


        if (videoHeight != 0 && videoWidth != 0) {
            arg0.start();
            Log.i("TAG", "prepared");
            if (seek > 0) {
                arg0.seekTo(seek);
            }
        }
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        // TODO Auto-generated method stub
        Log.e(TAG, "complete");
        mTimerTask.cancel();
        mTimer.cancel();
        mTimer = null;
        mTimerTask = null;
        Log.e(TAG, "mTimerTask cancel ");
//		if (mediaPlayer != null) {
//			mediaPlayer.reset();
//			mediaPlayer.release();
//			mediaPlayer = null;
        // listener.onCompleteListener();
//		}
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
        // TODO Auto-generated method stub
        skbProgress.setSecondaryProgress(bufferingProgress);
    }
}
