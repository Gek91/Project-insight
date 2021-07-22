package projectinsight.module.app.commons.uow;

import java.time.Instant;

public abstract class Entity<T> {

  private boolean updated;

  public abstract T getId();
  public abstract Instant getLastUpdateInstant();

  public void setUpdated() {
    this.updated = true;
  }
  protected boolean isUpdated() {
    return this.updated;
  }


}
