package projectTemplate.module.project.domain.project.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProjectTeam {

  private List<String> projectManagerIds;
  private List<String> techLeadIds;
  private List<String> developersIds;

  public ProjectTeam() {
    this.projectManagerIds = new ArrayList<>();
    this.techLeadIds = new ArrayList<>();
    this.developersIds = new ArrayList<>();
  }

  protected ProjectTeam(List<String> projectManagerIds, List<String> techLeadIds, List<String> developersIds) {
    this();

    if(projectManagerIds != null) {
      this.projectManagerIds.addAll(projectManagerIds);
    }
    if(techLeadIds != null) {
      this.techLeadIds.addAll(techLeadIds);
    }
    if(developersIds != null) {
      this.developersIds.addAll(developersIds);
    }
  }

  public List<String> getProjectManagerIds() {
    return Collections.unmodifiableList(projectManagerIds);
  }
  public List<String> getTechLeadIds() {
    return Collections.unmodifiableList(techLeadIds);
  }
  public List<String> getDevelopersIds() {
    return Collections.unmodifiableList(developersIds);
  }

}
