package com.cxgzs.seweb.model;

import java.util.Date;

public class HomeworkSubmit extends HomeworkSubmitKey {
    private String fileAddress;

    private Date uploadTime;

    private Integer score;

    private String suggest;

    public HomeworkSubmit(Integer homeworkId, String sno, String fileAddress, Date uploadTime, Integer score, String suggest) {
        super(homeworkId, sno);
        this.fileAddress = fileAddress;
        this.uploadTime = uploadTime;
        this.score = score;
        this.suggest = suggest;
    }

    public HomeworkSubmit() {
        super();
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress == null ? null : fileAddress.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest == null ? null : suggest.trim();
    }
}