package projectTemplate.module.project.domain.project.service;

import projectTemplate.module.project.domain.project.model.Project;
import projectTemplate.module.project.service.data.*;

import java.util.List;

public interface ProjectService {

  Project getProject(String projectId);
  List<Project> getProjectList();
  Project createProject(CreateProjectData data);
  Project editProject(String projectId, EditProjectData data);
  Project deleteProject(String projectId);

  ProjectVersionActionResponse createProjectVersion(String projectId, CreateProjectVersionData data);
  ProjectVersionActionResponse editProjectVersion(String projectId, String projectVersionId, EditProjectVersionData data);

}
