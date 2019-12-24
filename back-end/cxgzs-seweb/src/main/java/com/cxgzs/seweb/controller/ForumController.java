package com.cxgzs.seweb.controller;

import com.cxgzs.seweb.service.FileService;
import com.cxgzs.seweb.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/forum")
public class ForumController {
    @Autowired
    private ForumService forum;

    @PostMapping("/post_tie")
    public Map<String, Object> postTie(HttpServletRequest request){
        String uno=request.getParameter("uno"),title=request.getParameter("title");
        String content=request.getParameter("content");
        int type=Integer.parseInt(request.getParameter("type"));
        return forum.postTie(uno,title,content,type);
    }
    @PostMapping("/get_tie_list")
    public Map<String, Object> getTieList(HttpServletRequest request){
        int type=Integer.parseInt(request.getParameter("type"));
        int page=Integer.parseInt(request.getParameter("page"));
        return forum.getTieList(type,page,"-1");
    }
    @PostMapping("/get_tie")
    public Map<String, Object> getTie(HttpServletRequest request){
        int page=Integer.parseInt(request.getParameter("page"));
        int tie_id=Integer.parseInt(request.getParameter("tie_id"));
        String net_prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return forum.getTie(tie_id,page,net_prefix);
    }
    @PostMapping("/reply_tie")
    public Map<String, Object> replyTie(HttpServletRequest request){
        String uno=request.getParameter("uno");
        String content=request.getParameter("content");
        int tie_id=Integer.parseInt(request.getParameter("tie_id"));
        int reply_id=Integer.parseInt(request.getParameter("reply_id"));
        return forum.replyTie(uno,tie_id,content,reply_id);
    }
    @PostMapping("/delete_tie")
    public Map<String, Object> deleteTie(HttpServletRequest request){
        int tie_id=Integer.parseInt(request.getParameter("tie_id"));
        return forum.deleteTie(tie_id);
    }
    @PostMapping("/get_my_tie_list")
    public Map<String, Object> getMyTieList(HttpServletRequest request){
        int type=Integer.parseInt(request.getParameter("type"));
        int page=Integer.parseInt(request.getParameter("page"));
        String uno=request.getParameter("uno");
        return forum.getTieList(type, page, uno);
    }
}
