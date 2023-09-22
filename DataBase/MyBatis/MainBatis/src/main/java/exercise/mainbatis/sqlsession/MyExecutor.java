package exercise.mainbatis.sqlsession;

import exercise.eneity.Station;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyExecutor implements Executor{
    private Configuration configuration = new Configuration();

    @Override
    public <T> T query(String sql, Object parameter) {
        Connection connection = getConnection();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            //设置参数：如果参数多，可以使用参数数组处理，此处做了简化
            preparedStatement.setString(1,parameter.toString());
            resultSet = preparedStatement.executeQuery();

            //将结果集中的数据封装到JavaBean对象
            //完善的写法是使用反射机制，此处做了简化
            Station station = new Station();
            while (resultSet.next()) {
                station.setId(resultSet.getInt("id"));
                station.setName(resultSet.getString("name"));
                station.setCode(resultSet.getInt("code"));
                station.setCargo(resultSet.getInt("cargo"));
                station.setOpenday(resultSet.getDate("openday"));
                station.setDistance(resultSet.getDouble("distance"));
            }
            return (T) station;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    //通过Configuration对象返回连接
    private Connection getConnection() {
        Connection connection = configuration.build("MainBatisConfig.xml");
        return connection;
    }
}
