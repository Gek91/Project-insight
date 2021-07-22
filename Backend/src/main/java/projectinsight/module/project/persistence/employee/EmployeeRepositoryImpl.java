package projectinsight.module.project.persistence.employee;

import com.google.inject.Inject;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.employee.EmployeeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeRepositoryImpl implements EmployeeRepository {

  @Inject
  private PersistenceService persistenceService;



  @Override
  public Employee findById(String id) {
    return null;
  }

  @Override
  public void add(Employee employee) {

  }

  @Override
  public void remove(String id) {

  }

  @Override
  public List<Employee> search() {
    return null;
  }

  @Override
  public Map<String, Employee> findAll() {

    Map<String, Employee> result = new HashMap<>();

    try(Connection connection = persistenceService.getConnection()) {

        String query = "" +
          "SELECT * " +
          "FROM employee " +
          "WHERE employee.deleted = false";

        try (PreparedStatement statement = connection.prepareStatement(query)) {

          statement.execute();

          try (ResultSet resultSet = statement.getResultSet()) {

            while (resultSet.next()) {

              String id = resultSet.getString("id");
              Employee employee = new EmployeeMapper().buildEmployee(resultSet);

              result.put(id, employee);
            }
          }
        }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }


}
