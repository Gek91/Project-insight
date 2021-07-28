package projectinsight.module.app.commons.persistence;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.service.PersistenceService;

import java.util.Map;

public class UnitOfWorkProvider implements Provider<UnitOfWork> {

  @Inject
  private PersistenceService persistenceService;

  //Inject all repositories in unit of work
  @Inject
  private Map<Class, Repository> repositories;

  @Override
  public UnitOfWork get() {
    return new UnitOfWork(persistenceService, repositories);
  }
}
