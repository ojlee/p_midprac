package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetProductStatementStrategy implements StatementStrategy {
    private Long id;
    public GetProductStatementStrategy(Long id) {
        this.id = id;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
                //preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM product WHERE id = ?");
        preparedStatement.setLong(1,id);
        return preparedStatement;
    }
}
