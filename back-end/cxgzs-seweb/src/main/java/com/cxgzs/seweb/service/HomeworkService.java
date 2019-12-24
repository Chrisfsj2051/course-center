package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.HomeworkMapper;
import com.cxgzs.seweb.dao.HomeworkSubmitMapper;
import com.cxgzs.seweb.dao.LessonMapper;
import com.cxgzs.seweb.dao.StudentMapper;
import com.cxgzs.seweb.model.Homework;
import com.cxgzs.seweb.model.HomeworkSubmit;
import com.cxgzs.seweb.model.HomeworkSubmitKey;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

@Service("HomeworkService")
public class HomeworkService {
    @Resource
    HomeworkMapper hwMapper;
    @Resource
    HomeworkSubmitMapper hwsubMapper;
    @Resource
    LessonMapper csmapper;
    @Resource
    StudentMapper stumapper;
    @Resource
    UserService userservice;
    @Resource
    FileService fileservice;
    @Resource
    FileServiceForHw fileserviceforhw;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");

    private int getStuNumByLessonId(int lesson_id){
        return stumapper.selectAllByLessonId(lesson_id).size();
    }

    private int getSubNumByHwId(int hw_id){
        return hwsubMapper.selectByHomeworkId(hw_id).size();
    }

    public Map<String,Object> getMyStatus(int hw_id, String sno){
        List<HomeworkSubmit> lis=hwsubMapper.selectByHomeworkId(hw_id);
        int idx=-1;
        for(int i=0;i<lis.size();++i){
            //System.out.println(lis.get(i).getSno()+",,,"+sno);
            if(lis.get(i).getSno().equals(sno))
                {idx=i; break; }
        }
        Map<String,Object> ret=new HashMap<>();
        ret.put("state_code", idx==-1?1:0);
        ret.put("score",idx==-1?0:lis.get(idx).getScore());
        ret.put("suggestion",idx==-1?"g":lis.get(idx).getSuggest());
        return ret;
    }

    public Map<String,Object> getHomeworkSubmitList(int hw_id,int page, String net_prefix){
        List<HomeworkSubmit> lis=hwsubMapper.selectByHomeworkId(hw_id);
        List<Map<String,Object>> ret_lis=new ArrayList<Map<String, Object>>();
        Map<String,Object> ret=new HashMap<>();
        ret.put("totNum",lis.size());
        int idx=0;

        for(int i=0;i<lis.size();++i){
            if(page!=-1&&(idx<page*10-10||idx>=page*10))
                { idx++; lis.remove(i--); continue; }
            idx++;
            HomeworkSubmit cur = lis.get(i);
            Map<String,Object> item=new HashMap<>();
            item.put("uno", cur.getSno());
            item.put("name",userservice.getNameByUno(cur.getSno()));
            item.put("mark",cur.getScore());
            item.put("suggestion",cur.getSuggest());
            item.put("post_time",sdf.format(cur.getUploadTime()));
            String final_url=fileservice.getFileUrl(cur.getFileAddress(),net_prefix);
            item.put("url", final_url);
            ret_lis.add(item);
        }

        ret.put("state_code",0);
        ret.put("data",ret_lis);
        return ret;
    }

    public Map<String,Object> getZipFile(int hw_id,String net_prefix){
        List<HomeworkSubmit> lis=hwsubMapper.selectByHomeworkId(hw_id);
        List<File> file_lis=new ArrayList<>();
        for(HomeworkSubmit hwsub : lis){
            System.out.println(hwsub.getFileAddress());
            file_lis.add(new File(fileservice.getFile_path()+hwsub.getFileAddress()));
        }

        String url=fileservice.zipFiles(file_lis, Integer.toString(hw_id)+"_");
        String ret_url=fileservice.getFileUrl(url, net_prefix);
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("state_code", getSubNumByHwId(hw_id)==0?2:0);
        ret.put("url",ret_url);
        return ret;
    }

    private boolean canSubmit(Date deadline){
        Date cur=new Date();
        return cur.before(deadline);
    }

