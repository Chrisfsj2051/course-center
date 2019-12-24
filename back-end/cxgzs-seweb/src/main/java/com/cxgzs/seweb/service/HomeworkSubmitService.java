package com.cxgzs.seweb.service;


import com.cxgzs.seweb.dao.HomeworkMapper;
import com.cxgzs.seweb.dao.HomeworkSubmitMapper;
import com.cxgzs.seweb.model.HomeworkSubmit;
import com.cxgzs.seweb.model.HomeworkSubmitKey;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.annotation.Resource;

@Service("HomeworkSubmitService")
public class HomeworkSubmitService {
    @Resource
    HomeworkSubmitMapper homeworkSubmitMapper;
    HomeworkMapper hwMapper;
    @Resource
    HomeworkService hws;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");

    public Map<String, Object> getMyHomeworkList(String no) {
        HomeworkSubmitKey k = new HomeworkSubmitKey();
        Map<String, Object> ret = new HashMap<String, Object>();

        k.setSno(no);
        List<Map<String, Object>> lis = homeworkSubmitMapper.selectByStudentId(k);

        for (Map<String, Object> item : lis) {
            item.put("upload_time", sdf.format((Date) item.get("upload_time")));
        }
        ret.put("state_code", 0);
        ret.put("list", lis);

        return ret;
    }

    public String getStatusByPrimaryKey(HomeworkSubmitKey key) {
        HomeworkSubmit hw = homeworkSubmitMapper.selectByPrimaryKey(key);
        if (hw != null) return "已提交";
        else return "未提交";
    }

    public Map<String, Object> getMyHomeworkListStatus(int cid, String no) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> result = new HashMap<String, Object>();


        Map<String, Object> tmp = hws.getHomeworkList(cid, -1);
//        return tmp;
        List<Map<String, Object>> tmpList = (List<Map<String, Object>>) tmp.get("homeworkList");
        for (Map<String, Object> item :
                tmpList) {
            HomeworkSubmitKey k = new HomeworkSubmitKey();
            k.setHomeworkId((Integer) item.get("homework_id"));
            k.setSno(no);
//            System.out.println(k.getHomeworkId()+" "+k.getSno());
            Map<String, Object> retItem = new HashMap<String, Object>();
            retItem.put("homework_id",k.getHomeworkId());
            retItem.put("status", getStatusByPrimaryKey(k));
            retItem.put("title", item.get("title"));//从content改为title
            retItem.put("release_time", item.get("release_time"));
            retItem.put("deadline", item.get("deadline"));
            list.add(retItem);
        }
        result.put("state_code", 0);
        result.put("my_homework_list", list);
        return result;
    }
}
