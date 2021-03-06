package projectinsight.module.project.persistence.employee;

import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.employee.model.EmployeeBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMapper extends EmployeeBuilder {

  public EmployeeMapper setResultSetData(ResultSet resultSet) {

    try {
      this.id = resultSet.getString("id");
      this.name = resultSet.getString("name");
      this.surname = resultSet.getString("surname");
      this.creationInstant = resultSet.getTimestamp("creation_instant").toInstant();
      this.lastUpdateInstant = resultSet.getTimestamp("last_update_instant").toInstant();
      this.deleted = resultSet.getBoolean("deleted");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return this;
  }

  public Employee buildEmployee() {

    Employee employee = new Employee();
    populateEmployeeFields(employee);

    return employee;
  }
}
