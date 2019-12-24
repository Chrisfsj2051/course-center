package com.cxgzs.seweb.controller;


import com.cxgzs.seweb.service.ExamService;
import com.cxgzs.seweb.util.GetRequestJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @PostMapping("/get_paper")
    public Map<String, Object> getPaper(HttpServletRequest request) {
        int paper_id = Integer.parseInt(request.getParameter("paper_id"));
        return examService.getPaper(paper_id);
    }

    @PostMapping("/get_my_mark")
    public Map<String, Object> getMyMark(HttpServletRequest request) {
        int paper_id = Integer.parseInt(request.getParameter("paper_id"));
        String sno=request.getParameter("sno");
        return examService.getMyMark(paper_id,sno);
    }

    @PostMapping("/get_paper_list")
    public Map<String, Object> getPaperList(HttpServletRequest request) {
        int lesson_id = Integer.parseInt(request.getParameter("cid"));
        int paper_type = Integer.parseInt(request.getParameter("paper_type"));
        int start = Integer.parseInt(request.getParameter("start"));
//        int count = Integer.parseInt(request.getParameter("count"));
//        System.out.println(lesson_id);
        int count = 10;
        return examService.getPaperList(lesson_id,paper_type,start,count);
    }

    @PostMapping("/rel_paper")
    public Map<String,Object> releasePaper(HttpServletRequest request) throws IOException {
        return examService.releasePaper(GetRequestJsonUtil.getRequestJsonObject(request));
    }

    @PostMapping("/rel_question")
    public Map<String,Object> releaseQuestion(HttpServletRequest request) throws IOException {
        return examService.releaseSoloQues(GetRequestJsonUtil.getRequestJsonObject(request));
    }

    @PostMapping("/set_score")
    public Map<String,Object> setScore(HttpServletRequest request)  {
        String uno = request.getParameter("uno");
        int paper_id = Integer.parseInt(request.getParameter("paper_id"));
        int score = Integer.parseInt(request.getParameter("score"));
        return examService.setPaperScore(uno,paper_id,score);
    }

    @PostMapping("/del_question")
    public Map<String,Object> deleteQuestion(HttpServletRequest request)  {
        int paper_id = Integer.parseInt(request.getParameter("paper_id"));
        int question_id = Integer.parseInt(request.getParameter("question_id"));
        return examService.delQues(paper_id,question_id);
    }

    @PostMapping("/del_paper")
    public Map<String,Object> deletePaper(HttpServletRequest request)  {
        int paper_id = Integer.parseInt(request.getParameter("paper_id"));
        return examService.delPaper(paper_id);
    }

    @PostMapping("/create_paper")
    public Map<String,Object> createPaper(HttpServletRequest request)  {
        int lesson_id = Integer.parseInt(request.getParameter("lesson_id"));
        int paper_type = Integer.parseInt(request.getParameter("paper_type"));
        String title = request.getParameter("title");
        return examService.createEmptyPaper(lesson_id,paper_type,title);
    }
}
