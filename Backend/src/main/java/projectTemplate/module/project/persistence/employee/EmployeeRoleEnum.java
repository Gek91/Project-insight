package projectTemplate.module.project.persistence.employee;

import java.util.Arrays;
import java.util.Optional;

public enum EmployeeRoleEnum {

  MANAGER(1),
  TECH_LEAD(2),
  DEVELOPER(3);

  private EmployeeRoleEnum(int id) {
    this.id = id;
  }

  private int id;

  public int getId() {
    return this.id;
  }

  public static Optional<EmployeeRoleEnum> getValueById(Integer id) {

    if(id == null) {
      return Optional.empty();
    }

    return Arrays.stream(EmployeeRoleEnum.values())
      .filter(x -> x.getId() == id)
      .findFirst();
  }
}
