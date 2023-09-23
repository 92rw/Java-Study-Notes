package exercise.mapper;

import exercise.entity.Station;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface StationAnnotation {
    @Insert("INSERT INTO `station` " +
            "(`name`, `code`, `cargo`, `openday`, `distance`) " +
            "VALUES (#{name}, #{code}, #{cargo}, #{openday}, #{distance})")
    void addStation(Station station);
    @Delete("DELETE FROM `station` WHERE id = #{id}")
    void delStation(Integer id);
    @Update("UPDATE `station` " +
            "SET `name` = #{name}, `code` = #{code}, `cargo` = #{cargo}, `openday` = #{openday}, `distance` = #{distance} " +
            "WHERE `id` = #{id}")
    void updateStation(Station station);
    @Select("SELECT * FROM `station` WHERE `id` = #{id}")
    Station getStationById(Integer id);
    @Select("SELECT * FROM `station`")
    List<Station> findAllStations();
}
