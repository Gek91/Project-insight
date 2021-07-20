package projectTemplate.module.project.rest.customer.data;

import projectTemplate.module.project.domain.customer.model.Customer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CustomerListElementDTO {

  private String id;
  private String name;
  private long lastUpdateInstant;

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
  public long getLastUpdateInstant() {
    return lastUpdateInstant;
  }
  public void setLastUpdateInstant(long lastUpdateInstant) {
    this.lastUpdateInstant = lastUpdateInstant;
  }


  public static List<CustomerListElementDTO> buildDTO(List<Customer> input) {

    List<CustomerListElementDTO> output = new ArrayList<>();

    if(input != null) {
      input.forEach(element -> {
        CustomerListElementDTO elementDTO = buildDTO(element);

        if(elementDTO != null) {
          output.add(elementDTO);
        }
      });
    }

    return output;
  }

  public static CustomerListElementDTO buildDTO(Customer input) {

    if(input == null) {
      return null;
    }

    CustomerListElementDTO output = new CustomerDetailDTO();
    populateDTOFields(output, input);

    return output;
  }

  protected static void populateDTOFields(CustomerListElementDTO output, Customer input) {

    output.setId(input.getId());
    output.setName(input.getName());
    output.setLastUpdateInstant(input.getLastUpdateInstant().toEpochMilli());
  }
}
