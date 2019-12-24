package com.cxgzs.seweb.controller;

import com.cxgzs.seweb.model.Student;
import com.cxgzs.seweb.service.*;
import com.cxgzs.seweb.util.IpUtil;
import jxl.write.WriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private StudentService s;
    @Autowired
    private TeacherService t;
    @Autowired
    private AdminService a;
    @Autowired
    private HomeworkSubmitService hss;
    @Autowired
    private HomeworkService hws;
    @Autowired
    private FileOperateService fos;
    @Autowired
    private FileService fs;

    @PostMapping("/get_info")
    public Map<String, Object> getInfo(HttpServletRequest request) {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String id = request.getParameter("uno");
        if (id.length() == 9) {
            //student
            return s.getStudentById(id,net_prefix);
        } else if (id.length() == 6) {
            //teacher
            return t.getTeacherById(id,net_prefix);
        } else if (id.length() == 5) {
            //admin
            return a.getTeacherById(id);
        }

        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("state_code", 1);
        return ret;
    }

    @PostMapping("/upd_info")
    public Map<String, Object> updInfo(HttpServletRequest request) {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String no = request.getParameter("uno");
        String phone = request.getParameter("phone");
        String nickname = request.getParameter("nickname");
        Map<String, Object> ret = new HashMap<String, Object>();
        if (no.length() == 9) {
            //student
            return s.updateStudentInfoById(no, phone, nickname,net_prefix);
        } else if (no.length() == 6) {
            //teacher
            return t.updateTeacherInfoById(no, phone, nickname,net_prefix);
        } else {
            //admin&others
            ret.put("state_code", 1);
            return ret;
        }
    }

    @PostMapping("/upd_pwd")
    public Map<String, Object> updPwd(HttpServletRequest request) {
        String newPwd = request.getParameter("pwd");
        String no = request.getParameter("uno");
        String oldPwd = request.getParameter("old_pwd");
        Map<String, Object> ret = new HashMap<String, Object>();
        if (no.length() == 9) {
            //student
            if((Integer)s.verify(no,oldPwd).get("state_code")==0){
                return s.updateStudentPasswordById(no, newPwd);
            }
        } else if (no.length() == 6) {
            //teacher
            if((Integer)t.verify(no,oldPwd).get("state_code")==0){
                return t.updateTeacherPasswordById(no, newPwd);
            }
        } else if (no.length() == 5) {
            //admin
            if((Integer)a.verify(no,oldPwd).get("state_code")==0){
                return a.updateAdminPasswordById(no, newPwd);
            }
        } else {
            ret.put("state_code", 1);
            return ret;
        }
        ret.put("state_code", 2);
        return ret;
    }


    @PostMapping("/upd_pho")
    @ResponseBody
    public Map<String, Object> updPho(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        Map<String, Object> ret = new HashMap<String, Object>();
        if (file.isEmpty()) {
            ret.put("state_code", 3);
            return ret;
        }
        String id = request.getParameter("uno");

        String typeName = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        if (!typeName.equals("jpg") && !typeName.equals("png") && !typeName.equals("gif")) {
            ret.put("state_code", 1);
//            System.out.println("12345");
            return ret;//判断类型
        }

        if (file.getSize() > 1024 * 1024) {
            ret.put("state_code", 2);
            return ret;//判断文件大小
        }

        if(id.length()==9){
            return s.updateStudentPicById(file,id,net_prefix);
        }else if(id.length()==6){
            return t.updateTeacherPicById(file,id,net_prefix);
        }else {
            ret.put("state_code",4);
        }
        return ret;

    }

    @PostMapping("/my_hwk")
    public Map<String, Object> myHwk(HttpServletRequest request) {

        String no = request.getParameter("uno");
        int cid = Integer.parseInt(request.getParameter("cid"));


        return hss.getMyHomeworkListStatus(cid, no);
    }

    @PostMapping("login")
    public Map<String, Object> login(HttpServletRequest request) {
        String no = request.getParameter("uno");
        String pwd = request.getParameter("pwd");
        String ip = IpUtil.getIpAddress(request);
        Map<String, Object> ret = new HashMap<String, Object>();
        if (no.length() == 9) {
            return s.login(no, pwd, ip);
        } else if (no.length() == 6) {
            return t.login(no, pwd, ip);
        } else if (no.length() == 5) {
            return a.login(no, pwd, ip);
        } else {
            ret.put("state_code", 4);
            return ret;
        }
    }

    @PostMapping("down_his")
    public Map<String, Object> downHis(HttpServletRequest request) {
        String no = request.getParameter("uno");
        return fos.myRecord(no);
    }


}
