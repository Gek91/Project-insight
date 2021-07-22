package projectinsight.module.project.domain.project.model;


import projectinsight.module.app.commons.uow.Entity;
import projectinsight.module.app.commons.validations.ValidationErrorTypeEnum;
import projectinsight.module.app.commons.validations.ValidationManager;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Project extends Entity<String> {

  protected String id;
  protected String name;
  protected String description;
  protected String customerId;
  protected ProjectTeam team;
  protected SortedMap<String, ProjectVersion> versions;

  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  public Project() {
    versions = new TreeMap<>();
  }

  public String getId() {
    return id;
  }
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
   return Collections.unmodifiableList(this.versions.values().stream().collect(Collectors.toList()));
  }

  public Optional<ProjectVersion> getLastActiveVersion() {

    int i = versions.size() - 1;

    List<ProjectVersion> versionsList = getVersions();
    while(i > 0) {

      ProjectVersion version = versionsList.get(i);

      if(version.getStatus().equals(ProjectVersionStatusEnum.RELEASED)) {
        return Optional.of(version);
      }

      i--;
    }

    return Optional.empty();
  }

  public void addProjectVersion(ProjectVersion newVersion) {

    ValidationManager.build()
      .validate(!this.versions.containsKey(newVersion.getProjectVersionString()), "version", ValidationErrorTypeEnum.INVALID)
      .validate(isVersionsValuesConsistent(newVersion), "version", ValidationErrorTypeEnum.INVALID)
      .throwValidationExceptionIfHasErrors();

    this.versions.put(newVersion.getProjectVersionString(), newVersion);
    this.lastUpdateInstant = Instant.now();
  }

  private boolean isVersionsValuesConsistent(ProjectVersion newVersion) {

    Optional<ProjectVersion> nextVersion = versions.values().stream().filter(x -> x.getProjectVersionString().compareTo(newVersion.getProjectVersionString()) > 0).findFirst();

    if(nextVersion.isPresent()) {
      return nextVersion.get().releaseDate.compareTo(newVersion.releaseDate) >= 0;
    }
    return true;
  }

  public void removeProjectVersion(int major, int minor, int patch) {

    this.versions.remove(
      ProjectVersion.buildProjectVersionString(major, minor, patch)
    );
    this.lastUpdateInstant = Instant.now();
  }

  public static ProjectBuilder getBuilder() {
    return new ProjectBuilder();
  }

}

