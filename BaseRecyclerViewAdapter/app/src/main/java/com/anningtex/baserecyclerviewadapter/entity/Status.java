package com.anningtex.baserecyclerviewadapter.entity;

/**
 * @ClassName: Status
 * @Description: 状态实体类
 * @Author: alvis
 * @CreateDate: 2020/6/8 11:21
 */
public class Status {
    private boolean isRetweet;
    private String text;
    private String userName;
    private String userAvatar;
    private String createdAt;

    public boolean isRetweet() {
        return isRetweet;
    }

    public void setRetweet(boolean retweet) {
        isRetweet = retweet;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
