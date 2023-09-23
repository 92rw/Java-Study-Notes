import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.RailStation;
import org.exercise.entity.Railway;
import org.exercise.mapper.RailStationMapper;
import org.exercise.mapper.RailwayMapper;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MultiMappingTest {
    private SqlSession sqlSession;
    private RailwayMapper railwayMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        railwayMapper = sqlSession.getMapper(RailwayMapper.class);
    }

    @Test
    public void getRailwayByName() {
        Railway railway = railwayMapper.getRailwayByName("胶济客专");
        System.out.println(railway.getName() + " 线路下属车站：");

        List<RailStation> stations = railway.getStations();
        for (RailStation station : stations) {
            System.out.print(station.getName() + "\t");
        }
        System.out.println();

        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void getStationByName() {
        RailStationMapper railStationMapper = sqlSession.getMapper(RailStationMapper.class);
        RailStation qda = railStationMapper.getStationByName("青岛");
        System.out.println("车站名：" + qda.getName() + "，所在线路：" + qda.getLine().getName());

        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
