package kr.ac.jejunu;

import java.sql.*;

public class ProductDao {
    public Product get(Long id) throws ClassNotFoundException, SQLException {
        //connection
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection =
                DriverManager.getConnection(
                        "jdbc:mysql://localhost/midprac", "ojlee", "123456");
        //preparedStatement
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "SELECT * FROM product WHERE id = ?");
        preparedStatement.setLong(1,id);
        //resultset
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        //product mapping
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setTitle(resultSet.getString("title"));
        product.setPrice(resultSet.getInt("price"));
        //close
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //return
        return product;
    }
}
