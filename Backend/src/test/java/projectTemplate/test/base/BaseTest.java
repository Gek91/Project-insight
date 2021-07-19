package projectTemplate.test.base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.sql.DataSource;

public abstract class BaseTest {

	public static final String H2_JDBC_CONNECTION_URL = "jdbc:h2:mem:junitmemdb";
	public static final String H2_JDBC_CONNECTION_USER = "SA";
	public static final String H2_JDBC_CONNECTION_PASSWORD = "SA";
	//Test db scprit file
	public static final List<String> H2_DBMS_DUMP_INIT_SCRIPTS =
			Arrays.asList("classpath:dbms/devmemdb-schema.sql", "classpath:dbms/junitmemdb-data.sql");


	//connection for h2 in memory database
	//private Connection dbmsConnection;
  protected static DataSource datasource;

	/*
	 * Start h2 memory database
	 */
	@BeforeClass
	public static void loadRdbmsJbdcDriver() {
		//org.h2.Driver.load();
		//org.h2.engine.Mode.getInstance("MYSQL").convertInsertNullToZero = false;

    JdbcDataSource ds = new JdbcDataSource();
    ds.setURL(H2_JDBC_CONNECTION_URL);
    ds.setUser(H2_JDBC_CONNECTION_USER);
    ds.setPassword(H2_JDBC_CONNECTION_PASSWORD);

    datasource = ds;
	}


	/*
	 * Create h2 dbms connection and popluate it
	 */
	@Before
	public void setupInMemoryH2Dbms() throws SQLException, FileNotFoundException {
    InputStream stream = getClass().getClassLoader().getResourceAsStream("dbms/devmemdb-schema.sql");
    RunScript.execute(datasource.getConnection(), new InputStreamReader(stream));
  /*
		StringBuffer jdbcConnectionUrl = new StringBuffer(H2_JDBC_CONNECTION_URL + ";MODE=MYSQL;INIT=");

		//empty in memory db
		jdbcConnectionUrl.append("DROP ALL OBJECTS\\;");

		//add run scripts command for create schema and populate with test data
		for (String initScript : H2_DBMS_DUMP_INIT_SCRIPTS) {
			jdbcConnectionUrl.append("runscript from '" + initScript + "'\\;");
		}

		//create db connection
		dbmsConnection = DriverManager.getConnection(
			jdbcConnectionUrl.toString(),
			H2_JDBC_CONNECTION_USER,
			H2_JDBC_CONNECTION_PASSWORD
		);
*/
	}

	/*
	 * close h2 dbms connection
	 */
	@After
	public void destroyInMemoryH2Dbms() throws SQLException {
		/*dbmsConnection.close();*/
	}
}
