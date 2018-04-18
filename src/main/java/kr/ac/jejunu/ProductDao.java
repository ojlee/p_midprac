package kr.ac.jejunu;

import java.sql.*;

public class ProductDao {
    private final JdbcContext jdbcContext;

    public ProductDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public Product get(Long id) throws SQLException {
        StatementStrategy statementStrategy = connection -> {//메소드를 하나 implement하는 경우에 대해서의 람다표현식 콜백패턴
            //preparedStatement
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM product WHERE id = ?");
                preparedStatement.setLong(1,id);
                return preparedStatement;
        };
        return jdbcContext.jdbcContextForGet(statementStrategy);
    }

    public long insert(Product product) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
                                //preparedStatement
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO product(title, price) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, product.getTitle());
                preparedStatement.setInt(2, product.getPrice());
            return preparedStatement;
        };
        return jdbcContext.jdbcContextForInsert(statementStrategy);
    }

    public void update(Product product) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
                PreparedStatement preparedStatement = connection.prepareStatement(
            "UPDATE product SET title = ?, price = ? where id = ?");
                preparedStatement.setString(1, product.getTitle());
                preparedStatement.setInt(2, product.getPrice());
                preparedStatement.setLong(3, product.getId());
            return preparedStatement;
        };
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }

    public void delete(Long id) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            //preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE  FROM  product WHERE id = ?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        };
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }
}
