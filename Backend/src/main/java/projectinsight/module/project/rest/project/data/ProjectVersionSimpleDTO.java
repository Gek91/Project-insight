package projectinsight.module.project.rest.project.data;

import projectinsight.module.project.domain.project.model.ProjectVersion;

import java.time.format.DateTimeFormatter;

public class ProjectVersionSimpleDTO {

  private Integer majorVersion;
  private Integer minorVersion;
  private Integer patchVersion;
  private String versionLabel;
  private String releaseDate;

  public Integer getMajorVersion() {
    return majorVersion;
  }
  public void setMajorVersion(Integer majorVersion) {
    this.majorVersion = majorVersion;
  }
  public Integer getMinorVersion() {
    return minorVersion;
  }
  public void setMinorVersion(Integer minorVersion) {
    this.minorVersion = minorVersion;
  }
  public Integer getPatchVersion() {
    return patchVersion;
  }
  public void setPatchVersion(Integer patchVersion) {
    this.patchVersion = patchVersion;
  }
  public String getVersionLabel() {
    return versionLabel;
  }
  public void setVersionLabel(String versionLabel) {
    this.versionLabel = versionLabel;
  }
  public String getReleaseDate() {
    return releaseDate;
  }
  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public static ProjectVersionSimpleDTO buildDTO(ProjectVersion input) {

    if(input == null) {
      return null;
    }

    ProjectVersionSimpleDTO output = new ProjectVersionSimpleDTO();
    populateDTOField(output, input);

    return output;
  }

  protected static void populateDTOField(ProjectVersionSimpleDTO output, ProjectVersion input) {
    output.setMajorVersion(input.getMajorVersion());
    output.setMinorVersion(input.getMinorVersion());
    output.setPatchVersion(input.getMinorVersion());
    output.setVersionLabel(input.getVersionLabel());
    output.setReleaseDate(input.getReleaseDate() != null ? input.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null);
  }
}
