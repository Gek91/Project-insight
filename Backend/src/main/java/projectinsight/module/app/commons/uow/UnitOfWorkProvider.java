package projectinsight.module.app.commons.uow;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.service.PersistenceService;

import java.util.Map;

public class UnitOfWorkProvider implements Provider<UnitOfWork> {

  //TODO inject repository come mappa
  @Inject
  private PersistenceService persistenceService;

  @Inject
  private Map<Class, Repository> repositories;

  @Override
  public UnitOfWork get() {
    return new UnitOfWork(persistenceService, repositories);
  }
}
