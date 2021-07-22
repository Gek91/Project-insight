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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectRespositoryImpl extends RepositoryAbstractImpl<String, Project, ProjectSearchOptions> implements ProjectRepository {

  @Inject
  private PersistenceService persistenceService;

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
  protected void applyAdd(Project entity) {

  }

  @Override
  protected boolean applyUpdate(Project entity) {
    return false;
  }

  @Override
  protected void applyRemove(Project entity) {

  }

  private Map<String, Map<EmployeeRoleEnum, List<String>>> retrieveProjectsTeamMember(Set<String> projectsIds) {

    Map<String, Map<EmployeeRoleEnum, List<String>>> result = new HashMap<>();

    if (projectsIds != null) {

      try (Connection connection = persistenceService.getConnection()) {

        String query = "" +
          "SELECT * " +
          "FROM project_team_member " +
          "WHERE project_id IN (%s) ";

        query = String.format(query, projectsIds.stream().collect(Collectors.joining(", ")));

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

        query = String.format(query, projectsIds.stream().collect(Collectors.joining(", ")));

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
