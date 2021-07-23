package projectinsight.module.project.domain.project.model;

import projectinsight.module.app.commons.validations.ValidationErrorTypeEnum;
import projectinsight.module.app.commons.validations.ValidationManager;

import java.time.Instant;
import java.time.LocalDate;

public class ProjectVersionBuilder {

  protected Integer majorVersion;
  protected Integer minorVersion;
  protected Integer patchVersion;
  protected String versionLabel;
  protected String note;
  protected LocalDate releaseDate;
  protected Integer statusId;

  protected Instant creationInstant;
  protected Instant lastUpdateInstant;

  public ProjectVersionBuilder() { }

  public ProjectVersionBuilder setMajorVersion(Integer majorVersion) {
    this.majorVersion = majorVersion;
    return this;
  }
  public ProjectVersionBuilder setMinorVersion(Integer minorVersion) {
    this.minorVersion = minorVersion;
    return this;
  }
  public ProjectVersionBuilder setPatchVersion(Integer patchVersion) {
    this.patchVersion = patchVersion;
    return this;
  }
  public ProjectVersionBuilder setVersionLabel(String versionLabel) {
    this.versionLabel = versionLabel;
    return this;
  }
  public ProjectVersionBuilder setNote(String note) {
    this.note = note;
    return this;
  }
  public ProjectVersionBuilder setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }
  public ProjectVersionBuilder setStatusId(Integer statusId) {
    this.statusId = statusId;
    return this;
  }
  public ProjectVersionBuilder setCreationInstant(Instant creationInstant) {
    this.creationInstant = creationInstant;

    return this;
  }
  public ProjectVersionBuilder setLastUpdateInstant(Instant lastUpdateInstant) {
    this.lastUpdateInstant = lastUpdateInstant;

    return this;
  }

  protected ProjectVersion buildProjectVersion() {

    projectVersionValidations();

    ProjectVersion version = new ProjectVersion();

    populateVersionFields(version);

    this.creationInstant = Instant.now();
    this.lastUpdateInstant = this.creationInstant;

    return version;
  }

  public void editProjectVersion(ProjectVersion version) {

    projectVersionValidations();

    this.creationInstant = version.creationInstant;
    this.lastUpdateInstant = Instant.now();

    populateVersionFields(version);
  }

  private void projectVersionValidations() {
    ValidationManager validations = ValidationManager.build();

    validations
      .required(this.majorVersion, "majorVersion")
      .required(this.minorVersion, "minorVersion")
      .required(this.patchVersion, "patchVersion")
      .required(this.statusId, "statusId")
      .required(this.releaseDate, "releaseDate")
      .validate(
        this.statusId == null ||
          ProjectVersionStatusEnum.getValueById(this.statusId).isPresent(),
        "statusId", ValidationErrorTypeEnum.INVALID)
      .throwValidationExceptionIfHasErrors();
  }

  protected void populateVersionFields(ProjectVersion version) {
    version.majorVersion = this.majorVersion;
    version.minorVersion = this.minorVersion;
    version.patchVersion = this.patchVersion;
    version.versionLabel = this.versionLabel;
    version.note = this.note;
    version.releaseDate = this.releaseDate;
    version.status = ProjectVersionStatusEnum.getValueById(this.statusId).get();

    version.creationInstant = this.creationInstant;
    version.lastUpdateInstant = this.lastUpdateInstant;
  }

}
