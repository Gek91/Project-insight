package projectTemplate.module.project.persistence.customer;

import projectTemplate.module.project.domain.customer.model.Customer;
import projectTemplate.module.project.domain.customer.model.CustomerBuilder;
import projectTemplate.module.project.persistence.project.ProjectMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper extends CustomerBuilder {

  public CustomerMapper setResultSetData(ResultSet resultSet) {

    try {
      this.id = resultSet.getString("id");
      this.name = resultSet.getString("name");
      this.creationInstant = resultSet.getTimestamp("creation_instant").toInstant();
      this.lastUpdateInstant = resultSet.getTimestamp("last_update_instant").toInstant();
      this.deleted = resultSet.getBoolean("deleted");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return this;
  }

  public Customer buildCustomer() {

    Customer customer = new Customer();
    populateCustomerFields(customer);

    return customer;
  }
}
