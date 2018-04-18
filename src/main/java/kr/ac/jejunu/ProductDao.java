package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class ProductDao {
    private final JdbcContext jdbcContext;

    public ProductDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public Product get(Long id) throws SQLException {
        StatementStrategy statementStrategy = new GetProductStatementStrategy(id);
        return jdbcContext.jdbcContextForGet(statementStrategy);
    }

    public long insert(Product product) throws SQLException {
        StatementStrategy statementStrategy = new InsertProductStatementStrategy(product);
        return jdbcContext.jdbcContextForInsert(statementStrategy);
    }

    public void update(Product product) throws SQLException {
        StatementStrategy statementStrategy = new UpdateProductStatementStrategy(product);
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }

    public void delete(Long id) throws SQLException {
        StatementStrategy statementStrategy = new DeleteProductStatementStrategy(id);
        jdbcContext.jdbcContextForUpdate(statementStrategy);
    }
}
