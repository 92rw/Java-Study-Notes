package org.exercise.mapper;

import org.apache.ibatis.annotations.Param;
import org.exercise.entity.StaCode;

import java.util.List;
import java.util.Map;


public interface StaCodeMapper {
    //正常的查询方法
    StaCode getObjectByCode(Integer code);
    //反向级联查询的方法
    StaCode getReverseObject(Integer code);
}
