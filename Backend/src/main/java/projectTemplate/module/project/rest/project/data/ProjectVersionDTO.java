package projectTemplate.module.project.rest.project.data;

import projectTemplate.module.project.domain.project.model.ProjectVersion;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectVersionDTO extends ProjectVersionSimpleDTO {


  private String note;
  private Integer statusId;

  public String getNote() {
    return note;
  }
  public void setNote(String note) {
    this.note = note;
  }
  public Integer getStatusId() {
    return statusId;
  }
  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }



  public static List<ProjectVersionDTO> buildDTO(List<ProjectVersion> input) {

    List<ProjectVersionDTO> output = new ArrayList<>();

    if(input != null) {

      input.forEach(version -> {

        ProjectVersionDTO versionDTO = buildDTO(version);

        if(versionDTO != null){
          output.add(versionDTO);
        }
      });
    }

    return output;
  }

  public static ProjectVersionDTO buildDTO(ProjectVersion input) {

    if(input == null) {
      return null;
    }

    ProjectVersionDTO output = new ProjectVersionDTO();
    populateDTOField(output, input);
    output.setNote(input.getNote());
    output.setStatusId(input.getStatus().getId());

    return output;
  }
}
