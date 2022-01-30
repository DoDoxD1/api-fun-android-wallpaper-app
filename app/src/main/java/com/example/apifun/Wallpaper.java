package com.example.apifun;

public class Wallpaper {
    private int id, likes, views, downloads, commentsNumber;
    private String user, userImg_url;
    private String fullWallpaper_url, smallWallpaper_url, preview_url, wallpaperPage_url;

    public Wallpaper(int id, int likes, int views, int downloads, int commentsNumber, String user, String userImg_url, String fullWallpaper_url, String smallWallpaper_url, String preview_url, String wallpaperPage_url) {
        this.id = id;
        this.likes = likes;
        this.views = views;
        this.downloads = downloads;
        this.commentsNumber = commentsNumber;
        this.user = user;
        this.userImg_url = userImg_url;
        this.fullWallpaper_url = fullWallpaper_url;
        this.smallWallpaper_url = smallWallpaper_url;
        this.preview_url = preview_url;
        this.wallpaperPage_url = wallpaperPage_url;
    }

    public Wallpaper(int id) {
        this.id = id;
    }

    public Wallpaper(String fullWallpaper_url) {
        this.fullWallpaper_url = fullWallpaper_url;
    }

    public Wallpaper(String fullWallpaper_url,String smallWallpaper_url,String userImg_url,String user, int likes) {
        this.fullWallpaper_url = fullWallpaper_url;
        this.smallWallpaper_url = smallWallpaper_url;
        this.likes = likes;
        this.userImg_url = userImg_url;
        this.user = user;
    }

    public Wallpaper(int id, int likes, int views, int downloads, int commentsNumber, String user) {
        this.id = id;
        this.likes = likes;
        this.views = views;
        this.downloads = downloads;
        this.commentsNumber = commentsNumber;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getCommentsNumber() {
        return commentsNumber;
    }

    public void setCommentsNumber(int commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImg_url() {
        return userImg_url;
    }

    public void setUserImg_url(String userImg_url) {
        this.userImg_url = userImg_url;
    }

    public String getFullWallpaper_url() {
        return fullWallpaper_url;
    }

    public void setFullWallpaper_url(String fullWallpaper_url) {
        this.fullWallpaper_url = fullWallpaper_url;
    }

    public String getSmallWallpaper_url() {
        return smallWallpaper_url;
    }

    public void setSmallWallpaper_url(String smallWallpaper_url) {
        this.smallWallpaper_url = smallWallpaper_url;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public String getWallpaperPage_url() {
        return wallpaperPage_url;
    }

    public void setWallpaperPage_url(String wallpaperPage_url) {
        this.wallpaperPage_url = wallpaperPage_url;
    }
}
