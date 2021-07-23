package projectinsight.module.project.rest.customer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.commons.BaseRestApiImpl;
import projectinsight.module.app.commons.exception.EntityNotExistException;
import projectinsight.module.app.commons.uow.UnitOfWork;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.customer.repository.CustomerSearchOptions;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.domain.project.repository.ProjectSearchOptions;
import projectinsight.module.project.rest.customer.data.CreateCustomerRequestDTO;
import projectinsight.module.project.rest.customer.data.CustomerDetailDTO;
import projectinsight.module.project.rest.customer.data.CustomerListDTO;
import projectinsight.module.project.rest.customer.data.EditCustomerRequestDTO;

import java.util.Collections;
import java.util.List;

public class CustomerRestApiImpl extends BaseRestApiImpl implements CustomerRestApi {

  @Inject
  private Provider<UnitOfWork> unitOfWorkProvider;

  @Override
  public CustomerListDTO getCustomerList() {

    CustomerListDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);

      List<Customer> customers = customerRepository.search(
        new CustomerSearchOptions.CustomerSearchOptionsBuilder().build()
      );

      response = CustomerListDTO.buildDTO(customers);

      unitOfWork.commitUnitOfWorkTransaction();

    } catch (Exception e) {
      unitOfWork.handleExceptionAndRollbackJdbcTransaction(e);
    }

    return response;
  }

  @Override
  public CustomerDetailDTO getCustomerDetail(String id) {

    CustomerDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);

      Customer customer = customerRepository.findForRead(id);

      if(customer == null) {
        throw new EntityNotExistException();
      }

      ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);

      response = CustomerDetailDTO.buildDTO(customer, getCustomerProjects(projectRepository, customer.getId()));

      unitOfWork.commitUnitOfWorkTransaction();

    } catch (Exception e) {
      unitOfWork.handleExceptionAndRollbackJdbcTransaction(e);
    }

    return response;
  }

  private List<Project> getCustomerProjects(ProjectRepository projectRepository, String customerId) {
    ProjectSearchOptions projectSearchOptions = new ProjectSearchOptions
      .ProjectSearchOptionsBuilder()
      .setCustomerId(customerId)
      .build();

   return projectRepository.search(projectSearchOptions);
  }

  @Override
  public CustomerDetailDTO createCustomer(CreateCustomerRequestDTO request) {

    CustomerDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      Customer customer = buildCustomerFromRequest(request);

      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);

      customerRepository.add(customer);

      response = CustomerDetailDTO.buildDTO(customer, Collections.emptyList());

      unitOfWork.commitUnitOfWorkTransaction();

    } catch (Exception e) {
      unitOfWork.handleExceptionAndRollbackJdbcTransaction(e);
    }

    return response;
  }

  private Customer buildCustomerFromRequest(CreateCustomerRequestDTO request) {

    return Customer.getBuilder()
      .setName(request.getName())
      .buildCustomer();
  }

  @Override
  public CustomerDetailDTO editCustomer(String id, EditCustomerRequestDTO request) {

    CustomerDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);

      Customer customer = customerRepository.findForUpdate(id);

      if(customer == null) {
        throw new EntityNotExistException();
      }

      editCustomerFromRequest(customer, request);

      ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);

      response = CustomerDetailDTO.buildDTO(customer, getCustomerProjects(projectRepository, customer.getId()));

      unitOfWork.commitUnitOfWorkTransaction();

    } catch (Exception e) {
      unitOfWork.handleExceptionAndRollbackJdbcTransaction(e);
    }

    return response;
  }

  public void editCustomerFromRequest(Customer customer, EditCustomerRequestDTO request) {

    Customer.getBuilder()
      .setName(request.getName())
      .setVersion(request.getCurrentVersion())
      .editCustomer(customer);
  }
}
