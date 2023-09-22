package exercise.mainbatis.sqlsession;

import exercise.mainbatis.config.Function;
import exercise.mainbatis.config.MapperBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

public class MapperProxy implements InvocationHandler {
    private SQLSession sqlSession;
    private String mapperFile;
    private Configuration configuration;

    public MapperProxy(SQLSession sqlSession, Class clazz, Configuration configuration) {
        this.sqlSession = sqlSession;
        this.mapperFile = clazz.getSimpleName() + ".xml";
        this.configuration = configuration;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MapperBean mapperBean = configuration.readMapper(this.mapperFile);
        //判断当前传入的方法，是否在xml文件进行指定
        if (!method.getDeclaringClass().getName().equals(mapperBean.getInterfaceName())){
            return null;
        }

        List<Function> functions = mapperBean.getFunctions();
        if (null != functions && 0!= functions.size()) {
            for (Function function : functions) {
                if (method.getName().equals(function.getFuncName())) {
                    //此处做了简化：对于SELECT语句只查询单条结果
                    //原生框架中还需要进行参数解析、拼接字符串、处理返回类型
                    if ("SELECT".equalsIgnoreCase(function.getSqlType())) {
                        return sqlSession.selectOne(function.getSql(), String.valueOf(args[0]));
                    }
                }
            }
        }
        return null;
    }
}
