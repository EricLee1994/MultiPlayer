package com.eric.entity;

public class Music {
	private long id;
	private String title;
	private String album;
	private long albumId;
	private String displayName;
	private String artist;
	private long duration;
	private long size;
	private String url;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public long getAlbumId() {
		return albumId;
	}
	public void setAlbumId(long albumId) {
		this.albumId = albumId;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public long getDuration() {
		return duration;
	}
	public void setDuration(long duration) {
		this.duration = duration;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Music(long id, String title, String album, long albumId,
			String displayName, String artist, long duration, long size,
			String url) {
		super();
		this.id = id;
		this.title = title;
		this.album = album;
		this.albumId = albumId;
		this.displayName = displayName;
		this.artist = artist;
		this.duration = duration;
		this.size = size;
		this.url = url;
		
	}
	public Music(){
		super();
	}
	public String toString() {
		return "Mp3Info [id=" + id + ", title=" + title + ", album=" + album
				+ ", albumId=" + albumId + ", displayName=" + displayName
				+ ", artist=" + artist + ", duration=" + duration + ", size="
				+ size + ", url=" + url + "]";
	}

}
