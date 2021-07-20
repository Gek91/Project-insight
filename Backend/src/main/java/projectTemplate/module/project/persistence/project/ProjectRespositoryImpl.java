package projectTemplate.module.project.persistence.project;

import com.google.inject.Inject;
import org.apache.commons.lang3.text.StrSubstitutor;
import projectTemplate.module.app.service.PersistenceService;
import projectTemplate.module.project.domain.project.repository.ProjectRepository;
import projectTemplate.module.project.domain.project.model.Project;
import projectTemplate.module.project.domain.project.model.ProjectVersion;
import projectTemplate.module.project.domain.project.repository.data.ProjectSearchOptions;
import projectTemplate.module.project.persistence.employee.EmployeeRoleEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectRespositoryImpl implements ProjectRepository {

  @Inject
  private PersistenceService persistenceService;

  @Override
  public Project findForRead(String id) {

    String query = "" +
      "SELECT * " +
      "FROM project " +
      "WHERE project.deleted = false " +
      "AND project.id = ? ";
    // "FOR SHARE ; ";

    return find(id, query);
  }

  @Override
  public Project findForUpdate(String id) {

    String query = "" +
      "SELECT * " +
      "FROM project " +
      "WHERE project.deleted = false " +
      "AND project.id = ? " +
      "FOR UPDATE ";

    return find(id, query);
  }

  private Project find(String id, String query) {

    Project result = null;

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, id);
        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {

          if (resultSet.next()) {

            String projectId = resultSet.getString("id");

            Map<EmployeeRoleEnum, List<String>> teamMemberMap = retrieveProjectsTeamMember(Collections.singleton(projectId)).get(projectId);
            List<ProjectVersion> versions = retrieveProjectsVersions(Collections.singleton(projectId)).get(projectId);

            result = new ProjectMapper()
              .setResultSetData(resultSet)
              .setTeamMemberData(teamMemberMap)
              .setVersionsData(versions).buildProject();
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
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

  @Override
  public List<Project> search(ProjectSearchOptions options) {

    List<Project> result = new ArrayList<>();

    try (Connection connection = persistenceService.getConnection()) {

      String query = "" +
        "SELECT * " +
        "FROM project " +
        "WHERE deleted = false " +
        "${filterOptionPlaceHolder} " +
        "ORDER BY creation_instant DESC";

      Map<Integer, Object> parameterNameValueMaps = new HashMap<>();
      Map<String, String> sqlQueryPlaceHolderParams = new HashMap<>();
      sqlQueryPlaceHolderParams.put("filterOptionPlaceHolder", buildFilterOptionClausole(options, parameterNameValueMaps));

      try (PreparedStatement statement = connection.prepareStatement(
        StrSubstitutor.replace(query, sqlQueryPlaceHolderParams))) {

        for (Map.Entry<Integer, Object> integerObjectEntry : parameterNameValueMaps.entrySet()) {
          statement.setObject(integerObjectEntry.getKey(), integerObjectEntry.getValue());
        }
        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {

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
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  private String buildFilterOptionClausole(ProjectSearchOptions options, Map<Integer, Object> parameterNameValueMaps) {

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
  public void add(Project project) {

  }

  @Override
  public void remove(String id) {

  }

}
