package org.exercise.mapper;

import org.apache.ibatis.annotations.Select;
import org.exercise.entity.Station;

public interface StationAnnotation {
    @Select("SELECT * FROM `station` WHERE `id` = #{id}")
    Station getStationById(Integer id);
}
