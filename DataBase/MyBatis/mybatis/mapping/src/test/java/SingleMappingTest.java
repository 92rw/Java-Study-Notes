import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.StaCode;
import org.exercise.entity.StaName;
import org.exercise.mapper.StaCodeMapper;
import org.exercise.mapper.StaNameMapper;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

public class SingleMappingTest {
    private SqlSession sqlSession;
    private StaCodeMapper staCodeMapper;
    private StaNameMapper staNameMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        staCodeMapper = sqlSession.getMapper(StaCodeMapper.class);
        staNameMapper = sqlSession.getMapper(StaNameMapper.class);
    }

    @Test
    public void staCodeMapperTest() {
        StaCode staCode = staCodeMapper.getObjectByCode(18089);
        System.out.println(staCode);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void staNameMapperTest() {
        StaName staname = staNameMapper.getObjectByCode(18089);
        System.out.println(staname);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void staNameMapperTest2() {
        StaName staname = staNameMapper.getObjectByCode2(18089);
        System.out.println(staname);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void staCodeReverse() {
        StaCode staCode = staCodeMapper.getReverseObject(18089);
        System.out.println(staCode);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }
}
