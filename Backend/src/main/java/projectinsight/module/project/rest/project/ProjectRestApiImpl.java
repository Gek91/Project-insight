package projectinsight.module.project.rest.project;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.commons.BaseRestApiImpl;
import projectinsight.module.app.commons.exception.EntityNotExistException;
import projectinsight.module.app.commons.uow.UnitOfWork;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.rest.project.data.ProjectDetailDTO;
import projectinsight.module.project.rest.project.data.ProjectListDTO;

import java.util.List;

public class ProjectRestApiImpl extends BaseRestApiImpl implements ProjectRestApi {

  @Inject
  private Provider<UnitOfWork> unitOfWorkProvider;

  @Override
  public ProjectListDTO getProjectList() {

    ProjectListDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);
      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);

      List<Project> projects = projectRepository.search(null);

      response = ProjectListDTO.buildDTO(
        projects,
        customerRepository.findAll()
      );

      unitOfWork.commitUnitOfWorkTransaction();

    } catch (Exception e) {
      unitOfWork.handleExceptionAndRollbackJdbcTransaction(e);
    }

    return response;
  }

  @Override
  public ProjectDetailDTO getProjectDetail(String id) {

    ProjectDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);
    CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);
    EmployeeRepository employeeRepository = (EmployeeRepository) unitOfWork.getRepository(Employee.class);

    try {
      unitOfWork.beginUnitOfWorkTransaction();

      Project project = projectRepository.findForRead(id);

      if(project == null) {
        throw new EntityNotExistException();
      }

      response = ProjectDetailDTO.buildDTO(
        project,
        customerRepository.findAll(),
        employeeRepository.findAll()
      );

      unitOfWork.commitUnitOfWorkTransaction();

    } catch (Exception e) {
      unitOfWork.handleExceptionAndRollbackJdbcTransaction(e);
    }

    return response;
  }

}
