package kr.ac.jejunu;

import java.sql.*;

public class ProductDao {
    private final JdbcContext jdbcContext;

    public ProductDao(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public Product get(Long id) throws SQLException {
        String sql = "SELECT * FROM product WHERE id = ?";
        Object[] params = new Object[]{id};
        return jdbcContext.queryForObject(sql, params);
    }

    public long insert(Product product) throws SQLException {
        String sql = "INSERT INTO product(title, price) VALUES (?,?)";
        Object[] params = new Object[]{product.getTitle(), product.getPrice()};
        return jdbcContext.insert(sql, params);
    }

    public void update(Product product) throws SQLException {
        String sql = "UPDATE product SET title = ?, price = ? where id = ?";
        Object[] params = new Object[] {product.getTitle(), product.getPrice(), product.getId()};
        jdbcContext.update(sql, params);
    }

    public void delete(Long id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        Object[] params = new Object[]{id};
        jdbcContext.update(sql, params);
    }

}
