package com.rainbowx.finalwork.Bean;

public class RemarkBean {
    int aid, author;
    String timeStr, content;

    public RemarkBean(int aid, int author, String timeStr, String content) {
        this.aid = aid;
        this.author = author;
        this.timeStr = timeStr;
        this.content = content;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
