package projectTemplate.test.base;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import projectinsight.module.app.AppGuiceModule;
import projectinsight.module.app.commons.AppRuntimeEnvironment;
import projectinsight.module.app.commons.ConnectionProxy;
import projectinsight.module.app.commons.RequestContextData;

public abstract class BaseUnitTest extends BaseTest {

	private List<Class<? extends AbstractModule>> testModules;
	private Injector injector;

	public BaseUnitTest(List<Class<? extends AbstractModule>> testModules) {

		super();
		this.testModules = testModules;
	}


	@Before
	public void loadModules() {

		Map<Class<? extends AbstractModule>, Class<? extends AbstractModule>> testModulesToReplace = new HashMap<Class<? extends AbstractModule>, Class<? extends AbstractModule>>();
//		testModulesToReplace.put(CoreGuiceModule.class, TestCoreGuiceModule.class);

    AppGuiceModule appModule = new AppGuiceModule(this.testModules, testModulesToReplace);
    appModule.setRuntimeEnviroment(AppRuntimeEnvironment.JUNIT_TEST);
    appModule.setConnectionPoolDataSource(this.datasource);

		this.injector = Guice.createInjector(appModule);

	}

	@After
	public void closeOpenedConnections() throws SQLException {

		ConnectionProxy connectionProxy = injector.getInstance(RequestContextData.class).getCurrentThreadConnectionProxy();

		if (connectionProxy != null) {
			connectionProxy.getTargetConnection().close();
		}

	}

}
