package projectTemplate.module.project.domain.project.repository;


import projectTemplate.module.project.domain.project.model.Project;

import java.util.List;

public interface ProjectRepository {

  Project findForUpdate(String id);
  Project findForRead(String id);
  List<Project> search();
  void add(Project project);
  void remove(String id);

}
