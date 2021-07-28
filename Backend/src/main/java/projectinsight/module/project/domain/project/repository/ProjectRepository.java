package projectinsight.module.project.domain.project.repository;


import projectinsight.module.app.commons.persistence.Repository;
import projectinsight.module.project.domain.project.model.Project;

import java.util.List;

public interface ProjectRepository extends Repository {

  Project findForRead(String id);
  Project findForUpdate(String id);
  List<Project> search(ProjectSearchOptions options);
  void add(Project project);
  void remove(Project project);

}
