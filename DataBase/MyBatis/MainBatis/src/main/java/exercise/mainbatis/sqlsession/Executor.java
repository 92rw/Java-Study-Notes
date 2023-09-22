package exercise.mainbatis.sqlsession;

public interface Executor {
    <T>T query(String sql, Object parameter);
}
