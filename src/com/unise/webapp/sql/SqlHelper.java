package com.unise.webapp.sql;

import com.unise.webapp.exception.ExistStorageException;
import com.unise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public interface SqlExecutor<T> {
        T execute(PreparedStatement ps) throws SQLException;
    }

    public void execute(String sql) {
        execute(sql, PreparedStatement::execute);
    }

    public interface SqlTransaction<T> {
        T execute(Connection ps) throws SQLException;
    }

    public <T> T execute(String sql, SqlExecutor<T> executor) throws ExistStorageException {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executor.execute(ps);
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException("Дублирование uuid в базе");
            }
            throw new StorageException(e);
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> executor) throws ExistStorageException {
        try (Connection conn = connectionFactory.getConnection()) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(conn);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw SqlException.convertException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
