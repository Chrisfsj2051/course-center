package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.LessonMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.model.Lesson;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("LessonService")
public class LessonService {
    @Resource
    LessonMapper lessonMapper;
    @Resource
    TeacherMapper teacherMapper;

    public int getLessonIdByTno(String tno){
        return lessonMapper.selectLidByTno(tno);
    }
}
