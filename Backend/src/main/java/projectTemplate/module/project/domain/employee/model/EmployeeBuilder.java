package projectTemplate.module.project.domain.employee.model;

import projectTemplate.module.app.commons.UIIDGenerator;
import projectTemplate.module.app.commons.validations.ValidationManager;

import java.time.Instant;

public class EmployeeBuilder {

  protected String id;
  protected String name;
  protected String surname;
  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  protected EmployeeBuilder() {  }


  public EmployeeBuilder setId(String id) {
    this.id = id;

    return this;
  }
  public EmployeeBuilder setName(String name) {
    this.name = name;

    return this;
  }
  public EmployeeBuilder setSurname(String surname) {
    this.surname = surname;

    return this;
  }


  public Employee buildEmployee() {

    employeeValidations();

    Employee employee = new Employee();

    this.id = UIIDGenerator.generate();
    this.creationInstant = Instant.now();
    this.lastUpdateInstant = this.creationInstant;
    this.deleted = false;

    populateEmployeeFields(employee);

    return employee;
  }

  public void editEmployee(Employee employee) {

    employeeValidations();

    this.id = employee.id;
    this.creationInstant = employee.creationInstant;
    this.lastUpdateInstant = Instant.now();

    populateEmployeeFields(employee);
  }

  private void employeeValidations() {

    ValidationManager validations = ValidationManager.build();

    validations
      .requiredNotBlank(this.name, "name")
      .requiredNotBlank(this.surname, "surname");

    validations.throwValidationExceptionIfHasErrors();
  }

  protected void populateEmployeeFields(Employee employee) {

    employee.id = this.id;
    employee.name = this.name;
    employee.surname = this.surname;

    employee.creationInstant = this.creationInstant;
    employee.lastUpdateInstant = this.lastUpdateInstant;
    employee.deleted = this.deleted;
  }
}
