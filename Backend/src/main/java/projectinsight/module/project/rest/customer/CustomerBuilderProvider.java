package projectinsight.module.project.rest.customer;

import com.google.inject.Provider;
import projectinsight.module.project.domain.customer.model.CustomerBuilder;

public class CustomerBuilderProvider implements Provider<CustomerBuilder> {

  @Override
  public CustomerBuilder get() {
    return new CustomerBuilder();
  }
}
