package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JejuProductDao extends ProductDao {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //connection
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost/midprac", "ojlee", "123456");
    }
}
