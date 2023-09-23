package quickstart;

import exercise.entity.Station;
import exercise.mapper.StationAnnotation;
import exercise.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class StationAnnotationTest {
    private SqlSession sqlSession;
    private StationAnnotation stationAnnotation;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        //底层使用动态代理，获取到 com.sun.proxy.$Proxy11 对象
        stationAnnotation = sqlSession.getMapper(StationAnnotation.class);
    }

    @Test
    public void addStation(){
        Station gmi = new Station();
        gmi.setName("高密");
        gmi.setCode(17960);
        gmi.setCargo(1);
        gmi.setOpenday(new Date(1, 8, 8));
        gmi.setDistance(97.715);
        stationAnnotation.addStation(gmi);
        System.out.println("添加车站" + gmi);
        System.out.println("在数据库中的id值" + gmi.getId());

        //如果是增删改，需要提交事务
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("添加成功");
    }

    @Test
    public void findAllStations() {
        List<Station> allStations = stationAnnotation.findAllStations();
        for (Station station : allStations) {
            System.out.println(station);
        }
        if (sqlSession != null) {
            sqlSession.close();
        }
        System.out.println("查询完成");
    }
}
