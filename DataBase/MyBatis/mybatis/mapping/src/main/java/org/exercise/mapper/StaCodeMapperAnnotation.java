package org.exercise.mapper;

import org.apache.ibatis.annotations.Select;
import org.exercise.entity.StaCode;

public interface StaCodeMapperAnnotation {
    @Select("SELECT * FROM `sta_code` WHERE `code` = #{code}")
    StaCode getObjectByCode(Integer code);

}
