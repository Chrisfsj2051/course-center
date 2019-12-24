package com.cxgzs.seweb.controller;


import com.cxgzs.seweb.model.NoticeInfo;
import com.cxgzs.seweb.model.Student;
import com.cxgzs.seweb.service.CourseService;
import com.cxgzs.seweb.service.StudentService;
import jxl.write.WriteException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService cs;

    @Resource
    private StudentService s;

    @PostMapping("/intro")
    public Map<String, Object> getIntro(HttpServletRequest request) {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        return cs.getIntro(lesson_id,net_prefix);
    }

    @PostMapping("/get_notice")
    public Map<String, Object> getNotice(HttpServletRequest request) {
        int notice_id = Integer.parseInt(request.getParameter("nid"));
        return cs.getNotice(notice_id);
    }

    @PostMapping("/notice_list")
    public Map<String, Object> getNoticeList(HttpServletRequest request) {
        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        int page = Integer.parseInt(request.getParameter("page"));
        return cs.getNoticeList(lesson_id,page);
    }

    @PostMapping("/post_notice")
    public Map<String,Object> postNotice(HttpServletRequest request){
        String uno=request.getParameter("uno"),title=request.getParameter("title");
        String content=request.getParameter("content");
        Integer cid=Integer.parseInt(request.getParameter("cid"));
        return cs.postNotice(uno,content,title,cid);
    }

    @PostMapping("/get_roster")
    public Map<String,Object> getRoster(HttpServletRequest request){
        Integer cid=Integer.parseInt(request.getParameter("cid"));
        return cs.getRoster(cid);
    }

    @PostMapping("/get_ppt")
    public Map<String, Object> getPPTList(HttpServletRequest request) {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        return cs.getPPTList(lesson_id, net_prefix);
    }

    @PostMapping("/get_outline")
    public Map<String, Object> getOutline(HttpServletRequest request) {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        return cs.getOutline(lesson_id,net_prefix);
    }

    @PostMapping("/upload_ppt")
    public Map<String, Object> uploadPPT(@RequestParam("file") MultipartFile file,
                                         HttpServletRequest request) {

        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        String uno = request.getParameter("uno");
        return cs.uploadPPT(file, lesson_id, uno);
    }

    @PostMapping("/delete_ppt")
    public Map<String, Object> deletePPT(HttpServletRequest request) {
        int file_id = Integer.parseInt(request.getParameter("fid"));
        return cs.deletePPT(file_id);
    }

    @PostMapping("/upload_outline")
    public Map<String, Object> uploadOutline(@RequestParam("file") MultipartFile file,
                                            HttpServletRequest request) {

        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        String uno = request.getParameter("uno");
        return cs.uploadOutline(file,uno,lesson_id);
    }

    @PostMapping("/log_download")
    public Map<String, Object> logDownload(HttpServletRequest request) {

        String url = request.getParameter("url"), uno = request.getParameter("uno");
        return cs.logDownload(url, uno);
    }

    @PostMapping("get_stu_json")
    public List<Student> testAll(HttpServletRequest request){
        int lesson_id= Integer.parseInt(request.getParameter("cid"));
        return s.getAll(lesson_id);
    }
    @PostMapping("get_xls")
    public Map<String, Object> getXls(HttpServletRequest request) throws IOException, WriteException {
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int lesson_id= Integer.parseInt(request.getParameter("cid"));
        Map<String, Object> ret = s.generateXls(s.getAll(lesson_id));
        ret.put("file_path",net_prefix+"/"+ret.get("file_path"));
        return ret;
    }
}
