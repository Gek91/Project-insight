package projectinsight.module.project.domain.project.repository;

import projectinsight.module.app.commons.uow.SearchOptions;

public class ProjectSearchOptions implements SearchOptions {

  private String customerId;

  private ProjectSearchOptions() { };

  public String getCustomerId() {
    return customerId;
  }

  //Builder
  public static class ProjectSearchOptionsBuilder {

    private String customerId;

    public ProjectSearchOptionsBuilder setCustomerId(String customerId) {
      this.customerId = customerId;

      return this;
    }

    public ProjectSearchOptions build() {

      ProjectSearchOptions options = new ProjectSearchOptions();
      options.customerId = this.customerId;

      return options;
    }
  }
}
