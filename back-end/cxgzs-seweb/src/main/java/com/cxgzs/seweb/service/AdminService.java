package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.AdminMapper;
import com.cxgzs.seweb.dao.LoginInfoMapper;
import com.cxgzs.seweb.model.Admin;
import com.cxgzs.seweb.model.LoginInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("AdminService")
public class AdminService {
    @Resource
    AdminMapper adminMapper;
    @Resource
    LoginInfoMapper loginInfoMapper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
    public Map<String,Object> getTeacherById(String id){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Admin s=adminMapper.selectByPrimaryKey(id);
        if(s==null){
            ret.put("state_code",1);
            return ret;
        }
        else {
            ret.put("state_code",0);
            ret.put("user_type","管理员");
            ret.put("no",s.getAno());
            ret.put("name",s.getName());
            ret.put("nickname",s.getNickname());

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

    public Admin getUserById(String userId) {

        return adminMapper.selectByPrimaryKey(userId);

    }

    public boolean addUser(Admin record){
        boolean result = false;
        try {
            adminMapper.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<String,Object> updateAdminPasswordById(String id,String pwd){


        Map<String , Object> ret = new HashMap<String ,Object>();
        Admin admin=new Admin();
        admin.setAno(id);
        admin.setPassword(pwd);
        if(adminMapper.updateByPrimaryKeySelective(admin)!=0){
            ret.put("state_code",0);
        }
        else
            ret.put("state_code",1);
        return ret;
    }
    public  Map<String,Object> verify(String id,String pwd){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if(admin==null){
            ret.put("state_code",1);
            return ret;
        }
        if(!pwd.equals(admin.getPassword())){
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
        Admin admin = adminMapper.selectByPrimaryKey(id);
        if(admin==null){
            ret.put("state_code",1);
            return ret;
        }
        if(!pwd.equals(admin.getPassword())){
            ret.put("state_code",2);
            return ret;
        }
        else {
            ret.put("state_code",0);
            ret.put("user_type","Admin");
            LoginInfo info=new LoginInfo();
            info.setUno(id);
            info.setLoginTime(new Date());
            info.setLoginIp(ip);
            loginInfoMapper.insertSelective(info);
            return ret;
        }

    }
}
