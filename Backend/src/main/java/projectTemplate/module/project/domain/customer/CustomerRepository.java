package projectTemplate.module.project.domain.customer;


import projectTemplate.module.project.domain.customer.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerRepository {

  Customer findForRead(String id);
  Customer findForUpdate(String id);
  void add(Customer customer);
  void remove(String id);
  List<Customer> search();
  Map<String, Customer> findAll();

}
