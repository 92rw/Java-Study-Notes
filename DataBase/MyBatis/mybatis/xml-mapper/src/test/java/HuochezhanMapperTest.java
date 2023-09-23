import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.Huochezhan;
import org.exercise.mapper.HuochezhanMapper;
import org.exercise.mapper.StationMapper;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class HuochezhanMapperTest {
    private SqlSession sqlSession;
    private HuochezhanMapper huochezhanMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        huochezhanMapper = sqlSession.getMapper(HuochezhanMapper.class);
    }

    @Test
    public void addStation() {
        Huochezhan xsh = new Huochezhan();
        xsh.setZhanming("峡山");
        xsh.setDianbaoma(17953);
        xsh.setBanhuo(0);
        xsh.setYingyeriqi(new Date(1,11,1));
        xsh.setZhongxinlicheng(124.941);
        huochezhanMapper.addStation(xsh);
        System.out.println("车站添加成功，数据库编号：" + xsh.getId());

        if (sqlSession != null) {
            sqlSession.commit();
            sqlSession.close();
        }
    }

    @Test
    public void listAllStations() {
        List<Huochezhan> chezhanList = huochezhanMapper.listAllStations();
        for (Huochezhan huochezhan : chezhanList) {
            System.out.println(huochezhan);
        }
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}