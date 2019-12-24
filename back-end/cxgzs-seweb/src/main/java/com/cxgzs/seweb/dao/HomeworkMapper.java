package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.Homework;

import java.util.Map;
import java.util.List;

public interface HomeworkMapper {
    int deleteByPrimaryKey(Integer homeworkId);

    int insert(Homework record);

    int insertSelective(Homework record);

    Homework selectByPrimaryKey(Integer homeworkId);

    List<Map<String,Object>> selectByLessonId(Integer lessonId);

    int updateByPrimaryKeySelective(Homework record);

    int updateByPrimaryKey(Homework record);
}