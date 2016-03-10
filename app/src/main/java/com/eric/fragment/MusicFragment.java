package com.eric.fragment;

import java.util.List;

import com.eric.activity.AudioActivity;
import com.eric.activity.MainActivity;
import com.eric.adapter.MusicListAdapter;
import com.eric.common.MPCommon;
import com.eric.entity.Music;
import com.eric.entity.MusicInfo;
import com.eric.mutiplayer.R;
import com.eric.util.MusicUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class MusicFragment extends Fragment {
	public interface OnMusicListener {
		abstract void onChanged(MusicInfo info);
	}

	private ListView mMusiclist;
	MusicListAdapter listAdapter;
	private List<Music> musics = null;
	private int listPosition = 0;

	private ImageView musicAlbum;
	private TextView musicTitle;
	private int currentTime;
	private int duration;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View musicView = inflater.inflate(R.layout.musicplayer, container,
				false);

		mMusiclist = (ListView) musicView.findViewById(R.id.music_list);
		musics = MusicUtil.getMusics(getActivity());
		
		MPCommon.setMusicList(musics);
		mMusiclist.setOnItemClickListener(new MusicListItemClickListener());
		listAdapter = new MusicListAdapter(this.getActivity(), musics);
		mMusiclist.setAdapter(listAdapter);
		return musicView;
	}

	private class MusicListItemClickListener implements OnItemClickListener {
		/**
		 * 点击列表播放音乐
		 */
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			listPosition = position; // 获取列表点击的位置
			playMusic(listPosition); // 播放音乐
		}

		private void playMusic(int listPosition) {
			// TODO Auto-generated method stub
			Music mp3Info = musics.get(listPosition);
			//musicTitle.setText(mp3Info.getTitle());
			Bitmap bitmap = MusicUtil.getArtwork(getActivity(),
					mp3Info.getId(), mp3Info.getAlbumId(), true, true);// 获取专辑位图对象，为小图
			//musicAlbum.setImageBitmap(bitmap); // 这里显示专辑图片
			MusicInfo mInfo = new MusicInfo();
			mInfo.setTitle(mp3Info.getTitle());
			mInfo.setCurrentTime(currentTime);
			mInfo.setArtist(mp3Info.getArtist());
			mInfo.setListPosition(listPosition);
			mInfo.setUrl(mp3Info.getUrl());
			
			Log.i("TAG", "@@"+getActivity().toString());
			
			// Intent intent = new Intent(getActivity(), AudioActivity.class);
			// // 定义Intent对象，跳转到PlayerActivity
			// // 添加一系列要传递的数据
			// intent.putExtra("title", );
			// intent.putExtra("url", );
			// intent.putExtra("artist", );
			// intent.putExtra("listPosition", listPosition);
			// intent.putExtra("currentTime", currentTime);

			// intent.putExtra("repeatState", repeatState);
			// intent.putExtra("shuffleState", isShuffle);
			// intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
			// startActivity(intent);

			((OnMusicListener) getActivity()).onChanged(mInfo);
		}
	}
}
