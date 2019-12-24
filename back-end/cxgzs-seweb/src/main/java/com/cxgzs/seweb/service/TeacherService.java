package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.LoginInfoMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.model.LoginInfo;
import com.cxgzs.seweb.model.Teacher;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("TeacherService")
public class TeacherService {
    @Resource
    TeacherMapper teacherMapper;
    @Resource
    LoginInfoMapper loginInfoMapper;
    @Resource
    LessonService lessonService;
    @Resource
    FileService fileservice;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
    public Map<String, Object> getTeacherById(String id,String prefix) {
        Map<String, Object> ret = new HashMap<String, Object>();
        Teacher s = teacherMapper.selectByPrimaryKey(id);
        if (s == null) {
            ret.put("state_code", 1);
            return ret;
        } else {
            ret.put("state_code", 0);
            ret.put("user_type", "教师");
            ret.put("pho_url", fileservice.getFileUrl(s.getPhoUrl(),prefix));
            ret.put("no", s.getTno());
            ret.put("name", s.getName());
            ret.put("phone", s.getTelNum());
            ret.put("college", s.getCollege());
            ret.put("office_address", s.getOfficeAddress());
            ret.put("lessonid",lessonService.getLessonIdByTno(id));
            int gender = s.getGender();
            if (gender == 0) ret.put("gender", "男");
            else ret.put("gender", "女");
            ret.put("nickname", s.getNickname());
            Date thisTime=loginInfoMapper.selectThisByUserId(id);
            LoginInfo condition = new LoginInfo();
            condition.setLoginTime(thisTime);
            condition.setUno(id);
            LoginInfo info = loginInfoMapper.selectLastByUserId(condition);
            if(info!=null){
                ret.put("last_time",sdf.format(info.getLoginTime()));
            }
            else ret.put("last_time",sdf.format(thisTime));
            return ret;
        }
    }

    public Teacher getUserById(String userId) {

        return teacherMapper.selectByPrimaryKey(userId);

    }

    public boolean addUser(Teacher record) {
        boolean result = false;
        try {
            teacherMapper.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<String, Object> updateTeacherPasswordById(String id, String pwd) {

        Map<String, Object> ret = new HashMap<String, Object>();
        Teacher student = new Teacher();
        student.setTno(id);
        student.setPassword(pwd);
        if (teacherMapper.updateByPrimaryKeySelective(student) != 0) {
            ret.put("state_code", 0);
        } else
            ret.put("state_code", 1);
        return ret;
    }

    public Map<String, Object> updateTeacherInfoById(String id, String phone, String nickname,String prefix) {

        Map<String, Object> ret = new HashMap<String, Object>();
        Teacher student = new Teacher();
        student.setTno(id);
        student.setTelNum(phone);
        student.setNickname(nickname);
        if (teacherMapper.updateByPrimaryKeySelective(student) != 0) {
            ret = getTeacherById(id,prefix);
        } else
            ret.put("state_code", 1);
        return ret;
    }

    public Map<String, Object> updateTeacherPicById(MultipartFile file, String id, String net_prefix) {
        String address = fileservice.updPhoto(file,id);

        Map<String , Object> ret = new HashMap<String ,Object>();
        ret.put("state_code",0);
        ret.put("pho_url",fileservice.getFileUrl(address,net_prefix));
        return ret;
    }

    public  Map<String,Object> verify(String id,String pwd){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        if(teacher==null){
            ret.put("state_code",1);
            return ret;
        }
        if(!pwd.equals(teacher.getPassword())){
            ret.put("state_code",2);
            return ret;
        }
        else {
            ret.put("state_code",0);
            return ret;
        }

    }

    public  Map<String,Object> login(String id,String pwd,String ip){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        if(teacher==null){
            ret.put("state_code",1);
            return ret;
        }
        if(!pwd.equals(teacher.getPassword())){
            ret.put("state_code",2);
            return ret;
        }
        else {
            ret.put("state_code",0);
            ret.put("user_type","Teacher");
            LoginInfo info=new LoginInfo();
            info.setUno(id);
            info.setLoginTime(new Date());
            info.setLoginIp(ip);
            loginInfoMapper.insertSelective(info);
            return ret;
        }

    }
}
