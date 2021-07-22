package projectinsight.module.project.rest.project;

import com.google.inject.Inject;
import projectinsight.module.app.commons.BaseRestApiImpl;
import projectinsight.module.app.commons.exception.EntityNotExistException;
import projectinsight.module.app.service.PersistenceService;
import projectinsight.module.project.domain.customer.CustomerRepository;
import projectinsight.module.project.domain.employee.EmployeeRepository;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.rest.project.data.ProjectDetailDTO;
import projectinsight.module.project.rest.project.data.ProjectListDTO;

import java.util.List;

public class ProjectRestApiImpl extends BaseRestApiImpl implements ProjectRestApi {

  @Inject
  private PersistenceService persistenceService;

  @Inject
  private ProjectRepository projectRepository;

  @Inject
  private CustomerRepository customerRepository;

  @Inject
  private EmployeeRepository employeeRepository;



  @Override
  public ProjectListDTO getProjectList() {

    ProjectListDTO response = null;

    try {

      persistenceService.beginJdbcTransaction();

      List<Project> projects = projectRepository.search(null);

      response = ProjectListDTO.buildDTO(
        projects,
        customerRepository.findAll()
      );

      persistenceService.commitJdbcTransaction();

    } catch (Exception e) {
      handleExceptionAndRollbackJdbcTransaction(e, persistenceService);
    }

    return response;
  }

  @Override
  public ProjectDetailDTO getProjectDetail(String id) {

    ProjectDetailDTO response = null;

    try {
      persistenceService.beginJdbcTransaction();

      Project project = projectRepository.findForRead(id);

      if(project == null) {
        throw new EntityNotExistException();
      }

      response = ProjectDetailDTO.buildDTO(
        project,
        customerRepository.findAll(),
        employeeRepository.findAll()
      );

      persistenceService.commitJdbcTransaction();

    } catch (Exception e) {
      handleExceptionAndRollbackJdbcTransaction(e, persistenceService);
    }

    return response;
  }

}
