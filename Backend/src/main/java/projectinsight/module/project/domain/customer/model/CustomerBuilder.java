package projectinsight.module.project.domain.customer.model;

import projectinsight.module.app.commons.UIIDGenerator;
import projectinsight.module.app.commons.validations.ValidationManager;

import java.time.Instant;

public class CustomerBuilder {

  protected String id;
  protected int version;
  protected String name;
  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  public CustomerBuilder() { }

  public CustomerBuilder setName(String name) {
    this.name = name;

    return this;
  }
  public CustomerBuilder setVersion(int version) {
    this.version = version;

    return this;
  }

  public Customer buildCustomer() {

    customerValidations();

    Customer customer = new Customer();

    this.id = UIIDGenerator.generate();
    this.version = 0;
    this.creationInstant = Instant.now();
    this.lastUpdateInstant = this.creationInstant;
    this.deleted = false;

    populateCustomerFields(customer);

    return customer;
  }

  public void editCustomer(Customer customer) {

    customerValidations();

    this.id = customer.id;
    this.creationInstant = customer.creationInstant;
    this.lastUpdateInstant = Instant.now();

    populateCustomerFields(customer);

    customer.setUpdated();
  }

  private void customerValidations() {

    ValidationManager validations = ValidationManager.build();

    validations
      .requiredNotBlank(this.name, "name");

    validations.throwValidationExceptionIfHasErrors();
  }

  protected void populateCustomerFields(Customer customer) {

    customer.id = this.id;
    customer.name = this.name;
    customer.version = this.version;

    customer.creationInstant = this.creationInstant;
    customer.lastUpdateInstant = this.lastUpdateInstant;
    customer.deleted = this.deleted;
  }
}
