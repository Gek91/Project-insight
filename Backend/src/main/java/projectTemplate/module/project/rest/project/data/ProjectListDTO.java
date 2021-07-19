package projectTemplate.module.project.rest.project.data;

import projectTemplate.module.project.domain.customer.model.Customer;
import projectTemplate.module.project.domain.project.model.Project;
import projectTemplate.module.project.rest.customer.data.CustomerSimpleDTO;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ProjectListDTO {

  private List<ProjectListElementDTO> projects;

  public List<ProjectListElementDTO> getProjects() {
    return projects;
  }
  public void setProjects(List<ProjectListElementDTO> projects) {
    this.projects = projects;
  }


  public static ProjectListDTO buildDTO(List<Project> projects, Map<String, Customer> customerMap) {

    ProjectListDTO output = new ProjectListDTO();
    output.setProjects(ProjectListElementDTO.buildDTO(projects,customerMap));

    return output;
  }
}
