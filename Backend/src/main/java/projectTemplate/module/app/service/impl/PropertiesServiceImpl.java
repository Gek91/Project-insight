package projectTemplate.module.app.service.impl;

import java.io.IOException;
import java.util.Properties;

import com.google.inject.Inject;

import projectTemplate.module.app.commons.RuntimeEnvironment;
import projectTemplate.module.app.service.PropertiesService;
import projectTemplate.module.app.commons.AppRuntimeEnvironment;

public class PropertiesServiceImpl implements PropertiesService {

	private static final String CONFIG_FILE_CLASSPATH = "config/project.properties";

	private Properties configProperties;
	private AppRuntimeEnvironment appRuntimeEnvironment;


	@Inject
	protected void initService(RuntimeEnvironment runtimeEnvironment) throws IOException {

		//Retrieve runtime enviroment (injected)
		this.appRuntimeEnvironment = AppRuntimeEnvironment.getValueById(runtimeEnvironment.getId());

		if (this.appRuntimeEnvironment == null) {
			throw new IllegalArgumentException(String.format("Unrecognized runtimeEnvironment '%s'", runtimeEnvironment.getId()));
		}

		//Load properties file
		this.configProperties = new Properties();
		configProperties.load(getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_CLASSPATH));
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public AppRuntimeEnvironment getAppRuntimeEnvironment() {
		return this.appRuntimeEnvironment;
	}

	@Override
	public String getRdbmsJdbcConnectionURL() {
		return configProperties.getProperty("persistence.rdbms.jdbc.connection.url");
	}

	@Override
	public String getRdbmsJdbcConnectionUser() {
		return configProperties.getProperty("persistence.rdbms.jdbc.connection.user");
	}

	@Override
	public String getRdbmsJdbcConnectionPassword() {
		return configProperties.getProperty("persistence.rdbms.jdbc.connection.pwd");
	}

	@Override
	public String getTestValue() {

		return configProperties.getProperty("project.test");
	}

}
