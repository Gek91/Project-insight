package projectinsight.module.project.persistence.customer;

import projectinsight.module.app.commons.uow.RepositoryAbstractImpl;
import projectinsight.module.project.domain.customer.CustomerRepository;
import projectinsight.module.project.domain.customer.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomerRepositoryImpl extends RepositoryAbstractImpl<String, Customer> implements CustomerRepository {

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
      "ORDER BY customer.name ASC";
  }

  @Override
  protected Customer buildEntityFromResultSet(ResultSet resultSet) {
    return new CustomerMapper()
      .setResultSetData(resultSet)
      .buildCustomer();
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
    return search().stream().collect(Collectors.toMap(x -> x.getId(), x -> x));
  }

}
