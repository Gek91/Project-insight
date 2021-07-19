package projectTemplate.module.app.service.impl;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.google.inject.Inject;

import projectTemplate.module.app.service.PersistenceService;
import projectTemplate.module.app.service.PropertiesService;

public class PersistenceServiceImpl implements PersistenceService {

	private PropertiesService configService;
	private DataSource datasource;

	@Inject
	protected void initService(PropertiesService configService, DataSource datasource) throws SQLException, ClassNotFoundException {

		this.configService = configService;
		this.datasource = datasource;

//		loadRdbmsJbdcDriver(configService.getAppRuntimeEnvironment());
	}


	/*
	 * Load jdbc driver based on runtime enviroment
	 */
//	protected void loadRdbmsJbdcDriver(AppRuntimeEnvironment appRuntimeEnvironment) throws SQLException, ClassNotFoundException {
//
//		switch (appRuntimeEnvironment) {
//
//			case DEVELOPMENT:
//
//				//use h2 in local development
//				org.h2.Driver.load();
//				org.h2.engine.Mode.getInstance("MYSQL").convertInsertNullToZero = false;
//
//				break;
//
//			case TEST:
//
//				Class.forName("com.mysql.jdbc.GoogleDriver");
//
//				break;
//
//			case CERT:
//
//				Class.forName("com.mysql.jdbc.GoogleDriver");
//
//				break;
//
//			case PRODUCTION:
//
//				Class.forName("com.mysql.jdbc.GoogleDriver");
//
//				break;
//
//			default:
//				throw new UnsupportedOperationException();
//
//		}
//
//	}

	@Override
	public Connection getConnection() throws SQLException {

		return datasource.getConnection();

//		DataSource pool = (DataSource) req.getServletContext().getAttribute("connection-pool")
//		//Take connection from current thread
//		ConnectionProxy connectionProxy = requestContextData.getCurrentThreadConnectionProxy();
//
//		if (connectionProxy == null) {
//
//			Connection jdbcConnection = null;
//
//			if (StringUtils.isNotEmpty(configService.getRdbmsJdbcConnectionUser())) {
//				jdbcConnection = DriverManager.getConnection(
//					configService.getRdbmsJdbcConnectionURL(),
//					configService.getRdbmsJdbcConnectionUser(),
//					configService.getRdbmsJdbcConnectionPassword()
//				);
//			} else {
//				//create new connection
//				jdbcConnection = DriverManager.getConnection(configService.getRdbmsJdbcConnectionURL());
//			}
//
//			//disable close connection call on connection
//			connectionProxy = getCloseSuppressingConnectionProxy(jdbcConnection);
//			requestContextData.setCurrentThreadConnectionProxy(connectionProxy);
//		}
//
//		return connectionProxy;
	}

	@Override
	public void beginJdbcTransaction() throws SQLException {

		Connection connection = getConnection();

		connection.setAutoCommit(false);
		connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

	}

	@Override
	public void commitJdbcTransaction() throws SQLException {

		Connection connection = getConnection();

		try {
			connection.commit();
		} finally {
			connection.setAutoCommit(true);
		}

	}

	@Override
	public void rollbackJdbcTransaction() throws SQLException {

		Connection connection = getConnection();

		try {
			connection.rollback();
		} finally {
			connection.setAutoCommit(true);
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////
	//DATASTORE METHODS

	@Override
	public Connection getConnection(String arg0, String arg1) throws SQLException {
		throw new SQLException("Custom username and password not supported");
	}


	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new UnsupportedOperationException("getLogWriter");
	}


	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}


	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException();
	}


	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		throw new UnsupportedOperationException("setLogWriter");
	}


	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout");
	}


	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return iface.isInstance(this);
	}


	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {

		if (iface.isInstance(this)) {
			return (T) this;
		}

		throw new SQLException("DataSource of type [" + getClass().getName() + "] cannot be unwrapped as [" + iface.getName() + "]");
	}

}
