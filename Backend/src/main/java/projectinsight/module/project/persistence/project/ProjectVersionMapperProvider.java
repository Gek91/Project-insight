package projectinsight.module.project.persistence.project;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.service.PropertiesService;

public class ProjectVersionMapperProvider implements Provider<ProjectVersionMapper> {

  @Inject
  private PropertiesService propertiesService;

  @Override
  public ProjectVersionMapper get() {
    return new ProjectVersionMapper(propertiesService);
  }

}
