package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.Tie;

import java.util.List;
import java.util.Map;

public interface TieMapper {
    int deleteByPrimaryKey(Integer postId);

    int insert(Tie record);

    int insertSelective(Tie record);

    Tie selectByPrimaryKey(Integer postId);

    List<Map<String,Object> > selectByType(Integer type);

    int updateByPrimaryKeySelective(Tie record);

    int updateByPrimaryKey(Tie record);
}