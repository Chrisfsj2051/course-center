package com.cxgzs.seweb.service;


import com.cxgzs.seweb.dao.AdminMapper;
import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.model.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserService {//工具类，通过学工号获取名字和昵称

    @Resource
    StudentMapper studentMapper;
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    AdminMapper adminMapper;

    public String getNameByUno(String uno){
        if(uno.length()==9){
            return studentMapper.selectByPrimaryKey(uno).getName();
        }
        else if(uno.length()==6){
            return teacherMapper.selectByPrimaryKey(uno).getName();
        }
        else{
            return adminMapper.selectByPrimaryKey(uno).getName();
        }
    }

    public String getNickNameByUno(String uno){
        if(uno.length()==9){
            return studentMapper.selectByPrimaryKey(uno).getNickname();
        }
        else if(uno.length()==6){
            return teacherMapper.selectByPrimaryKey(uno).getNickname();
        }
        else{
            return adminMapper.selectByPrimaryKey(uno).getNickname();
        }
    }
}
