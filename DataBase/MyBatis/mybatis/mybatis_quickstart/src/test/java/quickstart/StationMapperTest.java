package quickstart;

import exercise.entity.Station;
import exercise.mapper.StationMapper;
import exercise.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class StationMapperTest {
    private SqlSession sqlSession;
    private StationMapper stationMapper;

    //编写方法完成初始化
    //@Before注解表示在执行目标测试方法前，会先执行该方法
    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        //底层使用动态代理，获取到 com.sun.proxy.$Proxy7 对象
        stationMapper = sqlSession.getMapper(StationMapper.class);
    }

    @Test
    public void addStation(){
        Station qdk = new Station();
        qdk.setName("青岛");
        qdk.setCode(18089);
        qdk.setCargo(1);
        qdk.setOpenday(new Date(1, 3, 8));
        qdk.setDistance(0.63);
        stationMapper.addStation(qdk);
        System.out.println("添加车站" + qdk);
        System.out.println("在数据库中的id值" + qdk.getId());

        //如果是增删改，需要提交事务
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("添加成功");
    }

    @Test
    public void delStation(){
        stationMapper.delStation(2);
        //如果是增删改，需要提交事务
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("删除成功");
    }

    @Test
    public void updateStation() {
        Station qzn = new Station();
        qzn.setName("青州市南");
        qzn.setCode(17882);
        qzn.setCargo(1);
        qzn.setOpenday(new Date(3, 3, 12));
        qzn.setDistance(235.966);

        qzn.setId(3);//用于替换既有项
        stationMapper.updateStation(qzn);

        //如果是增删改，需要提交事务
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
        System.out.println("修改成功");
    }

    @Test
    public void getStationById() {
        Station stationById = stationMapper.getStationById(3);
        System.out.println(stationById);

        if (sqlSession != null) {
            sqlSession.close();
        }
        System.out.println("查询完成");
    }

    @Test
    public void findAllStations() {
        List<Station> allStations = stationMapper.findAllStations();
        for (Station station : allStations) {
            System.out.println(station);
        }
        if (sqlSession != null) {
            sqlSession.close();
        }
        System.out.println("查询完成");
    }
}
