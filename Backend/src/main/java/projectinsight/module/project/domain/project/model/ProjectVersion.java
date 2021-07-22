package projectinsight.module.project.domain.project.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

public class ProjectVersion {

  protected Integer majorVersion;
  protected Integer minorVersion;
  protected Integer patchVersion;
  protected String versionLabel;
  protected String note;
  protected LocalDate releaseDate;
  protected ProjectVersionStatusEnum status;

  protected Instant creationInstant;
  protected Instant lastUpdateInstant;

  public ProjectVersion() { }

  public static String buildProjectVersionString(int major, int minor, int patch) {

    StringBuilder builder = new StringBuilder("");
    builder.append(major);
    builder.append(".");
    builder.append(minor);
    builder.append(".");
    builder.append(patch);

    return builder.toString();
  }

  public String getProjectVersionString() {
    return ProjectVersion.buildProjectVersionString(this.majorVersion, this.minorVersion, this.patchVersion);
  }

  public Integer getMajorVersion() {
    return majorVersion;
  }
  public Integer getMinorVersion() {
    return minorVersion;
  }
  public Integer getPatchVersion() {
    return patchVersion;
  }
  public String getVersionLabel() {
    return versionLabel;
  }
  public String getNote() {
    return note;
  }
  public LocalDate getReleaseDate() {
    return releaseDate;
  }
  public ProjectVersionStatusEnum getStatus() {
    return status;
  }
  public Instant getCreationInstant() {
    return creationInstant;
  }
  public Instant getLastUpdateInstant() {
    return lastUpdateInstant;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProjectVersion that = (ProjectVersion) o;

    return majorVersion.equals(that.majorVersion) && minorVersion.equals(that.minorVersion) && patchVersion.equals(that.patchVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(majorVersion, minorVersion, patchVersion);
  }

}




