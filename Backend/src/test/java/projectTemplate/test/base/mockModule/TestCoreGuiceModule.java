package projectTemplate.test.base.mockModule;

import com.google.inject.AbstractModule;

import projectTemplate.module.app.service.PropertiesService;
import projectTemplate.module.app.service.impl.PropertiesServiceImpl;

public class TestCoreGuiceModule extends AbstractModule {

	@Override
	protected void configure() {


		//Rest API

		//Services

		bind(PropertiesService.class).to(PropertiesServiceImpl.class).asEagerSingleton();

		//Dao

	}
}
