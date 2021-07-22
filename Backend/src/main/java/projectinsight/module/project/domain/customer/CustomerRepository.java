package projectinsight.module.project.domain.customer;


import projectinsight.module.app.commons.uow.Repository;
import projectinsight.module.project.domain.customer.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerRepository extends Repository {

  Customer findForRead(String id);
  Customer findForUpdate(String id);
  void add(Customer customer);
  void remove(Customer customer);
  List<Customer> search();
  Map<String, Customer> findAll();

}
