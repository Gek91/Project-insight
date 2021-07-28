package projectinsight.module.app.commons.persistence;

public interface Repository {

  //TODO vorrei fosse non pubblico ma solo utilizzabile nell'unit of work
  void saveChanges();
  void resetEntities();
}
