package com.cxgzs.seweb.model;

public class HomeworkSubmitKey {
    private Integer homeworkId;

    private String sno;

    public HomeworkSubmitKey(Integer homeworkId, String sno) {
        this.homeworkId = homeworkId;
        this.sno = sno;
    }

    public HomeworkSubmitKey() {
        super();
    }

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno == null ? null : sno.trim();
    }
}