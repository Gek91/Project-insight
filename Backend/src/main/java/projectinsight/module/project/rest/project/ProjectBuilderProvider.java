package projectinsight.module.project.rest.project;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;
import projectinsight.module.project.domain.project.model.ProjectBuilder;

public class ProjectBuilderProvider implements Provider<ProjectBuilder> {

  @Inject
  private EmployeeRepository employeeRepository;
  @Inject
  private CustomerRepository customerRepository;

  @Override
  public ProjectBuilder get() {
    return new ProjectBuilder(employeeRepository, customerRepository);
  }
}
