package org.exercise.mapper;

import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.exercise.entity.StaName;

public interface StaNameMapperAnnotation {
    @Select("SELECT * FROM `sta_name` WHERE code = #{code}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property="name", column = "name"),
            @Result(property = "staCode", column = "code", one = @One(select = "org.exercise.mapper.StaCodeMapper.getObjectByCode"))
    })
    StaName getObjectByCode(Integer code);
}
