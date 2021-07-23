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

}
