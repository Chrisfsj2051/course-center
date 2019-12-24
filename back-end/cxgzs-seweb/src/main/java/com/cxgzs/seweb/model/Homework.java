package com.cxgzs.seweb.model;

import java.util.Date;

public class Homework {
    private Integer homeworkId;

    private Integer lessonId;

    private String title;

    private String content;

    private Date releaseTime;

    private Date deadline;

    private Integer subTimes;

    public Integer getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getSubTimes() {
        return subTimes;
    }

    public void setSubTimes(Integer subTimes) {
        this.subTimes = subTimes;
    }

//    @Override
//    public int compareTo(Homework o) {
//        return o.
//    }
}