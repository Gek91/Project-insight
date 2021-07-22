package projectinsight.module.project.rest.project.data;

import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.rest.customer.data.CustomerSimpleDTO;

import java.util.Map;

public abstract class AbstractProjectDTO {

  private String id;
  private String name;
  private String description;
  private CustomerSimpleDTO customer;
  private long creationInstant;
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
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public CustomerSimpleDTO getCustomer() {
    return customer;
  }
  public void setCustomer(CustomerSimpleDTO customer) {
    this.customer = customer;
  }
  public long getCreationInstant() {
    return creationInstant;
  }
  public void setCreationInstant(long creationInstant) {
    this.creationInstant = creationInstant;
  }
  public long getLastUpdateInstant() {
    return lastUpdateInstant;
  }
  public void setLastUpdateInstant(long lastUpdateInstant) {
    this.lastUpdateInstant = lastUpdateInstant;
  }

  protected static void populateDTOFields(AbstractProjectDTO output, Project input, Map<String, Customer> customerMap) {
    output.setCustomer(CustomerSimpleDTO.buildDTO(customerMap.get(input.getCustomerId())));
    output.setDescription(input.getDescription());
    output.setId(input.getId());
    output.setName(input.getName());
    output.setCreationInstant(input.getCreationInstant().toEpochMilli());
    output.setLastUpdateInstant(input.getLastUpdateInstant().toEpochMilli());
  }
}
