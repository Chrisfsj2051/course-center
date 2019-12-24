package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.HomeworkSubmit;
import com.cxgzs.seweb.model.HomeworkSubmitKey;

import java.util.List;
import java.util.Map;

public interface HomeworkSubmitMapper {
    int deleteByPrimaryKey(HomeworkSubmitKey key);

    int deleteByHomeworkId(Integer homeworkId);

    int insert(HomeworkSubmit record);

    int insertSelective(HomeworkSubmit record);

    HomeworkSubmit selectByPrimaryKey(HomeworkSubmitKey key);

    List<HomeworkSubmit> selectByHomeworkId(Integer homeworkId);

    List<Map<String,Object>> selectByStudentId(HomeworkSubmitKey key);

    int updateByPrimaryKeySelective(HomeworkSubmit record);

    int updateByPrimaryKey(HomeworkSubmit record);
}