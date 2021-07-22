package projectTemplate.test.base.mockModule;

import com.google.inject.AbstractModule;

import projectinsight.module.app.service.PropertiesService;
import projectinsight.module.app.service.impl.PropertiesServiceImpl;

public class TestCoreGuiceModule extends AbstractModule {

	@Override
	protected void configure() {


		//Rest API

		//Services

		bind(PropertiesService.class).to(PropertiesServiceImpl.class).asEagerSingleton();

		//Dao

	}
}
