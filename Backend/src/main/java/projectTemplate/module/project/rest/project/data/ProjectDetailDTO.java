package projectTemplate.module.project.rest.project.data;

import projectTemplate.module.project.domain.customer.model.Customer;
import projectTemplate.module.project.domain.employee.model.Employee;
import projectTemplate.module.project.domain.project.model.Project;
import projectTemplate.module.project.rest.customer.data.CustomerSimpleDTO;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ProjectDetailDTO extends AbstractProjectDTO{


  private ProjectTeamDTO team;
  private List<ProjectVersionDTO> versions;


  public ProjectTeamDTO getTeam() {
    return team;
  }
  public void setTeam(ProjectTeamDTO team) {
    this.team = team;
  }
  public List<ProjectVersionDTO> getVersions() {
    return versions;
  }
  public void setVersions(List<ProjectVersionDTO> versions) {
    this.versions = versions;
  }


  public static ProjectDetailDTO buildDTO(Project input, Map<String, Customer> customerMap, Map<String, Employee> employeeMap) {

    if(input == null) {
      return null;
    }

    ProjectDetailDTO output = new ProjectDetailDTO();
    populateDTOFields(output, input, customerMap);
    output.setTeam(ProjectTeamDTO.buildDTO(input.getTeam(), employeeMap));
    output.setVersions(ProjectVersionDTO.buildDTO(input.getVersions()));

    return output;
  }
}
