import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.Station;
import org.exercise.mapper.StationMapper;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
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
    public void findStationByNameOrId() {
        //查询 id=1 或者 name="高密" 的车站
        Station condition = new Station();
        condition.setId(1);
        condition.setName("高密");
        List<Station> stations = stationMapper.findStationByNameOrId(condition);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByCode() {
        //查询 code 中包含 "18" 的车站
        List<Station> stations = stationMapper.findStationByCode(18);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByIdAndDistance_ParamMap() {
        //查询 code > 16295 并且 distance > 100 的所有车站
        Map<String, Object> condition = new HashMap<>();
        condition.put("code",16295);
        condition.put("distance",100);
        List<Station> stations = stationMapper.findStationByIdAndDistance_ParamMap(condition);
        for (Station station : stations) {
            System.out.println(station);
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void findStationByIdAndDistance_ReturnMap() {
        //查询 code > 16295 并且 distance > 100 的所有车站
        Map<String, Object> condition = new HashMap<>();
        condition.put("code",16295);
        condition.put("distance",100);
        List<Map<String, Object>> stationList = stationMapper.findStationByIdAndDistance_ReturnMap(condition);
        for (Map<String, Object> stationMap : stationList) {
            System.out.print("返回的结果：");
            for (Map.Entry<String, Object> entry : stationMap.entrySet()) {
                System.out.print(entry.getKey() + "⇒" + entry.getValue() + "\t");
            }
            System.out.println();
        }

        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
