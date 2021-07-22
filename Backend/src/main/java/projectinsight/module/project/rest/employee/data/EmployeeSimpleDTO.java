package projectinsight.module.project.rest.employee.data;

import projectinsight.module.project.domain.employee.model.Employee;

public class EmployeeSimpleDTO {

  private String id;
  private String name;
  private String surname;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }


  public static EmployeeSimpleDTO buildDTO(Employee input) {

    if(input == null) {
      return null;
    }

    EmployeeSimpleDTO output = new EmployeeSimpleDTO();
    output.setId(input.getId());
    output.setName(input.getName());
    output.setSurname(input.getSurname());

    return output;
  }
}
