package projectinsight.module.app.service;

import projectinsight.module.app.commons.AppRuntimeEnvironment;

import java.time.format.DateTimeFormatter;

public interface PropertiesService {

	public AppRuntimeEnvironment getAppRuntimeEnvironment();

	public String getRdbmsJdbcConnectionURL();

	public String getRdbmsJdbcConnectionUser();

	public String getRdbmsJdbcConnectionPassword();

  DateTimeFormatter getLocalDateStringFormatter();



}
