package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.FileInfoMapper;
import com.cxgzs.seweb.dao.FileOperateInfoMapper;
import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.model.FileInfo;
import com.cxgzs.seweb.model.FileOperateInfo;
import com.cxgzs.seweb.model.Student;
import com.cxgzs.seweb.model.Teacher;
import com.cxgzs.seweb.util.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("KKFileServiceForHw")
public class FileServiceForHw{
    @Resource
    FileOperateInfoMapper op;
    @Resource
    FileInfoMapper info;
    @Resource
    StudentMapper s;
    @Resource
    TeacherMapper t;

    String file_path;

    public FileServiceForHw(){
        file_path=ClassUtils.getDefaultClassLoader().getResource("").getPath();
        String res[]=file_path.split("target")[0].split("file:");
        file_path=(res[0].length()!=0?res[0]:res[1])+"files/";
        System.out.println("file_path is "+file_path);
    }

    //保存文件 种类参考数据库注释
    public String saveFile(MultipartFile file, Integer lesson_id, String uno, Integer type, String name){
        //System.out.println("file_path is "+file_path);
        FileInfo new_file = new FileInfo();
        String path=saveToLocal(file, type, name);

        new_file.setBelongLesson(lesson_id);
        new_file.setDownTimes(0);
        new_file.setFileName(file.getOriginalFilename());
        new_file.setFileAddress(path);
        new_file.setFileById(uno);
        new_file.setType(type);

        info.insert(new_file);
        return path;
    }
    private String saveToLocal(MultipartFile file, Integer type, String name){
        String prefix=file_path+"docs//";
        //System.out.println(1);
        String file_name_suffix="."+file.getOriginalFilename().split("\\.")[1];
        File descFile=new File(prefix+name+file_name_suffix);

        int i=1;
        String new_name=prefix+name+file_name_suffix;
        while(descFile.exists()){
            new_name=prefix+name+"("+i+")"+file_name_suffix;
            descFile=new File(new_name);
            i+=1;
        }

        descFile=new File("/"+new_name);
        try{file.transferTo(descFile);}
        catch(Exception e){ System.out.println(e); };
        System.out.println("get path is "+descFile.getPath());

        String ret=descFile.getPath().replace("\\","/");

        return ret.split("files/")[1];
    }
}
