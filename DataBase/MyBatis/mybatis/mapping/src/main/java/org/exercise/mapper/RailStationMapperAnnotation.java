package org.exercise.mapper;

import org.apache.ibatis.annotations.*;
import org.exercise.entity.RailStation;

import java.util.List;

public interface RailStationMapperAnnotation {
    @Select("SELECT * FROM `railstation` WHERE `line` = #{railway}")
    @Results(id = "RailStationMap", value = {
            @Result(id = true, property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "line", column = "line", one = @One(select = "org.exercise.mapper.RailwayMapperAnnotation.getRailwayByName"))
    })
    List<RailStation> getStaionByRailway(String railway);

    @ResultMap("RailStationMap")//和上面的复用
    @Select("SELECT * FROM `railstation` WHERE `name` = #{name}")
    RailStation getStationByName(String name);
}
