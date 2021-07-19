package projectTemplate.module.project.persistence.customer;

import com.google.inject.Inject;
import projectTemplate.module.app.service.PersistenceService;
import projectTemplate.module.project.domain.customer.CustomerRepository;
import projectTemplate.module.project.domain.customer.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerRepositoryImpl implements CustomerRepository {

  @Inject
  private PersistenceService persistenceService;


  @Override
  public Customer findById(String id) {
    return null;
  }

  @Override
  public void add(Customer customer) {

  }

  @Override
  public void remove(String id) {

  }

  @Override
  public List<Customer> search() {
    return null;
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
            Customer customer = new CustomerMapper().buildCustomer(resultSet);

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
