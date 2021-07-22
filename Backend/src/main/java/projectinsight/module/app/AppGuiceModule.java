package projectinsight.module.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.google.inject.AbstractModule;

import projectinsight.module.app.commons.RuntimeEnvironment;
import projectinsight.module.app.commons.AppRuntimeEnvironment;
import projectinsight.module.app.commons.provider.ExceptionMapperProvider;
import projectinsight.module.app.commons.uow.UnitOfWork;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.app.service.PropertiesService;
import projectinsight.module.app.service.impl.PersistenceServiceImpl;
import projectinsight.module.app.service.impl.PropertiesServiceImpl;

public class AppGuiceModule extends AbstractModule {

	private List<Class<? extends AbstractModule>> modulesToLoad;
	private AppRuntimeEnvironment runtimeEnvironment;
	private DataSource connectionPoolDataSource;

	public AppGuiceModule(List<Class<? extends AbstractModule>> inputModule, Map<Class<? extends AbstractModule>, Class<? extends AbstractModule>> modulesToReplace) {

		this.modulesToLoad = new ArrayList<>();

		for(Class<? extends AbstractModule> module : inputModule) {
			checkModuleReplacingInAdd(this.modulesToLoad, module, modulesToReplace);
		}

	}

	@Override
	protected void configure() {
    //bind runtimeEnviroment value, required to inject it in configuration service
		bind(RuntimeEnvironment.class).toInstance(runtimeEnvironment);
		bind(DataSource.class).toInstance(connectionPoolDataSource);
		bind(PersistenceService.class).to(PersistenceServiceImpl.class).asEagerSingleton();
		bind(PropertiesService.class).to(PropertiesServiceImpl.class).asEagerSingleton();
		bind(ExceptionMapperProvider.class);

    //Datasource
//    bind(DataSource.class).to(PersistenceService.class);

    //bind request context data to request scope -> limit its lifetime to a request
    //This will be injected using a Provider (Provider<RequestContextData> obj)
//		bind(RequestContextData.class).in(RequestScoped.class);

    //Install module
		for(Class<? extends AbstractModule> moduleClass : modulesToLoad) {
			try {
				install(moduleClass.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}

	}

	public void setRuntimeEnviroment(AppRuntimeEnvironment appRuntimeEnvironment) {
		this.runtimeEnvironment = appRuntimeEnvironment;
	}

	public void setConnectionPoolDataSource(DataSource datasource) {
	  this.connectionPoolDataSource = datasource;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void checkModuleReplacingInAdd(List<Class<? extends AbstractModule>> modulesToLoad, Class<? extends AbstractModule> module, Map<Class<? extends AbstractModule>, Class<? extends AbstractModule>> modulesToReplace) {

		if(modulesToReplace != null && modulesToReplace.containsKey(module)) {
			modulesToLoad.add(modulesToReplace.get(module));
		} else {
			modulesToLoad.add(module);
		}
	}

}
