package projectinsight.module.project.persistence.customer;

import com.google.inject.Provider;

public class CustomerMapperProvider implements Provider<CustomerMapper> {

  @Override
  public CustomerMapper get() {
    return new CustomerMapper();
  }
}
