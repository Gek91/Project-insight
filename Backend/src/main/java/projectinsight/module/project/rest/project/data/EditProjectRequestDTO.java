package projectinsight.module.project.rest.project.data;

public class EditProjectRequestDTO extends CreateProjectRequestDTO {

  private int currentVersion;

  public int getCurrentVersion() {
    return currentVersion;
  }

  public void setCurrentVersion(int currentVersion) {
    this.currentVersion = currentVersion;
  }
}
