package projectinsight.module.app.commons;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import projectinsight.module.app.AppGuiceModule;
import projectinsight.module.project.ProjectGuiceModule;

public class ApplicationModulesLoaderContextListener extends GuiceResteasyBootstrapServletContextListener {

	//web xml parameter that keep runtime enviroment value
	public static final String RUNTIME_ENV_CONTEXT_PARAM = "application.project.runtime.environment";

	private AppGuiceModule appModule;

	public ApplicationModulesLoaderContextListener() {

		//create base module with application module
		appModule = new AppGuiceModule(createModuleToLoadList(), null);
	}

	public ApplicationModulesLoaderContextListener(Map<Class<? extends AbstractModule>, Class<? extends AbstractModule>> modulesToReplace) {

		//create base module with application module and replace passed module
		appModule = new AppGuiceModule(createModuleToLoadList(), modulesToReplace);
	}

	@Override
	protected List<? extends Module> getModules(ServletContext context) {

		//retrieve runtime environment
		AppRuntimeEnvironment appRuntimeEnvironment = getAppRuntimeEnviroment(context);

		//set runtime enviroment value in appModule
		appModule.setRuntimeEnviroment(appRuntimeEnvironment);

		DataSource dataSource = createH2ConnectionPool();
		appModule.setConnectionPoolDataSource(dataSource);


		DataSource pool = (DataSource) context.getAttribute("connection-pool");
		if (pool == null) {
		    context.setAttribute("connection-pool", dataSource);
		}

		return Collections.singletonList(appModule);
	}

	///////////////////////////////////////////

	private AppRuntimeEnvironment getAppRuntimeEnviroment(ServletContext context) {

		String servletContextRuntimeEnvParam = context.getInitParameter(RUNTIME_ENV_CONTEXT_PARAM);

		AppRuntimeEnvironment appRuntimeEnvironment = AppRuntimeEnvironment.getValueById(servletContextRuntimeEnvParam);

		if (appRuntimeEnvironment == null) {
//			throw new IllegalArgumentException("Invalid '" + RUNTIME_ENV_CONTEXT_PARAM + "' context param value (passed '" + servletContextRuntimeEnvParam + "').");
			appRuntimeEnvironment = AppRuntimeEnvironment.DEVELOPMENT;
		}

		return appRuntimeEnvironment;
	}

	private List<Class<? extends AbstractModule>> createModuleToLoadList() {

		List<Class<? extends AbstractModule>> modulesToLoad = new ArrayList<>();

		modulesToLoad.add(ProjectGuiceModule.class);

		return modulesToLoad;
	}

	////////////////////////////////////////////

  private DataSource createH2ConnectionPool() {

    try {
      JdbcDataSource datasource = new JdbcDataSource();

      datasource.setURL("jdbc:h2:mem:junitmemdb;MODE=MYSQL;");
      datasource.setUser("SA");
      datasource.setPassword("SA");

      InputStream stream = getClass().getClassLoader().getResourceAsStream("dbms/devmemdb-schema.sql");
      RunScript.execute(datasource.getConnection(), new InputStreamReader(stream));
      stream.close();

      stream = getClass().getClassLoader().getResourceAsStream("dbms/devmemdb-data.sql");
      RunScript.execute(datasource.getConnection(), new InputStreamReader(stream));
      stream.close();

      return datasource;

    } catch(Exception e) {
	    throw new RuntimeException(e);
    }
  }

	private DataSource createConnectionPool() {

	    HikariConfig config = new HikariConfig();

	    config.setJdbcUrl(String.format("jdbc:mysql:///%s", "schema"));
	    config.setUsername(""); // e.g. "root", "mysql"
	    config.setPassword(""); // e.g. "my-password"

	    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
	    config.addDataSourceProperty("cloudSqlInstance", "project:europe-west1:instance");
//	    config.setThreadFactory(ThreadManager.backgroundThreadFactory())

	    // The ipTypes argument can be used to specify a comma delimited list of preferred IP types
	    // for connecting to a Cloud SQL instance. The argument ipTypes=PRIVATE will force the
	    // SocketFactory to connect with an instance's associated private IP.
	    config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE");
	    // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
	    // values for this setting are highly variable on app design, infrastructure, and database.
	    config.setMaximumPoolSize(5);
	    // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
	    // Additional connections will be established to meet this value unless the pool is full.
	    config.setMinimumIdle(5);

	    // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
	    // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
	    // SQLException.
	    config.setConnectionTimeout(10000); // 10 seconds
	    // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
	    // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
	    config.setIdleTimeout(600000); // 10 minutes
	    // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
	    // live longer than this many milliseconds will be closed and reestablished between uses. This
	    // value should be several minutes shorter than the database's timeout value to avoid unexpected
	    // terminations.
	    config.setMaxLifetime(1800000); // 30 minutes


	    DataSource pool = new HikariDataSource(config);

	    return pool;
	}


	// This function is called when the Servlet is destroyed
	@Override
	public void contextDestroyed(ServletContextEvent event) {

		HikariDataSource pool = (HikariDataSource) event.getServletContext().getAttribute("connection-pool");

		if (pool != null) {
		  pool.close();
		}

		super.contextDestroyed(event);
	}

//	// This function is called when the application starts and will safely create a connection pool
//    // that can be used to connect to.
//	@Override
//	public void contextInitialized(ServletContextEvent event) {
//
//		super.contextInitialized(event);
//
//
//	}
}
