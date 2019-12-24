package com.cxgzs.seweb.controller;

import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.model.Student;
import com.cxgzs.seweb.service.FileService;
import com.cxgzs.seweb.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/debug")
public class DebugController {
    @Autowired
    private FileService files;
    @Resource
    StudentMapper stumapper;

    @PostMapping("/generate_student")
    public String genStudent(HttpServletRequest request){
        String kkk=request.getParameter("1");
        int num=40;
        String name="毛清卓薛红豆胡之瑶余绿蕊陈梅梅冯雁丝唐厦洁戴子琳郑龙艳尹蔚星邱朵薇陆晨星吕菱凡汪芸英易悠雅梁芸欣袁长文白一南罗傲菡漕柯洁谢梵听徐彦芝宋凌萱张新林陈天菱沈乐悦史芮欣田夜白程天欣刘水卉姜梓云乔仪琳余灵韵钟晓凡徐雨彤王春霞史冰莹黎静晨沈怡婷萧问旋钟炜琳常颀秀孙泽恩何静秀许艾达孟小莉江冰海谢梨落叶七微唐香菱康思枫孙蓉洁段问柳杜悦婷王曼蔓曹成美彭叶春夏映蓉董从菡郝端静范鹤梅曾含香孔青香杜方方余悠逸尹平蓝顾翠琴许天亦石痴梦文慧恩梁香芹秦林帆江吉星何白亦孙佳莉武晓巧阎雨莹孙梓颖汪格菲黄如天丁家颖傅慧颖冯燕桦梁会雯叶紫蓝贾颖然乔幻梅黎如之邹柔怀曾新雅郑傲柏王映秋毛倩成邱可嘉赖绮玉孟依娜陆经文邱致欣潘贞芳曾方玉";
        for(int i=221600101;num!=0;num--,i++){
            Student stu=new Student();
            stu.setName(name.substring(num*3,num*3+3));
            Integer kk=i,cc=num+11;
            stu.setSno(kk.toString());
            stu.setClassId(1);
            stu.setLessonId(1);
            stu.setCollege("数学与计算机科学学院");
            stu.setEnrollmentYear(2016);
            stu.setGender(num>20?0:1);
            stu.setPassword("e10adc3949ba59abbe56e057f20f883e");
            stu.setMajor("软件工程");

            stu.setTelNum("130673024"+cc.toString());

            String old_file="images/025d16115acc936e2bb25acc799d0b85.jpg";
            String new_file="images/"+ MD5Util.MD5Encode(kk.toString(),"utf8");
            stu.setPhoUrl(new_file);
            stumapper.insertSelective(stu);
        }
        return "ok";
    }

    @PostMapping("/file_upload")
    public String getHwList(@RequestParam("file") MultipartFile file,
                            HttpServletRequest request){
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        return files.saveFile(file,1,"221212121",1);
    }

    @PostMapping("/zip_files")
    public String zipFiles(HttpServletRequest request){
        File f1=new File("kk.txt");
        File f2=new File("yy.txt");
        try {
            f1.createNewFile();
            f2.createNewFile();
        }
        catch (Exception e){
            System.out.println(e);
        }
        List<File> lis=new ArrayList<File>();
        lis.add(f1); lis.add(f2);
        return files.zipFiles(lis);
    }

}
