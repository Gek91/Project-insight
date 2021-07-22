package projectinsight.module.app.service;

import java.sql.SQLException;

import javax.sql.DataSource;

public interface PersistenceService extends DataSource {

	public void beginJdbcTransaction() throws SQLException;

	public void commitJdbcTransaction() throws SQLException;

	public void rollbackJdbcTransaction() throws SQLException;
}
