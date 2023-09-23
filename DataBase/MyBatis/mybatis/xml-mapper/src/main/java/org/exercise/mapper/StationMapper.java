package org.exercise.mapper;

import org.exercise.entity.Station;

import java.util.List;
import java.util.Map;

public interface StationMapper {
    //通过id或名字查询
    List<Station> findStationByNameOrId(Station station);
    //通过车站编码查询
    List<Station> findStationByCode(Integer code);
    //通过id和distance查询
    List<Station> findStationByIdAndDistance_ParamMap(Map<String,Object> map);
    List<Map<String, Object>> findStationByIdAndDistance_ReturnMap(Map<String,Object> map);
}
