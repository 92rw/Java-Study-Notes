package org.exercise.mapper;

import org.exercise.entity.RailStation;

import java.util.List;

public interface RailStationMapper {
    List<RailStation> getStaionByRailway(String railway);
    RailStation getStationByName(String name);
}
