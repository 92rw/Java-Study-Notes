import exercise.eneity.Station;
import exercise.mainbatis.config.MapperBean;
import exercise.mainbatis.sqlsession.*;
import exercise.mapper.StationMapper;
import org.junit.Test;

import java.sql.Connection;

public class MainBatisTest {
    @Test
    public void build() {
        Configuration configuration = new Configuration();
        Connection connection = configuration.build("MainBatisConfig.xml");
        System.out.println(connection);
    }

    @Test
    public void query() {
        Executor executor = new MyExecutor();
        Station query = executor.query("SELECT * FROM `station` WHERE id = ?", 1);
        System.out.println("执行器 MyExecutor 查询结果：" + query);
    }

    @Test
    public void selectOne() {
        SQLSession sqlSession = new SQLSession();
        Station selected = sqlSession.selectOne("SELECT * FROM `station` WHERE id = ?", 1);
        System.out.println("SqlSession 查询结果：" + selected);
    }

    @Test
    public void readMapper() {
        Configuration configuration = new Configuration();
        MapperBean mapperBean = configuration.readMapper("StationMapper.xml");
        System.out.println(mapperBean);
    }

    @Test
    public void getMapper() {
        SQLSession sqlSession = SessionFactory.getSession();
        StationMapper mapper = sqlSession.getMapper(StationMapper.class);
        System.out.println(mapper.getClass());
        Station stationById = mapper.getStationById(1);
        System.out.println(stationById);
    }
}
