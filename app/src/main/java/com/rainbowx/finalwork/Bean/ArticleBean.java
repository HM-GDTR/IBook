package com.rainbowx.finalwork.Bean;

public class ArticleBean {
    public int uid, aid;
    public String timeStr;
    public String content, title;
    public ArticleBean(int uid, int aid, String content, String timeStr, String title) {
        this.uid = uid;
        this.aid = aid;
        this.title = title;
        this.content = content;
        this.timeStr = timeStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
}
