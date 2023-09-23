import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.Station;
import org.junit.Before;
import org.junit.Test;

import org.exercise.mapper.StationMapper;
import org.exercise.util.MyBatisUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StationMapperTest {
    private SqlSession sqlSession;
    private StationMapper stationMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        stationMapper = sqlSession.getMapper(StationMapper.class);

    }

    @Test
    public void findStationByCode() {
        List<Station> stations = stationMapper.findStationByCode(17912);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByCodeAndDistance() {
        Station condition = new Station();
        condition.setCode(17912);
        condition.setDistance(300);
        List<Station> stations = stationMapper.findStationByCodeAndDistance(condition);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByMultiConditions() {
        Map<String, Object> map= new HashMap<>();
        map.put("distance",null);
        List<Station> stations = stationMapper.findStationByMultiConditions(map);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByCode_foreach() {
        Map<String, Object> map= new HashMap<>();
        map.put("codes", Arrays.asList(17960, 17912, 18089));
        List<Station> stations = stationMapper.findStationByCode_foreach(map);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByName_trim() {
        Map<String, Object> map= new HashMap<>();
        map.put("name", "高密");
        List<Station> stations = stationMapper.findStationByName_trim(map);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void updateStation() {
        Map<String, Object> map= new HashMap<>();
        map.put("name", "济南");
        map.put("code", "16295");
        map.put("openday", "1912-11-01");
        map.put("distance", "393.588");
        map.put("id",11);
        stationMapper.updateStation(map);
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
    }
}