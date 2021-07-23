package projectinsight.module.project.domain.employee.repository;

import projectinsight.module.app.commons.uow.SearchOptions;
import projectinsight.module.project.domain.customer.repository.CustomerSearchOptions;

public class EmployeeSearchOptions implements SearchOptions {

  //NO FIELD

  private EmployeeSearchOptions () { }

  //builder
  public static class CustomerSearchOptionsBuilder {

    //no fields

    public EmployeeSearchOptions build() {

      EmployeeSearchOptions options = new EmployeeSearchOptions();

      return options;
    }
  }
}
