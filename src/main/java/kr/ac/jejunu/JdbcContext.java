package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    Product jdbcContextForGet(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
            //resultset
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) { // get에는 empty면 null로 반환해 주는 조건이 없기 때문에
                //product mapping
                product = new Product();
                product.setId(resultSet.getLong("id"));
                product.setTitle(resultSet.getString("title"));
                product.setPrice(resultSet.getInt("price"));
            }
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

    long jdbcContextForInsert(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Long id;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();
            //resultset
            resultSet = preparedStatement.getGeneratedKeys();// last_index 대신 이걸로
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

    void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            //preparedStatement
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();

        } finally {
            //close
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
    }

    void update(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i =0; i < params.length; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };
        jdbcContextForUpdate(statementStrategy);
    }

    long insert(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
                                //preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            for(int i =0; i < params.length; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextForInsert(statementStrategy);
    }

    Product queryForObject(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {//메소드를 하나 implement하는 경우에 대해서의 람다표현식 콜백패턴
            //preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for(int i=0; i<params.length; i++){
                preparedStatement.setObject(i+1, params[i]);
            }
                return preparedStatement;
        };
        return jdbcContextForGet(statementStrategy);
    }
}