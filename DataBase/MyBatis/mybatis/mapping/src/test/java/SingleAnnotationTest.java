import org.apache.ibatis.session.SqlSession;
import org.exercise.entity.StaCode;
import org.exercise.entity.StaName;
import org.exercise.mapper.StaCodeMapperAnnotation;
import org.exercise.mapper.StaNameMapperAnnotation;
import org.exercise.util.MyBatisUtils;
import org.junit.Before;
import org.junit.Test;

public class SingleAnnotationTest {
    private SqlSession sqlSession;
    private StaCodeMapperAnnotation staCodeMapper;
    private StaNameMapperAnnotation staNameMapper;

    @Before
    public void init() {
        sqlSession = MyBatisUtils.getSqlSession();
        staCodeMapper = sqlSession.getMapper(StaCodeMapperAnnotation.class);
        staNameMapper = sqlSession.getMapper(StaNameMapperAnnotation.class);
    }

    @Test
    public void getCodeObjectByCode() {
        StaCode stacode = staCodeMapper.getObjectByCode(18089);
        System.out.println(stacode);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

    @Test
    public void getNameObjectByCode() {
        StaName staname = staNameMapper.getObjectByCode(18089);
        System.out.println(staname);
        if (sqlSession != null) {
            sqlSession.close();
        }
    }

}
