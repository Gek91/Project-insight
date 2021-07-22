package projectinsight.module.app.service;

import projectinsight.module.app.commons.AppRuntimeEnvironment;

public interface PropertiesService {

	public AppRuntimeEnvironment getAppRuntimeEnvironment();

	public String getRdbmsJdbcConnectionURL();

	public String getRdbmsJdbcConnectionUser();

	public String getRdbmsJdbcConnectionPassword();

	String getTestValue();



}
