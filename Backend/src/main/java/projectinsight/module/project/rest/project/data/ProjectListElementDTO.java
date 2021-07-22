package projectinsight.module.project.rest.project.data;

import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProjectListElementDTO extends AbstractProjectDTO{

  private ProjectVersionSimpleDTO lastProjectVersion;


  public ProjectVersionSimpleDTO getLastProjectVersion() {
    return lastProjectVersion;
  }
  public void setLastProjectVersion(ProjectVersionSimpleDTO lastProjectVersion) {
    this.lastProjectVersion = lastProjectVersion;
  }


  public static List<ProjectListElementDTO> buildDTO(List<Project> input, Map<String, Customer> customerMap) {

    List<ProjectListElementDTO> output = new ArrayList<>();

    if(input != null) {
      input.forEach(element -> {
        ProjectListElementDTO elementDTO = buildDTO(element, customerMap);

        if(elementDTO != null) {
          output.add(elementDTO);
        }
      });
    }

    return output;
  }

  public static ProjectListElementDTO buildDTO(Project input, Map<String, Customer> customerMap) {

    if(input == null) {
      return null;
    }

    ProjectListElementDTO output = new ProjectListElementDTO();
    populateDTOFields(output, input, customerMap);

    Optional<ProjectVersion> projectVersionOptional = input.getLastActiveVersion();
    output.setLastProjectVersion(ProjectVersionDTO.buildDTO(projectVersionOptional.isPresent() ? projectVersionOptional.get() : null));

    return output;
  }
}
