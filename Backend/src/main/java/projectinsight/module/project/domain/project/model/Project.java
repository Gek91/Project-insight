package projectinsight.module.project.domain.project.model;


import projectinsight.module.app.commons.persistence.Entity;
import projectinsight.module.app.commons.validations.ValidationErrorTypeEnum;
import projectinsight.module.app.commons.validations.ValidationManager;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Project extends Entity<String> {

  protected String id;
  protected int version;
  protected String name;
  protected String description;
  protected String customerId;
  protected ProjectTeam team;
  protected List<ProjectVersion> versions;

  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  public Project() {
    versions = new ArrayList<>();
  }

  public String getId() {
    return id;
  }
  public int getVersion() { return version; }
  public String getName() {
    return name;
  }
  public String getDescription() {
    return description;
  }
  public String getCustomerId() {
    return customerId;
  }
  public ProjectTeam getTeam() {
    return team;
  }
  public Instant getCreationInstant() {
    return creationInstant;
  }
  public Instant getLastUpdateInstant() {
    return lastUpdateInstant;
  }
  public boolean isDeleted() {
    return deleted;
  }
  public List<ProjectVersion> getVersions() {
   return Collections.unmodifiableList(this.versions.stream().collect(Collectors.toList()));
  }

  public Optional<ProjectVersion> getLastActiveVersion() {

    int i = versions.size() - 1;

    while(i >= 0) {

      ProjectVersion version = versions.get(i);

      if(version.getStatus().equals(ProjectVersionStatusEnum.RELEASED)) {
        return Optional.of(version);
      }

      i--;
    }

    return Optional.empty();
  }

  public void addProjectVersion(ProjectVersion newVersion) {

      ValidationManager.build()
        .validate(!versions.stream().anyMatch(x -> x.getId().equals(newVersion.getId())), "version.id", ValidationErrorTypeEnum.INVALID)
        .validate(!versions.stream().anyMatch(x -> x.getProjectVersionString().equals(newVersion.getProjectVersionString())), "version", ValidationErrorTypeEnum.INVALID)
        .validate(isVersionsValuesConsistent(newVersion), "version", ValidationErrorTypeEnum.INVALID)
        .throwValidationExceptionIfHasErrors();

    insertNewVersion(newVersion);

    this.setUpdated();
  }

  private boolean isVersionsValuesConsistent(ProjectVersion newVersion) {

    Optional<ProjectVersion> nextVersion = versions.stream().filter(x -> x.getProjectVersionString().compareTo(newVersion.getProjectVersionString()) > 0).findFirst();

    if(nextVersion.isPresent()) {
      return nextVersion.get().releaseDate.compareTo(newVersion.releaseDate) >= 0;
    }
    return true;
  }

  private void insertNewVersion(ProjectVersion newVersion) {
    Optional<ProjectVersion> nextVersion = versions.stream().filter(x -> x.getProjectVersionString().compareTo(newVersion.getProjectVersionString()) > 0).findFirst();

    int index = 0;

    for(; index < versions.size() ; index++) {

      if(versions.get(index).getProjectVersionString().compareTo(newVersion.getProjectVersionString()) > 0) {
        break;
      }
    }

    versions.add(index, newVersion);
  }

  public void removeProjectVersion(String id) {

    Optional<ProjectVersion> versionToRemoveOptional = this.versions.stream().filter(x -> x.getId().equals(id)).findFirst();

    if(versionToRemoveOptional.isPresent()) {
      this.versions.remove(versionToRemoveOptional.get());
    }

    this.setUpdated();
  }

}

