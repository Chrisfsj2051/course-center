package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(String ano);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(String ano);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}