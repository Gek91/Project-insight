package projectinsight.module.project.domain.employee.model;


import java.time.Instant;

public class Employee {

  protected String id;
  protected String name;
  protected String surname;
  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  public String getId() {
    return id;
  }
  public String getName() {
    return name;
  }
  public String getSurname() {
    return surname;
  }
  public Instant getCreationInstant() {
    return creationInstant;
  }
  public Instant getLastUpdateInstant() {
    return lastUpdateInstant;
  }
  public boolean isDeleted() {
    return deleted;
  }
}
