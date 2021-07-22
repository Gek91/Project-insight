package projectinsight.module.app.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectinsight.module.app.service.PersistenceService;

import java.sql.SQLException;

public  abstract class BaseRestApiImpl {

  private transient Logger logger = LoggerFactory.getLogger(this.getClass());

  protected Logger getLogger() {
    return logger;
  }

  protected void handleExceptionAndRollbackJdbcTransaction(Exception e, PersistenceService persistenceService) {

    rollbackJdbcTransaction(persistenceService);

    if (e instanceof RuntimeException) {
      throw (RuntimeException) e;
    } else {
      throw new RuntimeException(e);
    }

  }

  protected void rollbackJdbcTransaction(PersistenceService persistenceService) {
    try {
      persistenceService.rollbackJdbcTransaction();
    } catch (SQLException e1) {
      getLogger().error("Cannot rollback JDBC transaction", e1);
    }
  }
}
