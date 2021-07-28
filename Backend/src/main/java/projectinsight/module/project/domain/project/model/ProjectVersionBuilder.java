package projectinsight.module.project.domain.project.model;

import projectinsight.module.app.commons.UIIDGenerator;
import projectinsight.module.app.commons.validations.ValidationErrorTypeEnum;
import projectinsight.module.app.commons.validations.ValidationManager;
import projectinsight.module.app.service.PropertiesService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ProjectVersionBuilder {

  //dependencies
  private PropertiesService propertiesService;

  protected String id;
  protected Integer majorVersion;
  protected Integer minorVersion;
  protected Integer patchVersion;
  protected String versionLabel;
  protected String note;
  protected String releaseDate;
  protected Integer statusId;

  protected Instant creationInstant;
  protected Instant lastUpdateInstant;


  public ProjectVersionBuilder(PropertiesService propertiesService) {
    this.propertiesService = propertiesService;
  }

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
  public ProjectVersionBuilder setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
    return this;
  }
  public ProjectVersionBuilder setStatusId(Integer statusId) {
    this.statusId = statusId;
    return this;
  }

  public ProjectVersion buildProjectVersion() {

    projectVersionValidations();

    ProjectVersion version = new ProjectVersion();

    this.id = UIIDGenerator.generate();
    this.creationInstant = Instant.now();
    this.lastUpdateInstant = this.creationInstant;

    populateVersionFields(version);

    return version;
  }

  public void editProjectVersion(ProjectVersion version) {

    projectVersionValidations();

    this.id = version.id;
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
        "statusId", ValidationErrorTypeEnum.INVALID);

    if(this.releaseDate != null) {
      try {
        LocalDate.parse(this.releaseDate, propertiesService.getLocalDateStringFormatter());
      } catch (DateTimeParseException e) {
        validations.validate(false, "releaseDate", ValidationErrorTypeEnum.INVALID);
      }
    }

    validations.throwValidationExceptionIfHasErrors();
  }

  protected void populateVersionFields(ProjectVersion version) {

    version.id = this.id;
    version.majorVersion = this.majorVersion;
    version.minorVersion = this.minorVersion;
    version.patchVersion = this.patchVersion;
    version.versionLabel = this.versionLabel;
    version.note = this.note;
    version.releaseDate = LocalDate.parse(this.releaseDate, propertiesService.getLocalDateStringFormatter());
    version.status = ProjectVersionStatusEnum.getValueById(this.statusId).get();

    version.creationInstant = this.creationInstant;
    version.lastUpdateInstant = this.lastUpdateInstant;
  }

}
