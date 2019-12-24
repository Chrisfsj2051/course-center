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
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


@Service("FileService")
public class FileService {
    @Resource
    FileOperateInfoMapper op;
    @Resource
    FileInfoMapper info;
    @Resource
    StudentMapper s;
    @Resource
    TeacherMapper t;

    String file_path;

    public FileService(){
        file_path=ClassUtils.getDefaultClassLoader().getResource("").getPath();

        //file_path="file:/home/project/cxgzs-seweb/target/seweb-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/";
        //System.out.println("in init: "+file_path);
        //file:/home/project/cxgzs-seweb/target/seweb-0.0.1-SNAPSHOT.jar!/BOOT-INF/classes!/

        String res[]=file_path.split("target")[0].split("file:");
        file_path=(res[0].length()!=0?res[0]:res[1])+"files/";
    }
    public String getFile_path(){return file_path;}
    //保存文件 种类参考数据库注释
    public String saveFile(MultipartFile file, Integer lesson_id, String uno, Integer type){
        //System.out.println("file_path is "+file_path);
        FileInfo new_file = new FileInfo();
        String path=saveToLocal(file, type);

        new_file.setBelongLesson(lesson_id);
        new_file.setDownTimes(0);
        new_file.setFileName(file.getOriginalFilename());
        new_file.setFileAddress(path);
        new_file.setFileById(uno);
        new_file.setType(type);

        info.insert(new_file);
        return path;
    }
    public String updPhoto(MultipartFile file, String uno){
        //System.out.println("file_path is "+file_path);
        String MD5edId=MD5Util.MD5Encode(uno,"utf8");
        String path=savePicToLocal(file, MD5edId);

        if(uno.length()==9){
            Student student = new Student();
            student.setSno(uno);
            student.setPhoUrl(path);
            s.updateByPrimaryKeySelective(student);
        }else if(uno.length()==6){
            Teacher teacher = new Teacher();
            teacher.setTno(uno);
            teacher.setPhoUrl(path);
            t.updateByPrimaryKeySelective(teacher);
        }

        return path;
    }

    public String getPreviewUrl(String url, String net_prefix){
        return "http://dcsapi.com?k=493029203&url="+getFileUrl(url,net_prefix);
    }

    //返回完整的可供下载的url
    public String getFileUrl(String url, String net_prefix){
        return net_prefix+"/"+url;
    }

    public String zipFiles(List<File> lis){
        return zipFiles(lis, "tempFile");
    }

    public String zipFiles(List<File> lis, String name){
        String zipFilename = file_path+"temp/"+name+".zip";
        //System.out.println("zipFilename="+zipFilename);
        File file = new File(zipFilename);

        try {
            file.createNewFile();
            FileOutputStream fous = new FileOutputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(fous);

            for(File fi : lis){
                System.out.println(fi.getName());
                if(fi.exists()) {
                    System.out.println("IN");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));
                    ZipEntry entry = new ZipEntry(fi.getName());
                    zipOut.putNextEntry(entry);
                    int count;
                    byte[] buf = new byte[1024];
                    while ((count = bis.read(buf)) != -1) {
                        zipOut.write(buf, 0, count);
                    }
                    bis.close();
                }
            }
            zipOut.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        System.out.println(zipFilename);
        return zipFilename.split("files/")[1];
    }

    private String saveToLocal(MultipartFile file, Integer type){
        String prefix=file_path;
        if (type==0) prefix+="images//";
        if (type==1) prefix+="ppt//";
        if (type==2) prefix+="pdf//";
        if (type==3) prefix+="docs//";
        if (type==4) prefix+="temp//";
        //System.out.println(1);
        File descFile=new File(prefix+file.getOriginalFilename());
        String file_name_prefix=file.getOriginalFilename().split("\\.")[0];
        String file_name_suffix="."+file.getOriginalFilename().split("\\.")[1];
        //System.out.println(2);
        int i=1;
        String new_name=prefix+file.getOriginalFilename();
        while(descFile.exists()){
            new_name=prefix+file_name_prefix+"("+i+")"+file_name_suffix;
            descFile=new File(new_name);
            i+=1;
        }

        descFile=new File("/"+new_name);
        try{file.transferTo(descFile);}
        catch(Exception e){ System.out.println(e); };
        System.out.println("get path is "+descFile.getPath());
        //return descFile.getPath().split("files")[1];
        String ret=descFile.getPath().replace("\\","/");
        return ret.split("files/")[1];
    }

    private String savePicToLocal(MultipartFile file,String md5edID){
        String prefix=file_path;
        prefix+="images//";
        //System.out.println(1);
//        String file_name_prefix=file.getOriginalFilename().split("\\.")[0];
        String file_name_suffix="."+file.getOriginalFilename().split("\\.")[1];
        String renamed_file_name = md5edID +file_name_suffix;
        File descFile=new File(prefix+renamed_file_name);
        //System.out.println(2);

        try{file.transferTo(descFile);}
        catch(Exception e){ System.out.println(e); };
        System.out.println("get path is "+descFile.getPath());
        //return descFile.getPath().split("files")[1];
        String ret=descFile.getPath().replace("\\","/");
        return ret.split("files/")[1];
    }
    //记录文件操作  path是相对路径（参考数据库中存储的格式）
    public void logOp(String path, String uno, int type){
        Date cdate=new Date();
        FileOperateInfo ci=new FileOperateInfo();

        int file_id=info.selectByUrl(path).getFileId();

        ci.setFileId(file_id);
        ci.setOperateTime(cdate);
        ci.setUno(uno);
        ci.setOperateType(type);
        op.insert(ci);

    }

    public double getFileSizeByName(String file_name){
//        System.out.println("11111");
        String full_path = file_path + (info.selectAddrByFileName(file_name).replaceAll("/","//"));
        System.out.println("full_path");
        File file = new File(full_path);
        System.out.println(file.getPath());
        double file_size_kb = file.length() / 1024.0;
        double file_size_mb = file_size_kb / 1024.0;
        return file_size_mb;
    }

    public String getFileSizeByPath(String file_address){
        String full_path =file_path+file_address;
//        System.out.println(full_path);
        File file = new File(full_path);
//        System.out.println(file.getPath());
        double file_size_kb = file.length() / 1024.0;
        DecimalFormat df = new DecimalFormat("#.00");
        double file_size_mb = file_size_kb / 1024.0;

        return String.format("%.2f", file_size_mb)+"MB";
    }

    public Map<String,Object> saveFileToLocalList(String fileName) throws IOException {
        Map<String, Object> files =new HashMap<>();
        String prefix=file_path;
        prefix+="docs//";
        //System.out.println(1);
        File descFile=new File(prefix+fileName);
        String file_name_prefix=fileName.split("\\.")[0];
        String file_name_suffix="."+fileName.split("\\.")[1];
        //System.out.println(2);
        int i=1;
        String new_name=prefix+fileName;
        if(!descFile.exists()){
            descFile.createNewFile();
        }
        descFile=new File("/"+new_name);
        System.out.println("get path is "+descFile.getPath());
        String ret=descFile.getPath().replace("\\","/");
        files.put("address",ret.split("files/")[1]) ;
        files.put("file",descFile);
        return files;
    }
}
