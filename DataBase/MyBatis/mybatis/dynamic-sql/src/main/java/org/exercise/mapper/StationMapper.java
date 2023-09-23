package org.exercise.mapper;

import org.apache.ibatis.annotations.Param;
import org.exercise.entity.Station;

import java.util.List;
import java.util.Map;


public interface StationMapper {
    //使用#{code}虽然属性名和形参名保持一致，但是无法获得参数，通过注解配置可解决此问题
    List<Station> findStationByCode(@Param(value = "code") Integer code);
    List<Station> findStationByCodeAndDistance(Station station);
    List<Station> findStationByMultiConditions(Map<String,Object> map);
    List<Station> findStationByCode_foreach(Map<String,Object> map);
    List<Station> findStationByName_trim(Map<String,Object> map);
    void updateStation(Map<String,Object> map);
}
