package projectinsight.module.project.rest.project.data;

import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.project.model.ProjectTeam;
import projectinsight.module.project.rest.employee.data.EmployeeSimpleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectTeamDTO {

  private List<EmployeeSimpleDTO> projectManager;
  private List<EmployeeSimpleDTO> techLead;
  private List<EmployeeSimpleDTO> developers;

  public ProjectTeamDTO() {
    this.developers = new ArrayList<>();
    this.projectManager = new ArrayList<>();
    this.techLead = new ArrayList<>();
  }

  public List<EmployeeSimpleDTO> getProjectManager() {
    return projectManager;
  }

  public void setProjectManager(List<EmployeeSimpleDTO> projectManager) {
    this.projectManager = projectManager;
  }

  public List<EmployeeSimpleDTO> getTechLead() {
    return techLead;
  }

  public void setTechLead(List<EmployeeSimpleDTO> techLead) {
    this.techLead = techLead;
  }

  public List<EmployeeSimpleDTO> getDevelopers() {
    return developers;
  }

  public void setDevelopers(List<EmployeeSimpleDTO> developers) {
    this.developers = developers;
  }

  public static ProjectTeamDTO buildDTO(ProjectTeam input, Map<String, Employee> employeeMap) {

    if(input == null) {
      return null;
    }

    ProjectTeamDTO output = new ProjectTeamDTO();

    input.getDevelopersIds().forEach(id -> {
      EmployeeSimpleDTO employee = EmployeeSimpleDTO.buildDTO(employeeMap.get(id));

      if(employee != null) {
        output.developers.add(employee);
      }
    });

    input.getProjectManagerIds().forEach(id -> {
      EmployeeSimpleDTO employee = EmployeeSimpleDTO.buildDTO(employeeMap.get(id));

      if(employee != null) {
        output.projectManager.add(employee);
      }
    });

    input.getTechLeadIds().forEach(id -> {
      EmployeeSimpleDTO employee = EmployeeSimpleDTO.buildDTO(employeeMap.get(id));

      if(employee != null) {
        output.techLead.add(employee);
      }
    });

    return output;
  }
}
