package projectinsight.module.project.persistence.project;

import projectinsight.module.app.service.PropertiesService;
import projectinsight.module.project.domain.project.model.ProjectVersion;
import projectinsight.module.project.domain.project.model.ProjectVersionBuilder;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectVersionMapper extends ProjectVersionBuilder {


  public ProjectVersionMapper(PropertiesService propertiesService) {
    super(propertiesService);
  }

  public ProjectVersion buildProjectVersion(ResultSet resultSet) {

    try {
      this.id = resultSet.getString("id");
      this.majorVersion = resultSet.getInt("major_version");
      this.minorVersion = resultSet.getInt("minor_version");
      this.patchVersion = resultSet.getInt("patch_version");
      this.versionLabel = resultSet.getString("label");
      this.note = resultSet.getString("note");
      Date releaseDate = resultSet.getDate("release_date");
      this.releaseDate = releaseDate != null ?releaseDate.toLocalDate().toString() : null;
      this.statusId = resultSet.getInt("status");
      this.creationInstant = resultSet.getTimestamp("creation_instant").toInstant();
      this.lastUpdateInstant = resultSet.getTimestamp("last_update_instant").toInstant();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    ProjectVersion version = new ProjectVersion();
    populateVersionFields(version);

    return version;
  }
}
