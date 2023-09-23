package org.exercise.mapper;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.exercise.entity.Railway;

public interface RailwayMapperAnnotation {
    @Select("SELECT * FROM `railway` WHERE `name` = #{name}")
    @Results({
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "stations", column = "name", many = @Many(select = "org.exercise.mapper.RailStationMapperAnnotation.getStaionByRailway"))
    })
    Railway getRailwayByName(String name);
}
