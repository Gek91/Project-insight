package projectinsight.module.project.persistence.employee;

import com.google.inject.Inject;
import projectinsight.module.app.commons.uow.RepositoryAbstractImpl;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.customer.repository.CustomerSearchOptions;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;
import projectinsight.module.project.domain.employee.repository.EmployeeSearchOptions;
import projectinsight.module.project.persistence.customer.CustomerMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmployeeRepositoryImpl extends RepositoryAbstractImpl<String, Employee, EmployeeSearchOptions> implements EmployeeRepository {


  @Override
  protected String getFindForReadQuery() {
    return "" +
      "SELECT * " +
      "FROM employee " +
      "WHERE employee.deleted = false " +
      "AND employee.id = ? ";
    // "FOR SHARE ; ";
  }

  @Override
  protected String getFindForUpdateQuery() {
    return "" +
      "SELECT * " +
      "FROM employee " +
      "WHERE employee.deleted = false " +
      "AND employee.id = ? " +
      "FOR UPDATE ";
  }

  @Override
  protected String getSearchQuery() {
    return "SELECT * " +
      "FROM employee " +
      "WHERE deleted = false " +
      "${filterOptionPlaceHolder} " +
      "ORDER BY creation_instant DESC";
  }

  @Override
  protected String buildFilterOptionClause(EmployeeSearchOptions options, Map<Integer, Object> parameterNameValueMaps) {
    return " ";
  }

  @Override
  protected Employee buildSingleEntityFromResultSet(ResultSet resultSet) {
    return new EmployeeMapper()
      .setResultSetData(resultSet)
      .buildEmployee();
  }

  @Override
  protected List<Employee> buildListEntitiesFromResultSet(ResultSet resultSet) throws SQLException {

    List<Employee> result = new ArrayList<>();

    while (resultSet.next()) {
      Employee employee = buildSingleEntityFromResultSet(resultSet);
      result.add(employee);
    }

    return result;
  }

  @Override
  public void add(Employee employee) {
  //TODO
  }

  @Override
  public void remove(String id) {
  //TODO
  }

  @Override
  protected void applyAdd(Employee employee) {
    //TODO
  }

  @Override
  protected boolean applyUpdate(Employee employee) {
    //TODO
    return false;
  }

  @Override
  protected void applyRemove(Employee employee) {
    //TODO
  }

  @Override
  public Map<String, Employee> findAll() {
    return search(
      new EmployeeSearchOptions.CustomerSearchOptionsBuilder().build()
    ).stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
  }


}
