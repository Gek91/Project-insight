package projectinsight.module.project.persistence.employee;

import com.google.inject.Provider;

public class EmployeeMapperProvider implements Provider<EmployeeMapper> {

  @Override
  public EmployeeMapper get() {
    return new EmployeeMapper();
  }
}
