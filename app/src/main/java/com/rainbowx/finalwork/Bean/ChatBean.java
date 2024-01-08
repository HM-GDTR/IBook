package com.rainbowx.finalwork.Bean;

import com.rainbowx.finalwork.Services.Chat;

public class ChatBean {
    public int order, other, cid;
    public String content;
    public ChatBean(int order, int cid, int other, String content) {
        this.order = order;
        this.other = other;
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOther() {
        return other;
    }

    public void setOther(int other) {
        this.other = other;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
