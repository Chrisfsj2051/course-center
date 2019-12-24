package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.*;
import com.cxgzs.seweb.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("CourseService")
public class CourseService {
    @Resource
    LessonMapper csMapper;
    @Resource
    TeacherMapper tMapper;
    @Resource
    StudentMapper sMapper;
    @Resource
    NoticeInfoMapper noticeMapper;
    @Resource
    FileInfoMapper finfoMapper;
    @Resource
    FileOperateInfoMapper fopMapper;
    @Resource
    FileService fileservice;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");

    public Map<String,Object> uploadPPT(MultipartFile file, Integer lesson_id, String uno){
        String url = fileservice.saveFile(file,lesson_id,uno,1);
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("state_code", 0);
        fileservice.logOp(url, uno, 1);
        return ret;
    }

    public Map<String,Object> logDownload(String url, String uno){
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("state_code", 0);
        url=url.split(":")[2].split("/",2)[1];
        fileservice.logOp(url, uno, 2);
        FileInfo fin=finfoMapper.selectByUrl(url);
        fin.setDownTimes(fin.getDownTimes()+1);
        finfoMapper.updateByPrimaryKey(fin);
        return ret;
    }

    public Map<String,Object> postNotice(String uno, String content, String title, int cid){
        NoticeInfo notice=new NoticeInfo();
        notice.setContent(content); notice.setLessonId(cid);
        notice.setTitle(title);  notice.setUno(uno);  notice.setPublishTime(new Date());

        noticeMapper.insert(notice);
        Map<String,Object> ret=new HashMap<String, Object>();
        ret.put("state_code",0);
        return ret;
    }

    public Map<String,Object> getRoster(int cid){
        List<Student> lis=sMapper.selectAllByLessonId(cid);
        List<Map<String,Object>> ret_lis=new ArrayList<>();
        for(int i=0;i<lis.size();++i){
            Student cur = lis.get(i);
            Map<String,Object> item = new HashMap<>();
            item.put("stuid",cur.getSno()); item.put("year",cur.getEnrollmentYear());
            item.put("gender", cur.getGender()); item.put("class",cur.getClassId());
            item.put("major", cur.getMajor()); item.put("tel",cur.getTelNum());
            item.put("college",cur.getCollege()); item.put("name",cur.getName());
            ret_lis.add(item);
        }
        Map<String,Object> ret=new HashMap<>();
        ret.put("state_code",0); ret.put("stuList",ret_lis);
        return ret;
    }

    public Map<String,Object> getPPTList(Integer lesson_id, String net_prefix){
        List<Map<String,Object>> lis=finfoMapper.selectByLessonIdAndType(lesson_id,1);
        Map<String,Object> ret=new HashMap<String, Object>();
        for(int i=0;i<lis.size();++i){
            Map<String ,Object> item=lis.get(i);

            String uno=(String)item.get("file_by_id");
            String tname=tMapper.selectByPrimaryKey(uno).getName();
            String url=(String)item.get("file_address");

            item.put("preview_url",fileservice.getPreviewUrl(url,net_prefix));
            url=fileservice.getFileUrl(url, net_prefix);

            FileOperateInfo tt=fopMapper.selectByFileIdAndType(
                    (Integer)item.get("file_id"),1);

            item.put("operate_time", sdf.format(tt.getOperateTime()));
            item.put("file_address", url);  item.put("by", tname);

            item.remove("file_by_id");
            item.remove("belong_lesson");
            item.remove("type");
            lis.set(i, item);
        }
        ret.put("state_code",0);
        ret.put("fileList", lis);
        return ret;
    }

    public  Map<String,Object> getNotice(Integer nid){
        Map<String,Object> ret=new HashMap<String,Object>();
        NoticeInfo notice_info=noticeMapper.selectByPrimaryKey(nid);
        int cid=notice_info.getLessonId();
        Map<String,Object> tmp=getNoticeList(cid);

        for(Map<String,Object> obj : (List<Map<String,Object>>)tmp.get("noticeList")){
            if((int)(obj.get("notice_id"))==nid){
                ret.put("data", obj);
            }
        }
        ret.put("state_code",0);
        return ret;
    }

    public Map<String,Object> uploadOutline(MultipartFile file, String uno, int cid){
        String file_url = fileservice.saveFile(file,cid,uno,3);
        FileInfo fi=finfoMapper.selectByUrl(file_url);
        finfoMapper.deleteByPrimaryKey(fi.getFileId());
        Lesson les=csMapper.selectByPrimaryKey(cid);
        les.setSyllabus(file_url);

        csMapper.updateByPrimaryKey(les);
        Map<String,Object> ret=new HashMap<>();
        ret.put("state_code",0);
        return ret;
    }

    public Map<String,Object> deletePPT(int fid){
        fopMapper.deleteByFileId(fid);
        finfoMapper.deleteByPrimaryKey(fid);
        Map<String,Object> ret=new HashMap<>();
        ret.put("state_code",0);
        return ret;
    }

    public Map<String,Object> getOutline(int cid,String net_prefix){
        String file_url=csMapper.selectByPrimaryKey(cid).getSyllabus();
        Map<String,Object> ret=new HashMap<>();
        ret.put("state_code",0);
        ret.put("preview_url",fileservice.getPreviewUrl(file_url,net_prefix));
        return ret;
    }

    public Map<String, Object> getNoticeList(Integer cid)
        { return getNoticeList(cid, -1); }

    public Map<String, Object> getNoticeList(Integer cid,int page){
        //System.out.println(page);
        Map<String,Object> ret=new HashMap<String,Object>();
        List<Map<String, Object>> noticeList=noticeMapper.selectByLessonId(cid);
        int idx=0;
        ret.put("noticeNum", noticeList.size());
        for(int i=0;i<noticeList.size();++i){
            if(page!=-1&&(idx<page*10-10||idx>=page*10)){
                noticeList.remove(i);
                i--; idx++; continue;
            }
            idx++;
            Map<String,Object> notice=noticeList.get(i);

            String tno=(String)notice.get("uno");
            String tname=tMapper.selectByPrimaryKey(tno).getName();
            notice.remove("tno");
            notice.put("by", tname);

            Date time=(Date)notice.get("publish_time");
            notice.remove("publish_time");
            notice.put("time", sdf.format(time));

            notice.remove("uno");
            notice.remove("lesson_id");

            noticeList.set(i, notice);
        }

        ret.put("state_code",0);
        ret.put("noticeList", noticeList);

        return ret;
    }

    public Map<String,Object> getIntro(int cid, String net_prefix){
        Lesson cur = csMapper.selectByPrimaryKey(cid);
        String ss=(String)cur.getTno();
        //System.out.println("ss="+ss);
        Teacher tea=tMapper.selectByPrimaryKey(ss);
        String tName = tea.getName();

        Map<String, Object> ret = new HashMap<String, Object>();

        String hybird=cur.getLessonIntro();
        String res[]=hybird.split("\\$");

        List<String> pre=new ArrayList<String>();

        for(int i=1;i<res.length;++i)
            pre.add(res[i]);
        if(pre.size()==0)
            pre.add("无");

        ret.put("pre_knowledge",pre);
        ret.put("state_code", 0);
        ret.put("pho_url", fileservice.getFileUrl(cur.getPhoUrl(),net_prefix));
        ret.put("teacher", tName);
        ret.put("c_intro", res[0]);
        ret.put("year", cur.getReleaseYear());
        ret.put("term", cur.getReleaseSemester());

        return ret;
    }
}
