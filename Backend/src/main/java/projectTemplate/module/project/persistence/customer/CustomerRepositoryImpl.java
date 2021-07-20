package projectTemplate.module.project.persistence.customer;

import com.google.inject.Inject;
import org.apache.commons.lang3.text.StrSubstitutor;
import projectTemplate.module.app.service.PersistenceService;
import projectTemplate.module.project.domain.customer.CustomerRepository;
import projectTemplate.module.project.domain.customer.model.Customer;
import projectTemplate.module.project.persistence.project.ProjectMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {

  @Inject
  private PersistenceService persistenceService;


  @Override
  public Customer findForUpdate(String id) {

    String query = "" +
      "SELECT * " +
      "FROM customer " +
      "WHERE customer.deleted = false " +
      "AND customer.id = ? " +
      "FOR UPDATE ";

    return find(id, query);
  }

  @Override
  public Customer findForRead(String id) {

    String query = "" +
      "SELECT * " +
      "FROM customer " +
      "WHERE customer.deleted = false " +
      "AND customer.id = ? ";
    // "FOR SHARE ; ";

    return find(id, query);
  }

  private Customer find(String id, String query) {

    Customer result = null;

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, id);
        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {

          if (resultSet.next()) {

            result = new CustomerMapper()
              .setResultSetData(resultSet)
              .buildCustomer();

          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  public void add(Customer customer) {

  }

  @Override
  public void remove(String id) {

  }

  @Override
  public List<Customer> search() {

    List<Customer> result = new ArrayList<>();

    try (Connection connection = persistenceService.getConnection()) {

      String query = "" +
        "SELECT * " +
        "FROM customer " +
        "WHERE deleted = false " +
        "ORDER BY customer.name ASC";

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {
          while (resultSet.next()) {
            result.add(new CustomerMapper().setResultSetData(resultSet).buildCustomer());
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  @Override
  public Map<String, Customer> findAll() {

    Map<String, Customer> result = new HashMap<>();

    try(Connection connection = persistenceService.getConnection()) {

      String query = "" +
        "SELECT * " +
        "FROM customer " +
        "WHERE customer.deleted = false";

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {

          while (resultSet.next()) {

            String id = resultSet.getString("id");
            Customer customer = new CustomerMapper()
              .setResultSetData(resultSet)
              .buildCustomer();

            result.put(id, customer);
          }
        }
      }

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }
}
