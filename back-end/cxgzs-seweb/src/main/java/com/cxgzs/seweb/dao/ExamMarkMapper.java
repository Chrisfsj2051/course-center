package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.ExamMark;
import com.cxgzs.seweb.model.ExamMarkKey;

public interface ExamMarkMapper {
    int deleteByPrimaryKey(ExamMarkKey key);

    int insert(ExamMark record);

    int insertSelective(ExamMark record);

    ExamMark selectByPrimaryKey(ExamMarkKey key);

    int updateByPrimaryKeySelective(ExamMark record);

    int updateByPrimaryKey(ExamMark record);
}