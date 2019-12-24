package com.cxgzs.seweb.model;

public class Lesson {
    private Integer lessonId;

    private String lessonName;

    private String tno;

    private Integer releaseYear;

    private Integer releaseSemester;

    private String lessonIntro;

    private String syllabus;

    private String phoUrl;

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getTno() {
        return tno;
    }

    public void setTno(String tno) {
        this.tno = tno;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getReleaseSemester() {
        return releaseSemester;
    }

    public void setReleaseSemester(Integer releaseSemester) {
        this.releaseSemester = releaseSemester;
    }

    public String getLessonIntro() {
        return lessonIntro;
    }

    public void setLessonIntro(String lessonIntro) {
        this.lessonIntro = lessonIntro;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getPhoUrl() {
        return phoUrl;
    }

    public void setPhoUrl(String phoUrl) {
        this.phoUrl = phoUrl;
    }
}