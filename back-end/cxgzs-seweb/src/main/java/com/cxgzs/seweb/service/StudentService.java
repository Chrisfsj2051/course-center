package com.cxgzs.seweb.service;
//import com.cxgzs.seweb.service.StudentService;
import com.cxgzs.seweb.dao.LoginInfoMapper;
import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.model.LoginInfo;
import com.cxgzs.seweb.model.Student;
import jxl.write.WriteException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import java.io.File;
import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

@Service("studentService")
public class StudentService{

    @Resource
    private StudentMapper studentMapper;
    @Resource
    private LoginInfoMapper loginInfoMapper;
    @Resource
    FileService fileservice;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");

    public Student getUserById(String userId) {

        return studentMapper.selectByPrimaryKey(userId);

    }

    public Map<String,Object> getStudentById(String id,String prefix){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Student s=studentMapper.selectByPrimaryKey(id);
        if(s==null){
            ret.put("state_code",1);
            return ret;
        }
        else {
            ret.put("state_code",0);
            ret.put("user_type","学生");
            ret.put("pho_url",fileservice.getFileUrl(s.getPhoUrl(),prefix));
            ret.put("no",s.getSno());
            ret.put("name",s.getName());
            ret.put("phone",s.getTelNum());
            ret.put("college",s.getCollege());
            ret.put("major",s.getMajor());
            ret.put("classid",s.getClassId());
            ret.put("nickname",s.getNickname());
            ret.put("lessonid",s.getLessonId());
            int gender=s.getGender();
            if(gender==0) ret.put("gender","男");
            else ret.put("gender","女");
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

    public boolean addUser(Student record){
        boolean result = false;
        try {
            studentMapper.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public Map<String,Object> updateStudentPasswordById(String id,String pwd){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Student student=new Student();
        student.setSno(id);
        student.setPassword(pwd);
        if(studentMapper.updateByPrimaryKeySelective(student)!=0){
            ret.put("state_code",0);
        }
        else
            ret.put("state_code",1);
        return ret;
    }

    public Map<String,Object> updateStudentInfoById(String id,String phone,String nickname,String prefix){

        Map<String , Object> ret = new HashMap<String ,Object>();
        Student student=new Student();
        student.setSno(id);
        student.setTelNum(phone);
        student.setNickname(nickname);
        if(studentMapper.updateByPrimaryKeySelective(student)!=0){
            ret=getStudentById(id,prefix);
        }
        else
            ret.put("state_code",1);
        return ret;
    }

    public Map<String,Object> updateStudentPicById(MultipartFile file, String id,String net_prefix){
        String address = fileservice.updPhoto(file,id);

        Map<String , Object> ret = new HashMap<String ,Object>();
        ret.put("state_code",0);
        ret.put("pho_url",fileservice.getFileUrl(address,net_prefix));
        return ret;

    }

    public  Map<String,Object> login(String id,String pwd,String ip){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Student student = studentMapper.selectByPrimaryKey(id);
        if(student==null){
            ret.put("state_code",1);
            return ret;
        }
        if(!pwd.equals(student.getPassword())){
            ret.put("state_code",2);
            return ret;
        }
        else {
            ret.put("state_code",0);
            ret.put("user_type","Student");
            LoginInfo info=new LoginInfo();
            info.setUno(id);
            info.setLoginTime(new Date());
            info.setLoginIp(ip);
            loginInfoMapper.insertSelective(info);
            return ret;
        }
    }
    public  Map<String,Object> verify(String id,String pwd){
        Map<String , Object> ret = new HashMap<String ,Object>();
        Student student = studentMapper.selectByPrimaryKey(id);
        if(student==null){
            ret.put("state_code",1);
            return ret;
        }
        if(!pwd.equals(student.getPassword())){
            ret.put("state_code",2);
            return ret;
        }
        else {
            ret.put("state_code",0);
            return ret;
        }
    }

    public List<Student> getAll(int lesson_id){
        return studentMapper.selectAllByLessonId(lesson_id);
    }

    public Map<String, Object> generateXls(List<Student> stu) throws IOException, WriteException {
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> file_info = new HashMap<>();
        String lessonid = stu.get(0).getLessonId()==null?"XX": String.valueOf(stu.get(0).getLessonId());
        String fileName = lessonid +"班花名册.xls";
        file_info=fileservice.saveFileToLocalList(fileName);
        WritableWorkbook wwb = Workbook.createWorkbook((File) file_info.get("file"));
        WritableSheet ws =wwb.createSheet("MyStudent",0);

        Label labelId = new Label(0,0,"学号");
        Label labelName = new Label(1,0,"姓名");
        Label labelGender = new Label(2,0,"性别");
        Label labelTelNum = new Label(3,0,"手机号码");
        Label labelCollege = new Label(4,0,"学院");
        Label labelMajor = new Label(5,0,"专业");
        Label labelYear = new Label(6,0,"入学年份");
        Label labelClass = new Label(7,0,"班级");
//        Label labelNickName = new Label(8,0,"昵称");

        ws.addCell(labelId);
        ws.addCell(labelName);
        ws.addCell(labelGender);
        ws.addCell(labelTelNum);
        ws.addCell(labelCollege);
        ws.addCell(labelMajor);
        ws.addCell(labelYear);
        ws.addCell(labelClass);
        for(int i = 0 ; i < stu.size() ; i++){
            Label id = new Label(0,i+1,stu.get(i).getSno());
            Label name = new Label(1,i+1,stu.get(i).getName());
            Label gender = new Label(2,i+1,stu.get(i).getGender()==0?"男":"女");
            Label telnum = new Label(3,i+1,stu.get(i).getTelNum());
            Label college = new Label(4,i+1,stu.get(i).getCollege());
            Label major = new Label(5,i+1,stu.get(i).getMajor());
            Label year = new Label(6,i+1,""+stu.get(i).getEnrollmentYear());
//            System.out.println(stu.get(i).getEnrollmentYear());
            Label Lclass = new Label(7,i+1,""+stu.get(i).getClassId());
            ws.addCell(id);
            ws.addCell(name);
            ws.addCell(gender);
            ws.addCell(telnum);
            ws.addCell(college);
            ws.addCell(major);
            ws.addCell(year);
            ws.addCell(Lclass);
        }
        wwb.write();
        System.out.println("导出成功");
        wwb.close();
        ret.put("file_path",file_info.get("address"));
        return ret;
    }



}