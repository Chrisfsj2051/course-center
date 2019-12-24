package com.cxgzs.seweb.model;

import java.util.Date;

public class ReplyTie {
    private Integer replyId;

    private Integer tieId;

    private String uno;

    private String content;

    private Date replyTime;

    private Integer replyTo;

    public Integer getReplyId() {
        return replyId;
    }

    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
    }

    public Integer getTieId() {
        return tieId;
    }

    public void setTieId(Integer tieId) {
        this.tieId = tieId;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Integer getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Integer replyTo) {
        this.replyTo = replyTo;
    }
}