package projectinsight.module.project.rest.project;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.service.PropertiesService;
import projectinsight.module.project.domain.project.model.ProjectVersionBuilder;

public class ProjectVersionBuilderProvider implements Provider<ProjectVersionBuilder> {

  @Inject
  private PropertiesService propertiesService;

  @Override
  public ProjectVersionBuilder get() {
    return new ProjectVersionBuilder(propertiesService);
  }
}
