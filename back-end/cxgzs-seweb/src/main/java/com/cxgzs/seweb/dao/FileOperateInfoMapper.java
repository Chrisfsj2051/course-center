package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.FileOperateInfo;

import java.util.List;
import java.util.Map;

public interface FileOperateInfoMapper {
    int deleteByPrimaryKey(Integer fileOperateId);

    int deleteByFileId(Integer fileId);

    int insert(FileOperateInfo record);

    int insertSelective(FileOperateInfo record);

    FileOperateInfo selectByPrimaryKey(Integer fileOperateId);

    FileOperateInfo selectByFileIdAndType(Integer fileId, Integer operateType);
    
    int selectCountByUserId(String uno);

    List<Map<String,Object>> selectByUserId(String uno);

    int updateByPrimaryKeySelective(FileOperateInfo record);

    int updateByPrimaryKey(FileOperateInfo record);
}