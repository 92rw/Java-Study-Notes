package exercise.mainbatis.sqlsession;

public class SessionFactory {
    public static SQLSession getSession() {
        return new SQLSession();
    }
}
