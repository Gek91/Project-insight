package projectinsight.module.project.rest.customer.data;

import projectinsight.module.project.domain.customer.model.Customer;

public class CustomerSimpleDTO {

  private String id;
  private String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static CustomerSimpleDTO buildDTO(Customer input) {

    if(input == null) {
      return null;
    }

    CustomerSimpleDTO output = new CustomerSimpleDTO();
    output.setId(input.getId());
    output.setName(input.getName());

    return output;
  }
}
