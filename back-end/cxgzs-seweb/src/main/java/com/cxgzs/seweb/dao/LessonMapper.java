package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.Lesson;

public interface LessonMapper {

    int deleteByPrimaryKey(Integer lessonId);

    int insert(Lesson record);

    int insertSelective(Lesson record);

    Lesson selectByPrimaryKey(Integer lessonId);

    int updateByPrimaryKeySelective(Lesson record);

    int updateByPrimaryKey(Lesson record);

    int selectLidByTno(String tno);
}