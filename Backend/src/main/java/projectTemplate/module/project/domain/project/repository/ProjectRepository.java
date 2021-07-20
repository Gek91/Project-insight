package projectTemplate.module.project.domain.project.repository;


import projectTemplate.module.project.domain.project.model.Project;
import projectTemplate.module.project.domain.project.repository.data.ProjectSearchOptions;

import java.util.List;

public interface ProjectRepository {

  Project findForRead(String id);
  Project findForUpdate(String id);
  List<Project> search(ProjectSearchOptions options);
  void add(Project project);
  void remove(String id);

}
