package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.NoticeInfo;

import java.util.List;
import java.util.Map;

public interface NoticeInfoMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(NoticeInfo record);

    int insertSelective(NoticeInfo record);

    NoticeInfo selectByPrimaryKey(Integer noticeId);

    List<Map<String, Object>> selectByLessonId(Integer lessonId);

    int updateByPrimaryKeySelective(NoticeInfo record);

    int updateByPrimaryKey(NoticeInfo record);

    int selectCount();
}