    public Map<String,Object> submitHw(MultipartFile file, int hw_id ,String uno,int leid){
        String save_name=uno+"_"+Integer.toString(hw_id);
        String url=fileserviceforhw.saveFile(file,leid,uno,3,save_name);
        Map<String, Object> ret = new HashMap<String, Object>();

        Homework to_hw=hwMapper.selectByPrimaryKey(hw_id);
        ret.put("state_code", canSubmit(to_hw.getDeadline())?0:1);
        if((int)ret.get("state_code")==1)
            return ret;

        to_hw.setSubTimes(to_hw.getSubTimes()+1);
        hwMapper.updateByPrimaryKey(to_hw);


        fileservice.logOp(url, uno, 1);
        HomeworkSubmit sub=new HomeworkSubmit();
        sub.setScore(0); sub.setSuggest("无");
        sub.setHomeworkId(hw_id); sub.setSno(uno);
        sub.setFileAddress(url); sub.setUploadTime(new Date());

        HomeworkSubmitKey keys=new HomeworkSubmitKey();
        keys.setHomeworkId(hw_id); keys.setSno(uno);
        if(hwsubMapper.selectByPrimaryKey(keys)!=null)
            hwsubMapper.deleteByPrimaryKey(keys);
        hwsubMapper.insert(sub);
        fileservice.logOp(url,uno,1);
        return ret;
    }

    public Map<String, Object> markHw(String uno, int hw_id, int mark, String suggest){
        HomeworkSubmitKey keys=new HomeworkSubmitKey();
        keys.setSno(uno); keys.setHomeworkId(hw_id);
        HomeworkSubmit old=hwsubMapper.selectByPrimaryKey(keys);
        old.setScore(mark); old.setSuggest(suggest);
        hwsubMapper.updateByPrimaryKey(old);
        Map<String , Object> ret = new HashMap<String ,Object>();
        ret.put("state_code",0);
        return ret;
    }

    public Map<String,Object> getHomeworkList(int cid, int page){
        Map<String , Object> ret = new HashMap<String ,Object>();
        List<Map<String,Object>> lis = hwMapper.selectByLessonId(cid);
        int idx=0;  ret.put("totNum",lis.size());

        for(int i=0;i<lis.size();++i){
            if(page!=-1&&(idx<page*10-10||idx>=page*10)){
                lis.remove(i--);  idx++;
                continue;
            }
            idx++;
            Map<String,Object> item=lis.get(i);
            item.put("deadline", sdf.format((Date)item.get("deadline")));
            item.put("release_time", sdf.format((Date)item.get("release_time")));
            Integer subNum=getSubNumByHwId((Integer)item.get("homework_id"));
            Integer totNum=getStuNumByLessonId((Integer)item.get("lesson_id"));
            item.put("submit_times",subNum.toString()+"/"+totNum.toString());
            try{
                Date cur = sdf.parse((String)item.get("deadline"));
                item.put("enable", canSubmit(cur)?1:0);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        ret.put("state_code", 0);
        ret.put("homeworkList", lis);
        return ret;
    }

    public Map<String,Object> getHomeworkContent(int cid){
        Homework hw = hwMapper.selectByPrimaryKey(cid);
        Map<String , Object> ret = new HashMap<String ,Object>();
        ret.put("state_code",0);
        Map<String,Object> tmp=getHomeworkList(hw.getLessonId(), -1);
        for(Map<String,Object> items : (List<Map<String,Object>>) tmp.get("homeworkList")){
            if((int)items.get("homework_id")==cid){
                ret.put("data",items);
            }
        }
        return ret;
    }

    public Map<String,Object> deleteHomework(int hid){
        Map<String , Object> ret = new HashMap<String ,Object>();
        ret.put("state_code",0);
        hwsubMapper.deleteByHomeworkId(hid);
        hwMapper.deleteByPrimaryKey(hid);

        return ret;
    }

    public Map<String,Object> releaseHomework(String userid, int lessonid, String title,
                                              String content, String begStr, String edStr){

        Map<String, Object> ret=new HashMap<String, Object>();

        try{
            Date cur=sdf.parse(begStr), ddl=sdf.parse(edStr);
            if(cur.after(ddl)){ ret.put("state_code",2); return ret; }

        } catch (Exception e){}


        Homework cur=new Homework();
        cur.setTitle(title); cur.setContent(content);
        cur.setSubTimes(0);  cur.setLessonId(lessonid);

        try{
            cur.setReleaseTime(sdf.parse(begStr));
            cur.setDeadline(sdf.parse(edStr));
        }
        catch (Exception e){}
        hwMapper.insert(cur);

        ret.put("state_code",0);
        return ret;
    }

}


