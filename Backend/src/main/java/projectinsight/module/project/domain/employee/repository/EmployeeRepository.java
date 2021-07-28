package projectinsight.module.project.domain.employee.repository;


import projectinsight.module.app.commons.persistence.Repository;
import projectinsight.module.project.domain.employee.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeRepository extends Repository {

  Employee findForRead(String id);

  void add(Employee employee);
  void remove(String id);

  List<Employee> search(EmployeeSearchOptions options);
  Map<String, Employee> findAll();
}
