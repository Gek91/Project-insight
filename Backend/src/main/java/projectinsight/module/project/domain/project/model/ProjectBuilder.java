package projectinsight.module.project.domain.project.model;

import com.google.inject.Inject;
import projectinsight.module.app.commons.UIIDGenerator;
import projectinsight.module.app.commons.validations.ValidationErrorTypeEnum;
import projectinsight.module.app.commons.validations.ValidationManager;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.customer.CustomerRepository;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.employee.EmployeeRepository;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class ProjectBuilder {

  @Inject
  private EmployeeRepository employeeRepository;
  @Inject
  private CustomerRepository customerRepository;

  protected String name;
  protected String description;
  protected List<String> projectManagerIds;
  protected List<String> techLeadIds;
  protected List<String> developersIds;
  protected String customerId;
  protected List<ProjectVersion> versions;

  protected String id;
  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  protected ProjectBuilder() {
    this.projectManagerIds = new ArrayList<>();
    this.techLeadIds = new ArrayList<>();
    this.developersIds = new ArrayList<>();
  }

  public ProjectBuilder setName(String name) {
    this.name = name;
    return this;
  }
  public ProjectBuilder setProjectManagerIds(List<String> projectManagerIds) {
     if(projectManagerIds != null) {
       this.projectManagerIds.addAll(projectManagerIds);
     }
    return this;
  }
  public ProjectBuilder setTechLeadIds(List<String> techLeadIds) {
    if(techLeadIds != null) {
      this.techLeadIds.addAll(techLeadIds);
    }
    return this;
  }
  public ProjectBuilder setDevelopersIds(List<String> developersIds) {
    if(developersIds != null) {
      this.developersIds.addAll(developersIds);
    }
    return this;
  }
  public ProjectBuilder setCustomerId(String customerId) {
    this.customerId = customerId;
    return this;
  }
  public ProjectBuilder setDescription(String description) {
    this.description = description;

    return this;
  }

  public Project buildProject() {

    projectValidations();

    Project project = new Project();

    this.id = UIIDGenerator.generate();
    this.creationInstant = Instant.now();
    this.lastUpdateInstant = this.creationInstant;
    this.deleted = false;

    populateProjectFields(project);

    return project;
  }

  public void editProject(Project project) {

    projectValidations();

    this.id = project.id;
    this.creationInstant = project.creationInstant;
    this.lastUpdateInstant = Instant.now();

    populateProjectFields(project);
  }

  private void projectValidations() {

    Map<String, Employee> employeesMap = employeeRepository.findAll();
    Map<String, Customer> customersMap = customerRepository.findAll();

    ValidationManager validations = ValidationManager.build();

    validations
      .requiredNotBlank(this.name, "name")
      .required(this.customerId, "customerId");

    Set<String> idsSet = new HashSet<>();
    for(int i = 0 ; i < this.projectManagerIds.size() ; i++) {
      String id = this.projectManagerIds.get(i);
      validations.validate(!employeesMap.containsKey(id) &&
        !idsSet.contains(id), "projectManagerIds["+ i +"]", ValidationErrorTypeEnum.INVALID);
      idsSet.add(id);
    }

    idsSet = new HashSet<>();
    for(int i = 0 ; i <  techLeadIds.size() ; i++) {
      String id = this.techLeadIds.get(i);
      validations.validate(!employeesMap.containsKey(id) &&
        !idsSet.contains(id), "techLeadIds["+ i +"]", ValidationErrorTypeEnum.INVALID);
      idsSet.add(id);
    }

    idsSet = new HashSet<>();
    for(int i = 0 ; i < developersIds.size() ; i++) {
      String id = this.developersIds.get(i);
      validations.validate(!employeesMap.containsKey(id) &&
        !idsSet.contains(id), "developersIds["+ i +"]", ValidationErrorTypeEnum.INVALID);
      idsSet.add(id);
    }

    if(this.customerId != null) {
      validations.validate(!customersMap.containsKey(customerId), "customerId", ValidationErrorTypeEnum.INVALID);
    }

    validations.throwValidationExceptionIfHasErrors();
  }

  protected void populateProjectFields(Project project) {

    project.name = this.name;
    project.description = this.description;
    project.team = new ProjectTeam(this.projectManagerIds, this.techLeadIds, this.developersIds);
    project.customerId = this.customerId;

    project.id = this.id;
    project.creationInstant = this.creationInstant;
    project.lastUpdateInstant = this.lastUpdateInstant;
    project.deleted = this.deleted;
    if (this.versions != null) {
      project.versions.putAll(
        this.versions.stream().collect(Collectors.toMap(x -> x.getProjectVersionString(), x -> x))
      );
    }
  }
}
