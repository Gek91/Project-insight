package projectinsight.module.project.persistence.project;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;

public class ProjectMapperProvider implements Provider<ProjectMapper> {

  @Inject
  private EmployeeRepository employeeRepository;
  @Inject
  private CustomerRepository customerRepository;

  @Override
  public ProjectMapper get() {
    return new ProjectMapper(employeeRepository, customerRepository);
  }
}
