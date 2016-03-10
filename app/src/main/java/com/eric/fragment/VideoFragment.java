package com.eric.fragment;

import java.util.List;

import com.eric.adapter.VideoListAdapter;
import com.eric.common.MPCommon;
import com.eric.entity.VideoInfo;
import com.eric.entity.Videofile;
import com.eric.mutiplayer.R;
import com.eric.mutiplayer.R.layout;
import com.eric.util.VideoUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;

public class VideoFragment extends Fragment {
	public interface OnVideoListener{
		abstract void onChanged(VideoInfo info);
	}
	private List<Videofile> videofiles = null;
	private ListView mVideoList;
	private int listPosition = 0;
	private int currentTime;
	private int duration;
	VideoListAdapter listAdapter;
	 public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
	        
		 View videoView = inflater.inflate(R.layout.videoplayer, container,false);
		 mVideoList = (ListView) videoView.findViewById(R.id.video_list);
		 videofiles = VideoUtil.getVideos(getActivity());
		 
		 MPCommon.setVideoList(videofiles);
		 mVideoList.setOnItemClickListener(new VideoListItemClickListener());
		 
		 listAdapter = new VideoListAdapter(this.getActivity(), videofiles);
		 mVideoList.setAdapter(listAdapter);
		 return videoView;
	        
	    }  
	 private class VideoListItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			listPosition = position;
			playVideo(listPosition);
		}

		private void playVideo(int listPosition) {
			// TODO Auto-generated method stub
			Videofile videoInfo = videofiles.get(listPosition);
			VideoInfo vInfo = new VideoInfo();
			vInfo.setTitle(videoInfo.getTitle());
			vInfo.setCurrentTime(currentTime);
			vInfo.setArtist(videoInfo.getArtist());
			vInfo.setListPosition(listPosition);
			vInfo.setUrl(videoInfo.getUrl());
			
			((OnVideoListener) getActivity()).onChanged(vInfo);
		}
		 
	 }   
}
