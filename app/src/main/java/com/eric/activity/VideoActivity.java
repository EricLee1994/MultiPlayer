package com.eric.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.eric.common.MPCommon;
import com.eric.entity.Videofile;
import com.eric.service.VideoPlayer;
import com.eric.mutiplayer.R;

public class VideoActivity extends Activity {

	private String title;
	private String url;
	private int listPosition;
	private int currentTime;
	private int duration;
	private int flag;
	private SeekBar video_progressBar;
	private VideoPlayer videoPlayer;
	private SurfaceView surfaceView;
	private Button playButton;
	private Button nextButton;
	private Button previousButton;
	private boolean isPlaying;
	private boolean isFirstTime = true;
	private String TAG = "VideoActivity";
	int screenWidth ,screenHeight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vplay_layout);
		initView();
		findVideoViewById();
		setViewOnClickListener();
		getVideoData();
		playvideo();
	}
	private void initView(){
		screenHeight = getWindowManager().getDefaultDisplay().getHeight();
		screenWidth = getWindowManager().getDefaultDisplay().getWidth();
	}
	private void setViewOnClickListener() {
		// TODO Auto-generated method stub
		ViewOnClickListener viewOnClickListener = new ViewOnClickListener();
		playButton.setOnClickListener(viewOnClickListener);
		nextButton.setOnClickListener(viewOnClickListener);
		previousButton.setOnClickListener(viewOnClickListener);
		//repeatButton.setOnClickListener(viewOnClickListener);
		//shuffleButton.setOnClickListener(viewOnClickListener);

	}

	private class ViewOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnPlayUrl:
				if (isFirstTime) {
					videoPlayer.playUrl(url);
					isFirstTime = false;
					isPlaying = true;
				}
				if (isPlaying) {
					playButton.setBackgroundResource(R.drawable.play_selector);
					videoPlayer.pause();
					isPlaying = false;

				} else {
					playButton.setBackgroundResource(R.drawable.pause_selector);
					Videofile videofile = MPCommon.getVideoList().get(
							listPosition);
					videoPlayer.playUrl(videofile.getUrl());
					isPlaying = true;
				}
				break;

			case R.id.btnNext:
				playButton.setBackgroundResource(R.drawable.pause_selector);
				isFirstTime = false;
				nextVideo();
				break;

			case R.id.btnPrevious:
				playButton.setBackgroundResource(R.drawable.pause_selector);
				isFirstTime = false;
				previousVideo();
				break;
			}
		}

		private void previousVideo() {
			// TODO Auto-generated method stub
			Log.e(TAG,"prepostion="+listPosition);
			listPosition = listPosition - 1;
			if (listPosition >= 0) {
				Videofile videofile = MPCommon.getVideoList().get(listPosition);
				videoPlayer.replay(videofile.getUrl());
				Log.e(TAG, videofile.getUrl());
			} else {
				listPosition = 0;
				Toast.makeText(VideoActivity.this, "no previous", Toast.LENGTH_SHORT)
						.show();
			}
		}

		private void nextVideo() {
			// TODO Auto-generated method stub
			Log.e(TAG, "nextposition ="+listPosition);
			listPosition = listPosition + 1;
			if (listPosition <= MPCommon.getVideoList().size() - 1) {
				Videofile videofile = MPCommon.getVideoList().get(listPosition);
				videoPlayer.replay(videofile.getUrl());
			} else {
				listPosition = MPCommon.getVideoList().size() - 1;
				Toast.makeText(VideoActivity.this, "no next", Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	private void findVideoViewById() {
		// TODO Auto-generated method stub
		surfaceView = (SurfaceView) findViewById(R.id.surfaceView1);
		video_progressBar = (SeekBar) findViewById(R.id.skbProgress);
		playButton = (Button) findViewById(R.id.btnPlayUrl);
		nextButton = (Button) findViewById(R.id.btnNext);
		previousButton = (Button) findViewById(R.id.btnPrevious);

	}

	private void playvideo() {
		// TODO Auto-generated method stub
		video_progressBar.setOnSeekBarChangeListener(new mSeekBarChangeEvent());
		videoPlayer = new VideoPlayer(surfaceView, video_progressBar,screenWidth,screenHeight);
		playButton.setBackgroundResource(R.drawable.pause_selector);
		isPlaying = true;
		videoPlayer.playUrl(url);
		Log.e("url", url);
	}

	class mSeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
		int progress;

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {

			this.progress = progress * videoPlayer.mediaPlayer.getDuration()
					/ seekBar.getMax();
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {

		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {

			videoPlayer.mediaPlayer.seekTo(progress);
		}
	}

	private void getVideoData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		title = bundle.getString("title");
		url = bundle.getString("url");
		listPosition = bundle.getInt("listPostion");
		currentTime = bundle.getInt("currentTime");
		duration = bundle.getInt("duration");
	}
}
