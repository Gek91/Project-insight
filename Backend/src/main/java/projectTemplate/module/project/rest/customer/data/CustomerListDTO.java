package projectTemplate.module.project.rest.customer.data;

import projectTemplate.module.project.domain.customer.model.Customer;

import java.util.List;

public class CustomerListDTO {

  private List<CustomerListElementDTO> customers;

  public List<CustomerListElementDTO> getCustomers() {
    return customers;
  }
  public void setCustomers(List<CustomerListElementDTO> customers) {
    this.customers = customers;
  }


  public static CustomerListDTO buildDTO(List<Customer> customers) {

    CustomerListDTO output = new CustomerListDTO();
    output.setCustomers(CustomerListElementDTO.buildDTO(customers));

    return output;
  }
}
