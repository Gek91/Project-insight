package projectinsight.module.project.rest.project.data;

import java.time.LocalDate;

public class CreateProjectVersionRequestDTO {

  private Integer majorVersion;
  private Integer minorVersion;
  private Integer patchVersion;
  private String versionLabel;
  private String releaseDate;
  private String note;
  private Integer statusId;

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

}
