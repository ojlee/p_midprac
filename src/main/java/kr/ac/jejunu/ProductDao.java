package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class ProductDao {
    private final DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Product get(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = dataSource.getConnection();

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

    public long insert(Product product) throws SQLException, ClassNotFoundException {
        Connection connection = dataSource.getConnection();

        //preparedStatement
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO product(title, price) VALUES (?,?)");
        preparedStatement.setString(1, product.getTitle());
        preparedStatement.setInt(2, product.getPrice());

        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement("SELECT last_insert_id()");

        //resultset
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();

        //id mapping
        Long id = resultSet.getLong(1);
        //close
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //return
        return id;
    }
}
