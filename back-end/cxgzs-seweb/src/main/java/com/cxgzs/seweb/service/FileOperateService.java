package com.cxgzs.seweb.service;

import com.cxgzs.seweb.dao.FileInfoMapper;
import com.cxgzs.seweb.dao.FileOperateInfoMapper;
import com.cxgzs.seweb.dao.TeacherMapper;
import com.cxgzs.seweb.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("FileOperateService")
public class FileOperateService {
    @Resource
    FileOperateInfoMapper fileOperateInfoMapper;
    @Resource
    FileInfoMapper f;
    @Resource
    TeacherMapper t;
    @Resource
    FileService fs;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd kk:mm");
    public Map<String, Object> myRecord(String id) {
        Map<String , Object> ret = new HashMap<String ,Object>();
        List<Map<String, Object>> list =fileOperateInfoMapper.selectByUserId(id);
        for(Map<String, Object> item:list){
            int fid= (int) item.get("file_id");
            FileInfo fi=f.selectByPrimaryKey(fid);
            item.put("file_name",fi.getFileName());
//            System.out.println(fi.getFileName());

//            item.put("file_size",fs.getFileSizeByName(fi.getFileName()));
//            item.put("file_size",fs.getFileSizeByPath(fi.getFileAddress()));
            System.out.println(fi.getFileId());
            System.out.println(t.selectByPrimaryKey(fi.getFileById()));
            System.out.println(t.selectByPrimaryKey(fi.getFileById()).getName());
            item.put("uploader",t.selectByPrimaryKey(fi.getFileById()).getName());
            item.put("operate_type",(int)item.get("operate_type")==1?"上传":"下载");
            item.put("operate_time", sdf.format((Date)item.get("operate_time")));
            item.remove("file_operate_id");
            item.remove("file_id");
            item.remove("uno");
        }
        ret.put("state_code",0);
        ret.put("myRecord",list);
        return ret;
    }
}
