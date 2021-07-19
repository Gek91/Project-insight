package projectTemplate.module.project.domain.customer.model;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import projectTemplate.module.app.commons.UIIDGenerator;
import projectTemplate.module.app.commons.validations.ValidationManager;

import java.time.Instant;
import java.util.UUID;

public class CustomerBuilder {

  protected String id;
  protected String name;
  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  protected CustomerBuilder() { }

  public CustomerBuilder setId(String id) {
    this.id = id;

    return this;
  }
  public CustomerBuilder setName(String name) {
    this.name = name;

    return this;
  }

  public Customer buildCustomer() {

    customerValidations();

    Customer customer = new Customer();

    this.id = UIIDGenerator.generate();
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

    customer.creationInstant = this.creationInstant;
    customer.lastUpdateInstant = this.lastUpdateInstant;
    customer.deleted = this.deleted;
  }
}
