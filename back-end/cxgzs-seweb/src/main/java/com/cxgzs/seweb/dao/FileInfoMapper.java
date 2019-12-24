package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.FileInfo;

import java.util.List;
import java.util.Map;

public interface FileInfoMapper {
    int deleteByPrimaryKey(Integer fileId);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer fileId);

    List<Map<String,Object>> selectByLessonIdAndType(Integer lessonId, Integer type);

    FileInfo selectByUrl(String url);

    String selectAddrByFileName(String filename);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);
}