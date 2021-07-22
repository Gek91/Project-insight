package projectinsight.module.project.persistence.customer;

import projectinsight.module.app.commons.uow.RepositoryAbstractImpl;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.customer.repository.CustomerSearchOptions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl extends RepositoryAbstractImpl<String, Customer, CustomerSearchOptions> implements CustomerRepository {

  @Override
  protected String getFindForReadQuery() {
    return "" +
      "SELECT * " +
      "FROM customer " +
      "WHERE customer.deleted = false " +
      "AND customer.id = ? ";
    // "FOR SHARE ; ";
  }

  @Override
  protected String getFindForUpdateQuery() {
    return "" +
      "SELECT * " +
      "FROM customer " +
      "WHERE customer.deleted = false " +
      "AND customer.id = ? " +
      "FOR UPDATE ";
  }

  @Override
  protected String getSearchQuery() {
    return "" +
      "SELECT * " +
      "FROM customer " +
      "WHERE deleted = false " +
      "${filterOptionPlaceHolder} " +
      "ORDER BY customer.name ASC";
  }

  @Override
  protected String buildFilterOptionClause(CustomerSearchOptions options, Map<Integer, Object> parameterNameValueMaps) {
    return " ";
  }

  @Override
  protected Customer buildSingleEntityFromResultSet(ResultSet resultSet) {
    return new CustomerMapper()
      .setResultSetData(resultSet)
      .buildCustomer();
  }

  @Override
  protected List<Customer> buildListEntitiesFromResultSet(ResultSet resultSet) throws SQLException {

    List<Customer> result = new ArrayList<>();

    while (resultSet.next()) {
      Customer customer = buildSingleEntityFromResultSet(resultSet);
      result.add(customer);
    }

    return result;
  }

  @Override
  protected void applyAdd(Customer customer) {

    String query = "" +
      "INSERT INTO customer " +
      "VALUES( ?, ?,  ?, ?, ?, ?) ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, customer.getId());
        statement.setString(2, customer.getName());
        statement.setInt(3, customer.getVersion());
        statement.setTimestamp(4, Timestamp.from(customer.getCreationInstant()));
        statement.setTimestamp(5, Timestamp.from(customer.getLastUpdateInstant()));
        statement.setBoolean(6, customer.isDeleted());

        statement.execute();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected boolean applyUpdate(Customer customer) {

    String query = "" +
      "UPDATE customer " +
      "SET name = ?, version = ?, last_update_instant = ?, deleted = ? " +
      "WHERE id = ? AND version = ? ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, customer.getName());
        statement.setInt(2, customer.getVersion() + 1);
        statement.setTimestamp(3, Timestamp.from(customer.getLastUpdateInstant()));
        statement.setBoolean(4, customer.isDeleted());
        statement.setString(5, customer.getId());
        statement.setInt(6, customer.getVersion());

        return statement.executeUpdate() > 0;
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void applyRemove(Customer customer) {

    String query = "" +
      "UPDATE customer " +
      "SET last_update_instant = ?, deleted = ? " +
      "WHERE id = ? ";

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setTimestamp(1, Timestamp.from(customer.getLastUpdateInstant()));
        statement.setBoolean(2, true);
        statement.setString(3, customer.getId());

        statement.executeUpdate();
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public Map<String, Customer> findAll() {
    return search(
      new CustomerSearchOptions.CustomerSearchOptionsBuilder().build()
    ).stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
  }

}
