package projectinsight.module.project.rest.customer.data;

public class EditCustomerRequestDTO extends CreateCustomerRequestDTO {

  private int currentVersion;

  public int getCurrentVersion() {
    return currentVersion;
  }

  public void setCurrentVersion(int currentVersion) {
    this.currentVersion = currentVersion;
  }
}
