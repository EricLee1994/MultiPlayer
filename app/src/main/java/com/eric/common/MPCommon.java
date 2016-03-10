package com.eric.common;

import java.util.List;

import com.eric.entity.Music;
import com.eric.entity.Videofile;

import android.app.Application;

public class MPCommon {
	private static List<Music> musicList;
	private static List<Videofile> videoList;
	public static List<Videofile> getVideoList() {
		return videoList;
	}

	public static void setVideoList(List<Videofile> videoList) {
		MPCommon.videoList = videoList;
	}

	public static List<Music> getMusicList() {
		return musicList;
	}

	public static void setMusicList(List<Music> musicList) {
		MPCommon.musicList = musicList;
	}
}
