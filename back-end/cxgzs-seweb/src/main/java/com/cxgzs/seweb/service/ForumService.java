package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.ReplyTieMapper;
import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.dao.TieMapper;
import com.cxgzs.seweb.model.ReplyTie;
import com.cxgzs.seweb.model.Student;
import com.cxgzs.seweb.model.Teacher;
import com.cxgzs.seweb.model.Tie;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("ForumService")
public class ForumService {
    @Resource
    TieMapper tieMapper;
    @Resource
    ReplyTieMapper replyTieMapper;

    @Resource
    UserService userservice;
    @Resource
    FileService fileservice;
    @Resource
    StudentMapper studentmapper;
    @Resource
    TeacherMapper teachermapper;


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");

    public Map<String, Object> postTie(String uno, String title, String content, int type) {
        Map<String, Object> ret = new HashMap<>();
        Date now = new Date();
        Tie t = new Tie();

        t.setUno(uno); t.setLastReplyUserid(uno);
        t.setType(type);  t.setTitle(title);
        t.setContent(content);  t.setPostTime(now);
        t.setLastReplyTime(now); t.setReplyNum(0);

        if (tieMapper.insertSelective(t) != 0) {
            ret.put("state_code", 0);
        } else ret.put("state_code", 1);
        return ret;
    }

    public Map<String, Object> replyTie(String uno, int tie_id, String content,int reply_to) {
        Map<String, Object> ret = new HashMap<>();
        ReplyTie rpl=new ReplyTie();
        rpl.setContent(content); rpl.setReplyTime(new Date());
        rpl.setTieId(tie_id); rpl.setUno(uno); rpl.setReplyTo(reply_to);

        Tie tie=tieMapper.selectByPrimaryKey(tie_id);
        tie.setReplyNum(tie.getReplyNum()+1);
        tie.setLastReplyUserid(uno);
        tie.setLastReplyTime(new Date());

        tieMapper.updateByPrimaryKey(tie);
        replyTieMapper.insert(rpl);
        ret.put("state_code",0);
        return ret;
    }

    public Map<String, Object> deleteTie(int tie_id) {
        Map<String,Object> ret=new HashMap<>();
        ret.put("state_code",0);
        replyTieMapper.deleteByTieId(tie_id);
        tieMapper.deleteByPrimaryKey(tie_id);
        return ret;
    }

    public Map<String,Object> getTieList(int type, int page, String constrain){
        Map<String, Object> ret = new HashMap<>();
        ret.put("state_code",0);

        List<Map<String,Object> > tie_list=tieMapper.selectByType(type);
        List<Map<String,Object> > tie_list_final=new ArrayList<Map<String, Object>>();

        int idx=0, totNum=tie_list.size();
        for(Map<String, Object> item : tie_list){
            if(constrain!="-1" && !((String)item.get("uno")).equals(constrain)){
                totNum-=1; continue;
            }
            if(idx<page*10-10||idx>=page*10){
                idx+=1; continue;
            }
            String post_no=(String)item.get("uno"), last_no=(String)item.get("last_reply_userid");
            String post_name=userservice.getNameByUno(post_no);
            String last_name=userservice.getNameByUno(last_no);
            String post_time=sdf.format((Date)item.get("post_time"));
            String last_time=sdf.format((Date)item.get("last_reply_time"));

            item.put("post_time",post_time);
            item.put("last_reply_time",last_time);
            item.put("last_reply_name",last_name);
            item.put("name",post_name);

            tie_list_final.add(item);

            idx+=1;
        }

        ret.put("tieList",tie_list_final);
        ret.put("tieNum",totNum);
        return ret;
    }

    public Map<String,Object> getTie(int tie_id, int page, String net_prefix){
        Map<String, Object> ret = new HashMap<>();
        ret.put("state_code",0);

        Tie tie=tieMapper.selectByPrimaryKey(tie_id);
        ret.put("title", tie.getTitle());
        ret.put("content",tie.getContent());
        ret.put("post_time",sdf.format((Date)tie.getPostTime()));
        ret.put("name",userservice.getNameByUno(tie.getUno()));
        ret.put("id",tie.getUno());
        ret.put("pho_url",getUserPho(tie.getUno(),net_prefix));
        ret.put("reply_num",tie.getReplyNum());

        List<Map<String,Object>> lis=replyTieMapper.selectByTieId(tie_id);

        for(int i=lis.size()-1;i>=0;--i){
            if(i>=page*10||i<(page-1)*10)
                lis.remove(i);
        }

        for(int i=0;i<lis.size();++i){
            Map<String,Object> item=lis.get(i);
            item.put("pho_url", getUserPho((String)item.get("uno"),net_prefix));
            item.put("reply_time",sdf.format((Date)item.get("reply_time")));
            item.put("reply_name",userservice.getNameByUno((String)item.get("uno")));
            Integer reply_to_id=(Integer)item.get("reply_to");
            if(reply_to_id==-1){
                item.put("reply_to",0);
                item.put("reply_to_name","no_use");
                item.put("reply_content","no_use");
            }
            else{
                item.put("reply_to",1);
                ReplyTie subrpl=replyTieMapper.selectByPrimaryKey(reply_to_id);
                item.put("reply_to_name",userservice.getNameByUno(subrpl.getUno()));
                item.put("reply_content",subrpl.getContent());
            }
            //item.remove("tie_id");
        }
        ret.put("replyList",lis);
        return ret;
    }

    private String getUserPho(String no, String net_prefix){
        Student stu=studentmapper.selectByPrimaryKey(no);
        Teacher tea=teachermapper.selectByPrimaryKey(no);

        String pho_url=(stu==null?tea.getPhoUrl():stu.getPhoUrl());
        return fileservice.getFileUrl(pho_url,net_prefix);
    }
}
