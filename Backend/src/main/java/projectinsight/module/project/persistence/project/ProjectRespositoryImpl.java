package projectinsight.module.project.persistence.project;

import com.google.inject.Inject;
import org.apache.commons.lang3.text.StrSubstitutor;
import projectinsight.module.app.commons.uow.RepositoryAbstractImpl;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectVersion;
import projectinsight.module.project.domain.project.repository.ProjectSearchOptions;
import projectinsight.module.project.persistence.employee.EmployeeRoleEnum;

import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectRespositoryImpl extends RepositoryAbstractImpl<String, Project, ProjectSearchOptions> implements ProjectRepository {

  @Override
  protected String getFindForReadQuery() {
    return "" +
      "SELECT * " +
      "FROM project " +
      "WHERE project.deleted = false " +
      "AND project.id = ? ";
    // "FOR SHARE ; ";
  }

  @Override
  protected String getFindForUpdateQuery() {
    return "" +
      "SELECT * " +
      "FROM project " +
      "WHERE project.deleted = false " +
      "AND project.id = ? " +
      "FOR UPDATE ";
  }

  @Override
  protected String getSearchQuery() {
    return "SELECT * " +
      "FROM project " +
      "WHERE deleted = false " +
      "${filterOptionPlaceHolder} " +
      "ORDER BY creation_instant DESC";
  }

  @Override
  protected String buildFilterOptionClause(ProjectSearchOptions options, Map<Integer, Object> parameterNameValueMaps) {

    StringBuilder stringBuilder = new StringBuilder(" ");

    if(options != null) {

      int index = 1;

      if(options.getCustomerId() != null) {

        stringBuilder.append("AND customer_id = ? ");
        parameterNameValueMaps.put(index, options.getCustomerId());

        index++;
      }
    }

    return stringBuilder.toString();
  }

  @Override
  protected Project buildSingleEntityFromResultSet(ResultSet resultSet) throws SQLException {

    String projectId = resultSet.getString("id");

    Map<EmployeeRoleEnum, List<String>> teamMemberMap = retrieveProjectsTeamMember(Collections.singleton(projectId)).get(projectId);
    List<ProjectVersion> versions = retrieveProjectsVersions(Collections.singleton(projectId)).get(projectId);

    return new ProjectMapper()
      .setResultSetData(resultSet)
      .setTeamMemberData(teamMemberMap)
      .setVersionsData(versions).buildProject();
  }

  @Override
  protected List<Project> buildListEntitiesFromResultSet(ResultSet resultSet) throws SQLException {

    List<Project> result = new ArrayList<>();

    Map<String, ProjectMapper> projectMapperMap = new HashMap<>();
    while (resultSet.next()) {
      String projectId = resultSet.getString("id");
      projectMapperMap.put(projectId, new ProjectMapper().setResultSetData(resultSet));
    }

    Map<String, Map<EmployeeRoleEnum, List<String>>> projectEmployeeMap = retrieveProjectsTeamMember(projectMapperMap.keySet());
    Map<String, List<ProjectVersion>> projectVersionsMap = retrieveProjectsVersions(projectMapperMap.keySet());

    projectMapperMap.entrySet().forEach(entry -> {

      result.add(entry.getValue()
        .setTeamMemberData(projectEmployeeMap.get(entry.getKey()))
        .setVersionsData(projectVersionsMap.get(entry.getKey()))
        .buildProject()
      );
    });

    return result;
  }

  @Override
  protected void applyAdd(Project project) {

    insertProject(project);
    insertProjectTeamMember(project);
    insertProjectVersions(project);
  }

  private void insertProject(Project project) {

    String query = "" +
      "INSERT INTO project " +
      "VALUES( ?, ?, ?, ?, ?, ?, ?, ?) ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, project.getId());
        statement.setString(2, project.getName());
        statement.setInt(3, project.getVersion());
        statement.setString(4, project.getDescription());
        statement.setString(5, project.getCustomerId());
        statement.setTimestamp(6, Timestamp.from(project.getCreationInstant()));
        statement.setTimestamp(7, Timestamp.from(project.getLastUpdateInstant()));
        statement.setBoolean(8, project.isDeleted());

        statement.execute();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private void insertProjectTeamMember(Project project) {

    if(project.getTeam() != null) {

      String query = "" +
        "INSERT INTO project_team_member " +
        "VALUES(?, ?, ?) ";

      try (Connection connection = persistenceService.getConnection()) {

        try (PreparedStatement statement = connection.prepareStatement(query)) {

          for (String projectManagerId : project.getTeam().getProjectManagerIds()) {
            statement.setString(1, project.getId());
            statement.setString(2, projectManagerId);
            statement.setInt(3, EmployeeRoleEnum.MANAGER.getId());
            statement.addBatch();
          }

          for(String developerId : project.getTeam().getDevelopersIds()) {
            statement.setString(1, project.getId());
            statement.setString(2, developerId);
            statement.setInt(3, EmployeeRoleEnum.DEVELOPER.getId());
            statement.addBatch();
          }

         for(String techLeadId : project.getTeam().getTechLeadIds()) {
           statement.setString(1, project.getId());
           statement.setString(2, techLeadId);
           statement.setInt(3, EmployeeRoleEnum.TECH_LEAD.getId());
           statement.addBatch();
         }

         statement.executeBatch();
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  private void removeProjectTeamMembers(String projectId) {

    String query ="" +
      "DELETE FROM project_team_member " +
      "WHERE project_id = ? ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, projectId);

        statement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void insertProjectVersions(Project project) {

    if(project.getVersions() != null && !project.getVersions().isEmpty()) {

      String query = "" +
        "INSERT INTO project_version " +
        "VALUES(?, ?, ?) ";

      try (Connection connection = persistenceService.getConnection()) {

        try (PreparedStatement statement = connection.prepareStatement(query)) {

          for (ProjectVersion version : project.getVersions()) {
            statement.setInt(1, version.getMajorVersion());
            statement.setInt(2, version.getMinorVersion());
            statement.setInt(3, version.getPatchVersion());
            statement.setString(4, project.getId());
            statement.setString(5, version.getVersionLabel());
            statement.setString(6, version.getNote());
            statement.setInt(7, version.getStatus().getId());
            statement.setDate(8, Date.valueOf(version.getReleaseDate()));
            statement.setTimestamp(5, Timestamp.from(version.getCreationInstant()));
            statement.setTimestamp(6, Timestamp.from(version.getLastUpdateInstant()));
            statement.addBatch();
          }

          statement.executeBatch();
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  @Override
  protected boolean applyUpdate(Project project) {

    if(updateProject(project)) {

      removeProjectTeamMembers(project.getId());
      insertProjectTeamMember(project);

      return true;
    } else {
      return false;
    }
  }

  private boolean updateProject(Project project) {
    String query = "" +
      "UPDATE project " +
      "SET name = ?, version = ?, description = ?, customer_id = ?, last_update_instant = ?, deleted = ? " +
      "WHERE id = ? AND version = ? ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, project.getName());
        statement.setInt(2, project.getVersion() + 1);
        statement.setString(3, project.getDescription());
        statement.setString(4, project.getCustomerId());
        statement.setTimestamp(5, Timestamp.from(project.getLastUpdateInstant()));
        statement.setBoolean(6, project.isDeleted());
        statement.setString(7, project.getId());
        statement.setInt(8, project.getVersion());

        return statement.executeUpdate() > 0;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void applyRemove(Project project) {
    String query = "" +
      "UPDATE project " +
      "SET last_update_instant = ?, deleted = ? " +
      "WHERE id = ? ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setTimestamp(1, Timestamp.from(project.getLastUpdateInstant()));
        statement.setBoolean(2, true);
        statement.setString(3, project.getId());

        statement.executeUpdate();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private Map<String, Map<EmployeeRoleEnum, List<String>>> retrieveProjectsTeamMember(Set<String> projectsIds) {

    Map<String, Map<EmployeeRoleEnum, List<String>>> result = new HashMap<>();

    if (projectsIds != null) {

      try (Connection connection = persistenceService.getConnection()) {

        String query = "" +
          "SELECT * " +
          "FROM project_team_member " +
          "WHERE project_id IN (%s) ";

        query = String.format(query, projectsIds.stream().map(x -> "\'" + x + "\'").collect(Collectors.joining(", ")));

        try (PreparedStatement statement = connection.prepareStatement(query)) {

          statement.execute();

          try (ResultSet resultSet = statement.getResultSet()) {
            while (resultSet.next()) {

              String projectId = resultSet.getString("project_id");
              String employeeId = resultSet.getString("employee_id");
              EmployeeRoleEnum role = EmployeeRoleEnum.getValueById(resultSet.getInt("role_id")).get();

              Map<EmployeeRoleEnum, List<String>> projectTeamMap = result.computeIfAbsent(projectId, x -> new HashMap<>());
              projectTeamMap.computeIfAbsent(role, x -> new ArrayList<>()).add(employeeId);
            }
          }
        }

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return result;
  }

  private Map<String, List<ProjectVersion>> retrieveProjectsVersions(Set<String> projectsIds) {

    Map<String, List<ProjectVersion>> result = new HashMap<>();

    if (projectsIds != null) {

      try (Connection connection = persistenceService.getConnection()) {

        String query = "" +
          "SELECT * " +
          "FROM project_version " +
          "WHERE project_id IN (%s) " +
          "ORDER BY major_version ASC, minor_version ASC, patch_version ASC";

        query = String.format(query, projectsIds.stream().map(x -> "\'" + x + "\'").collect(Collectors.joining(", ")));

        try (PreparedStatement statement = connection.prepareStatement(query)) {

          statement.execute();

          try (ResultSet resultSet = statement.getResultSet()) {

            while (resultSet.next()) {
              String projectId = resultSet.getString("project_id");
              ProjectVersion projectVersion = new ProjectVersionMapper().buildProjectVersion(resultSet);

              result.computeIfAbsent(projectId, x -> new ArrayList<>()).add(projectVersion);
            }
          }
        }

      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return result;
  }

}
