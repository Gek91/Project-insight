package projectinsight.module.app.commons.uow;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.customer.CustomerRepository;
import projectinsight.module.project.domain.customer.model.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UnitOfWork {

  private transient Logger logger = LoggerFactory.getLogger(this.getClass());

  protected Logger getLogger() {
    return logger;
  }

  private PersistenceService persistenceService;
  private Map<Class, Repository> repositories;

  //TODO rendere più esplicita la necessità di far partire la transizione da begin (evitare due step creazione e begin)
  protected UnitOfWork(PersistenceService persistenceService, CustomerRepository customerRepository) {
    repositories = new HashMap<>();
    repositories.put(Customer.class, customerRepository);

    this.persistenceService = persistenceService;
  }

  public void beginUnitOfWorkTransaction() throws SQLException {

    Connection connection = persistenceService.getConnection();

    connection.setAutoCommit(false);
    connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
  }

  public void commitUnitOfWorkTransaction() throws SQLException {

    saveChanges();

    Connection connection = persistenceService.getConnection();

    try {
      connection.commit();
    } finally {
      connection.setAutoCommit(true);
    }
  }

  public void handleExceptionAndRollbackJdbcTransaction(Exception e) {

    rollbackJdbcTransaction();

    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    } else {
      throw new RuntimeException(e);
    }

  }

  public void rollbackJdbcTransaction() {
    try {
      resetEntities();

      Connection connection = persistenceService.getConnection();

      try {
        connection.rollback();
      } finally {
        connection.setAutoCommit(true);
      }
    } catch (SQLException e1) {
      getLogger().error("Cannot rollback JDBC transaction", e1);
    }
  }

  public Repository getRepository(Class clazz) {
    return this.repositories.get(clazz);
  }

  public void saveChanges() {
    this.repositories.values().forEach(repository -> repository.saveChanges());
    resetEntities();
  }

  private void resetEntities() {
    this.repositories.values().forEach(repository -> repository.resetEntities());
  }

}
