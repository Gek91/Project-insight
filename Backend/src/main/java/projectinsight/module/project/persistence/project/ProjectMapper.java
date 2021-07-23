package projectinsight.module.project.persistence.project;

import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectBuilder;
import projectinsight.module.project.domain.project.model.ProjectVersion;
import projectinsight.module.project.persistence.employee.EmployeeRoleEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProjectMapper extends ProjectBuilder {

  public ProjectMapper setResultSetData(ResultSet resultSet) {

    try {
      this.id = resultSet.getString("id");
      this.version = resultSet.getInt("version");
      this.name = resultSet.getString("name");
      this.description = resultSet.getString("description");
      this.customerId = resultSet.getString("customer_id");
      this.creationInstant = resultSet.getTimestamp("creation_instant").toInstant();
      this.lastUpdateInstant = resultSet.getTimestamp("last_update_instant").toInstant();
      this.deleted = resultSet.getBoolean("deleted");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return this;
  }

  public ProjectMapper setTeamMemberData(Map<EmployeeRoleEnum, List<String>> teamMembersMap) {

    if (teamMembersMap != null) {
      this.projectManagerIds = teamMembersMap.get(EmployeeRoleEnum.MANAGER);
      this.techLeadIds = teamMembersMap.get(EmployeeRoleEnum.TECH_LEAD);
      this.developersIds = teamMembersMap.get(EmployeeRoleEnum.DEVELOPER);
    }

    return this;
  }

  public ProjectMapper setVersionsData(List<ProjectVersion> versions) {
    this.versions = versions;

    return this;
  }

  public Project buildProject() {
    Project project = new Project();
    populateProjectFields(project);

    return project;
  }

}
