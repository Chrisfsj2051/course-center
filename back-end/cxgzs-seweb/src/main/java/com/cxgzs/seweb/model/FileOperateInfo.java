package com.cxgzs.seweb.model;

import java.util.Date;

public class FileOperateInfo {
    private Integer fileOperateId;

    private Integer fileId;

    private Date operateTime;

    private Integer operateType;

    private String uno;

    public Integer getFileOperateId() {
        return fileOperateId;
    }

    public void setFileOperateId(Integer fileOperateId) {
        this.fileOperateId = fileOperateId;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public String getUno() {
        return uno;
    }

    public void setUno(String uno) {
        this.uno = uno;
    }
}