package com.cxgzs.seweb.controller;

import com.cxgzs.seweb.service.HomeworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/hw")
public class HomeworkController {
    @Autowired
    private HomeworkService hws;

    @PostMapping("/get_hw_list")
    public Map<String, Object> getHwList(HttpServletRequest request){
        int cid=Integer.parseInt(request.getParameter("cid"));
        int page=Integer.parseInt(request.getParameter("page"));
        return hws.getHomeworkList(cid,page);
    }

    @PostMapping("/submit_hw")
    public Map<String, Object> submitHw(@RequestParam("file") MultipartFile file,
                                        HttpServletRequest request){

        int hw_id=Integer.parseInt(request.getParameter("hw_id"));
        int lesson_id=Integer.parseInt(request.getParameter("lesson_id"));
        String uno=request.getParameter("uno");

        return hws.submitHw(file,hw_id,uno,lesson_id);
    }

    @PostMapping("/get_hw")
    public Map<String, Object> getHwContent(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("hw_id"));
        return hws.getHomeworkContent(id);
    }

    @PostMapping("/mark_hw")
    public Map<String, Object> markHw(HttpServletRequest request){
        String uno=request.getParameter("uno"), sug=request.getParameter("suggestion");
        int id=Integer.parseInt(request.getParameter("hw_id"));
        int mark=Integer.parseInt(request.getParameter("mark"));
        return hws.markHw(uno,id,mark,sug);
    }

    @PostMapping("/get_my_status")
    public Map<String, Object> getMyStatus(HttpServletRequest request){
        String uno=request.getParameter("sno");
        int id=Integer.parseInt(request.getParameter("hw_id"));
        return hws.getMyStatus(id,uno);
    }

    @PostMapping("/get_sub_hw_list")
    public Map<String, Object> getSubHwList(HttpServletRequest request){
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int id=Integer.parseInt(request.getParameter("hw_id"));
        int page=Integer.parseInt(request.getParameter("page"));
        return hws.getHomeworkSubmitList(id, page, net_prefix);
    }

    @PostMapping("/delete_hw")
    public Map<String, Object> deleteHomework(HttpServletRequest request){
        int id=Integer.parseInt(request.getParameter("hw_id"));
        return hws.deleteHomework(id);
    }

    @PostMapping("/get_zip")
    public Map<String, Object> getZip(HttpServletRequest request){
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        int id=Integer.parseInt(request.getParameter("hw_id"));
        return hws.getZipFile(id, net_prefix);
    }

    @PostMapping("/release_hw")
    public Map<String, Object> releaseHw(HttpServletRequest request){
        String user_id=request.getParameter("userid");
        String title=request.getParameter("title");
        String content=request.getParameter("content");
        String deadline_str=request.getParameter("deadline");
        Integer lesson_id=Integer.parseInt(request.getParameter("lessonid"));
        Date curDate=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");

        return hws.releaseHomework(user_id,lesson_id,title,content,sdf.format(curDate),deadline_str);
    }
}
