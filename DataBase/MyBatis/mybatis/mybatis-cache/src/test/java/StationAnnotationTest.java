import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.Station;
import org.exercise.mapper.StationAnnotation;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

public class StationAnnotationTest {
    private SqlSession sqlSession;
    private StationAnnotation stationAnnotation;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        stationAnnotation = sqlSession.getMapper(StationAnnotation.class);
    }

    @Test
    public void localCacheTest() {
        Station station1 = stationAnnotation.getStationById(1);
        System.out.println(station1);

        System.out.println("存在本地缓存的情况下，不会发出第二次请求");

        Station station2 = stationAnnotation.getStationById(1);
        System.out.println(station2);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
