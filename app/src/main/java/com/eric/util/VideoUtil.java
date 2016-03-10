package com.eric.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.eric.entity.Music;
import com.eric.entity.Videofile;

import android.R.integer;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.provider.MediaStore.Video.Media;

public class VideoUtil {
	public static List<Videofile> getVideos(Context context){
		Cursor cursor=context.getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
		List<Videofile> videofiles = new ArrayList<Videofile>();
		for(int i=0;i<cursor.getCount();i++){
			cursor.moveToNext();
			Videofile videofile = new Videofile();
			long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
			String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
            String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
            String mimeType = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE));
            String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            long duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

            videofile.setId(id);
            videofile.setTitle(title);
            videofile.setArtist(artist);
            videofile.setAlbum(album);
            videofile.setDisplayName(displayName);
            videofile.setDuration(duration);
            videofile.setSize(size);
            videofile.setUrl(url);
            videofiles.add(videofile);
		}
	return videofiles;
	}
	public static List<HashMap<String, String>> getVedioMaps(List<Videofile> videoInfos){
		List<HashMap<String, String>> videoList = new ArrayList<HashMap<String,String>>();		
		for (Iterator iterator = videoInfos.iterator();iterator.hasNext();) {
			Videofile videofile = (Videofile) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", videofile.getTitle());
			map.put("Artist", videofile.getArtist());
			map.put("album", videofile.getAlbum());
			map.put("displayName", videofile.getDisplayName());
			map.put("albumId", String.valueOf(videofile.getAlbumId()));
			map.put("duration", formatTime(videofile.getDuration()));
			map.put("size", String.valueOf(videofile.getSize()));
			map.put("url", videofile.getUrl());
			videoList.add(map);
		}
		
		return videoList;
	}
	public static String formatTime(long time) {
		// TODO Auto-generated method stub
		String min = time / (1000 * 60) + "";
		String sec = time % (1000 * 60) + "";
		if (min.length() < 2) {
			min = "0" + time / (1000 * 60) + "";
		} else {
			min = time / (1000 * 60) + "";
		}
		if (sec.length() == 4) {
			sec = "0" + (time % (1000 * 60)) + "";
		} else if (sec.length() == 3) {
			sec = "00" + (time % (1000 * 60)) + "";
		} else if (sec.length() == 2) {
			sec = "000" + (time % (1000 * 60)) + "";
		} else if (sec.length() == 1) {
			sec = "0000" + (time % (1000 * 60)) + "";
		}
		return min + ":" + sec.trim().substring(0, 2);
	}
}
			
