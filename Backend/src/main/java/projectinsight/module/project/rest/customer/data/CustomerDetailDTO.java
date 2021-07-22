package projectinsight.module.project.rest.customer.data;

import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.rest.project.data.ProjectListElementDTO;

import java.util.Collections;
import java.util.List;

public class CustomerDetailDTO extends CustomerListElementDTO {

  private long creationInstant;
  private List<ProjectListElementDTO> projects;


  public long getCreationInstant() {
    return creationInstant;
  }
  public void setCreationInstant(long creationInstant) {
    this.creationInstant = creationInstant;
  }

  public List<ProjectListElementDTO> getProjects() {
    return projects;
  }
  public void setProjects(List<ProjectListElementDTO> projects) {
    this.projects = projects;
  }

  public static CustomerDetailDTO buildDTO(Customer input, List<Project> projects) {

    if(input == null) {
      return null;
    }

    CustomerDetailDTO output = new CustomerDetailDTO();
    populateDTOFields(output, input);
    output.setCreationInstant(input.getCreationInstant().toEpochMilli());
    output.setProjects(ProjectListElementDTO.buildDTO(projects, Collections.singletonMap(input.getId(), input)));

    return output;
  }
}
