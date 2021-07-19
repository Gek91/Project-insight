package projectTemplate.module.project.domain.customer.model;


import java.time.Instant;

public class Customer {

  protected String id;
  protected String name;
  protected Instant creationInstant;
  protected Instant lastUpdateInstant;
  protected boolean deleted;

  public String getId() {
    return id;
  }
  public String getName() {
    return name;
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
