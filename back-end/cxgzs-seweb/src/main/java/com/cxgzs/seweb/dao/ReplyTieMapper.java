package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.ReplyTie;

import java.util.List;
import java.util.Map;

public interface ReplyTieMapper {
    int deleteByPrimaryKey(Integer replyId);

    int deleteByTieId(Integer tieId);

    int insert(ReplyTie record);

    int insertSelective(ReplyTie record);

    ReplyTie selectByPrimaryKey(Integer replyId);

    List<Map<String,Object>> selectByTieId(Integer tieId);

    int updateByPrimaryKeySelective(ReplyTie record);

    int updateByPrimaryKey(ReplyTie record);
}