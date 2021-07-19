package projectTemplate.module.app.service;

import projectTemplate.module.app.commons.AppRuntimeEnvironment;

public interface PropertiesService {

	public AppRuntimeEnvironment getAppRuntimeEnvironment();

	public String getRdbmsJdbcConnectionURL();

	public String getRdbmsJdbcConnectionUser();

	public String getRdbmsJdbcConnectionPassword();

	String getTestValue();



}
