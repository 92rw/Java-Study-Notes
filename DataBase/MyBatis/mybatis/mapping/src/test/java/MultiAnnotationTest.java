import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.RailStation;
import org.exercise.entity.Railway;
import org.exercise.mapper.RailStationMapper;
import org.exercise.mapper.RailStationMapperAnnotation;
import org.exercise.mapper.RailwayMapper;
import org.exercise.mapper.RailwayMapperAnnotation;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class MultiAnnotationTest {
    private SqlSession sqlSession;
    private RailwayMapperAnnotation railwayMapperAnnotation;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        railwayMapperAnnotation = sqlSession.getMapper(RailwayMapperAnnotation.class);
    }

    @Test
    public void getRailwayByName() {
        Railway railway = railwayMapperAnnotation.getRailwayByName("胶济客专");
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
        RailStationMapperAnnotation railStationMapper = sqlSession.getMapper(RailStationMapperAnnotation.class);
        RailStation cya = railStationMapper.getStationByName("城阳");
        System.out.println("车站名：" + cya.getName() + "，所在线路：" + cya.getLine().getName());

        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
