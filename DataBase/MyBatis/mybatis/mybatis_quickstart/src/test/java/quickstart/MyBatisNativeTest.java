package quickstart;

import exercise.entity.Station;
import exercise.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class MyBatisNativeTest {
    private SqlSession sqlSession;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
    }

    @Test
    public void myBatisNativeCrud() {
        //添加
        Station zcu = new Station();
        zcu.setName("周村");
        zcu.setCode(17771);
        zcu.setCargo(1);
        zcu.setOpenday(new Date(3,8,22));
        zcu.setDistance(298.705);

        int insert = sqlSession.insert("exercise.mapper.StationMapper.addStation", zcu);
        System.out.println("添加完成，数据库修改条数" + insert);

        //删除
        int delete = sqlSession.delete("exercise.mapper.StationMapper.delStation", 4);
        System.out.println("删除成功，数据库修改条数" + delete);

        //修改
        Station wfa = new Station();
        wfa.setName("潍坊");
        wfa.setCode(17912);
        wfa.setCargo(1);
        wfa.setOpenday(new Date(2,5,1));
        wfa.setDistance(179.443);
        wfa.setId(3);//如果不指定id，MySQL无法找到修改哪一个
        int update = sqlSession.update("exercise.mapper.StationMapper.updateStation", wfa);
        System.out.println("修改成功，数据库修改条数" + update);

        //查询（所有记录）：查询语句下方可以不commit
        List<Station> stations = sqlSession.selectList("exercise.mapper.StationMapper.findAllStations");
        for (Station station : stations) {
            System.out.println(station);
        }

        //如果是增删改，需要提交事务
        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
    }
}
