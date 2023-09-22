package exercise.mainbatis.sqlsession;

import java.lang.reflect.Proxy;

public class SQLSession {
    private Executor executor = new MyExecutor();
    private Configuration configuration = new Configuration();

    //原生MyBatis在此处实现了很多方法，此处仅列举一个做代码样例，且做了简化
    public <T> T selectOne(String statement, Object parameter) {
        return executor.query(statement, parameter);
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MapperProxy(this, clazz, configuration));
    }
}
