package projectinsight.module.app.commons.uow;

import com.google.inject.Inject;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.persistence.customer.CustomerMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RepositoryAbstractImpl<K, T extends Entity<K>> implements Repository {

  @Inject
  protected PersistenceService persistenceService;

  protected Map<OperationEnum, Map<K, T>> entities;

  protected abstract String getFindForReadQuery();
  protected abstract String getFindForUpdateQuery();
  protected abstract String getSearchQuery();
  protected abstract T buildEntityFromResultSet(ResultSet resultSet);

  protected abstract void applyAdd(T entity);
  protected abstract boolean applyUpdate(T entity);
  protected abstract void applyRemove(T entity);


  public RepositoryAbstractImpl() {
    resetEntities();
  }

  public void resetEntities() {
    this.entities = new HashMap<>();
    this.entities.put(OperationEnum.GET, new HashMap<>());
    this.entities.put(OperationEnum.ADD, new HashMap<>());
    this.entities.put(OperationEnum.REMOVE, new HashMap<>());
  }

  public T findForRead(K id) {

    T entry = entities.get(OperationEnum.GET).get(id);

    if(entry == null) {
      entry = entities.get(OperationEnum.ADD).get(id);
    }

    if(entry == null) {
      entry = find(id, getFindForReadQuery());
    }

    return entry;
  }

  public T findForUpdate(K id) {
    return find(id, getFindForUpdateQuery());
  }

  private T find(K id, String query) {

    T result = null;

    try (Connection connection = persistenceService.getConnection()) {

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setObject(1, id);
        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {

          if (resultSet.next()) {

            result = buildEntityFromResultSet(resultSet);

            this.entities.get(OperationEnum.GET).put(result.getId(), result);
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  public List<T> search() {

    List<T> result = new ArrayList<>();

    try (Connection connection = persistenceService.getConnection()) {

      String query = getSearchQuery();

      try (PreparedStatement statement = connection.prepareStatement(query)) {

        statement.execute();

        try (ResultSet resultSet = statement.getResultSet()) {
          while (resultSet.next()) {
            T entity = buildEntityFromResultSet(resultSet);
            result.add(entity);
            this.entities.get(OperationEnum.GET).put(entity.getId(), entity);
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return result;
  }

  public void add(T entry) {
    entities.get(OperationEnum.ADD).put(entry.getId(), entry);
  }

  public void remove(T entry) {
    entities.get(OperationEnum.REMOVE).put(entry.getId(), entry);
    entities.get(OperationEnum.GET).remove(entry.getId());
    entities.get(OperationEnum.ADD).remove(entry.getId());
  }

  public void saveChanges() {

    //TODO batch operation
    entities.get(OperationEnum.ADD).values().forEach(
      entity -> applyAdd(entity)
    );

    entities.get(OperationEnum.REMOVE).values().forEach(
      entity -> applyRemove(entity)
    );

    entities.get(OperationEnum.GET).values().stream().filter(x -> x.isUpdated()).forEach(
      entity -> applyUpdate(entity)
    );
  }

}
