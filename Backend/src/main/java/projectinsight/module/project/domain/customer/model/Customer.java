package projectinsight.module.project.domain.customer.model;


import projectinsight.module.app.commons.persistence.Entity;

import java.time.Instant;

public class Customer extends Entity<String> {

  protected String id;
  protected int version;
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
  public int getVersion() { return version; }
  public boolean isDeleted() {
    return deleted;
  }
}
