package projectinsight.module.project.domain.customer.repository;

import projectinsight.module.app.commons.persistence.SearchOptions;

public class CustomerSearchOptions implements SearchOptions {

  //NO FIELD

  private CustomerSearchOptions () { }

  //builder
  public static class CustomerSearchOptionsBuilder {

    //no fields

    public CustomerSearchOptions build() {

      CustomerSearchOptions options = new CustomerSearchOptions();

      return options;
    }
  }
}
