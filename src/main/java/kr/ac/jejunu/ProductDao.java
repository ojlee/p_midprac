package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class ProductDao {
    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Product get(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product;
        try {
            connection = dataSource.getConnection();
            //preparedStatement
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM product WHERE id = ?");
            preparedStatement.setLong(1,id);
            //resultset
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            //product mapping
            product = new Product();
            product.setId(resultSet.getLong("id"));
            product.setTitle(resultSet.getString("title"));
            product.setPrice(resultSet.getInt("price"));
        } finally {
            //close
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }//하나만 close 되도 나머지 다 close 됨
        }
        //return
        return product;
    }

    public long insert(Product product) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id;
        try {
            connection = dataSource.getConnection();
            //preparedStatement
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO product(title, price) VALUES (?,?)");
            preparedStatement.setString(1, product.getTitle());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement("SELECT last_insert_id()");
            //resultset
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            //id mapping
            id = resultSet.getLong(1);
        } finally {
            //close
            if (resultSet != null)
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (preparedStatement != null)
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }

        //return
        return id;
    }
}
