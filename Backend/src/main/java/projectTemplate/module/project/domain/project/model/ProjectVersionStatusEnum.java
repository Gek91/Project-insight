package projectTemplate.module.project.domain.project.model;

import java.util.Arrays;
import java.util.Optional;

public enum ProjectVersionStatusEnum {

  RELEASED(1, "Released"),
  SCHEDULED(2, "Scheduled"),
  DELETED(3, "Deleted");

  private int id;
  private String label;

  private ProjectVersionStatusEnum(int id, String label) {
    this.id = id;
    this.label = label;
  }

  public int getId() {
    return id;
  }

  public String getLabel() {
    return label;
  }

  public static Optional<ProjectVersionStatusEnum> getValueById(Integer id) {

    if(id == null) {
      return Optional.empty();
    }

    return Arrays.stream(ProjectVersionStatusEnum.values())
      .filter(x -> x.getId() == id.intValue())
      .findFirst();
  }
}
