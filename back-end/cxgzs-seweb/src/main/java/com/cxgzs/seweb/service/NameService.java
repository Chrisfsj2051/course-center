package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.model.Student;
import com.cxgzs.seweb.model.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("NameService")
public class NameService {
    @Resource
    StudentMapper s;
    @Resource
    TeacherMapper t;
    public String getName(String no){
        Student st=s.selectByPrimaryKey(no);
        Teacher te=t.selectByPrimaryKey(no);

        return st==null?te.getName():st.getName();
    }
}
