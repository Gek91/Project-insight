package projectinsight.module.project.domain.employee;


import projectinsight.module.project.domain.employee.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository {

  Employee findById(String id);

  void add(Employee employee);
  void remove(String id);

  List<Employee> search();
  Map<String, Employee> findAll();
}
