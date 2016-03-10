package com.eric.entity;

public class VideoInfo {
	private String title;
	private String url;
	private String artist;
	private int currentTime;
	private int listPosition;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public int getCurrentTime() {
		return currentTime;
	}
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	public int getListPosition() {
		return listPosition;
	}
	public void setListPosition(int listPosition) {
		this.listPosition = listPosition;
	}
}
