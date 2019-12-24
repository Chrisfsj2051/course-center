package com.cxgzs.seweb.dao;

import com.cxgzs.seweb.model.LoginInfo;

import java.util.Date;
import java.util.List;

public interface LoginInfoMapper {
    int deleteByPrimaryKey(Integer uno);

    int insert(LoginInfo record);

    int insertSelective(LoginInfo record);

    LoginInfo selectByPrimaryKey(String uno);

    Date selectThisByUserId(String uno);

    LoginInfo selectLastByUserId(LoginInfo record);

    int updateByPrimaryKeySelective(LoginInfo record);

    int updateByPrimaryKey(LoginInfo record);
}