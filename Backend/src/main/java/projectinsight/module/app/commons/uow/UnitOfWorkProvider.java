package projectinsight.module.app.commons.uow;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.customer.CustomerRepository;

public class UnitOfWorkProvider implements Provider<UnitOfWork> {

  //TODO inject repository come mappa
  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private PersistenceService persistenceService;

  @Override
  public UnitOfWork get() {
    return new UnitOfWork(persistenceService, customerRepository);
  }
}
