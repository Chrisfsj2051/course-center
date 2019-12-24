package com.cxgzs.seweb.model;

public class FileInfo {
    private Integer fileId;

    private String fileAddress;

    private String fileName;

    private String fileById;

    private Integer downTimes;

    private Integer belongLesson;

    private Integer type;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileById() {
        return fileById;
    }

    public void setFileById(String fileById) {
        this.fileById = fileById;
    }

    public Integer getDownTimes() {
        return downTimes;
    }

    public void setDownTimes(Integer downTimes) {
        this.downTimes = downTimes;
    }

    public Integer getBelongLesson() {
        return belongLesson;
    }

    public void setBelongLesson(Integer belongLesson) {
        this.belongLesson = belongLesson;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}