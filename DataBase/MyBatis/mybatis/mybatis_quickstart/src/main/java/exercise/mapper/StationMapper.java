package exercise.mapper;

import exercise.entity.Station;

import java.util.List;

public interface StationMapper {
    void addStation(Station station);
    void delStation(Integer id);
    void updateStation(Station station);
    Station getStationById(Integer id);
    List<Station> findAllStations();
}
