package projectinsight.module.project.rest.project.data;

import java.util.List;

public class CreateProjectRequestDTO {

  private String name;
  private String description;
  private String customerId;
  protected List<String> projectManagerIds;
  protected List<String> techLeadIds;
  protected List<String> developersIds;

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public String getCustomerId() {
    return customerId;
  }
  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }
  public List<String> getProjectManagerIds() {
    return projectManagerIds;
  }
  public void setProjectManagerIds(List<String> projectManagerIds) {
    this.projectManagerIds = projectManagerIds;
  }
  public List<String> getTechLeadIds() {
    return techLeadIds;
  }
  public void setTechLeadIds(List<String> techLeadIds) {
    this.techLeadIds = techLeadIds;
  }
  public List<String> getDevelopersIds() {
    return developersIds;
  }
  public void setDevelopersIds(List<String> developersIds) {
    this.developersIds = developersIds;
  }
}
