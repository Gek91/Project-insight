package projectinsight.module.project.rest.project;

import com.google.inject.Inject;
import com.google.inject.Provider;
import projectinsight.module.app.commons.BaseRestApiImpl;
import projectinsight.module.app.commons.exception.EntityNotExistException;
import projectinsight.module.app.commons.persistence.UnitOfWork;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectBuilder;
import projectinsight.module.project.domain.project.model.ProjectVersion;
import projectinsight.module.project.domain.project.model.ProjectVersionBuilder;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.rest.project.data.*;

import javax.ws.rs.PathParam;
import java.util.List;

public class ProjectRestApiImpl extends BaseRestApiImpl implements ProjectRestApi {

  @Inject
  private Provider<UnitOfWork> unitOfWorkProvider;

  @Inject
  private Provider<ProjectBuilder> projectBuilderProvider;

  @Inject
  private Provider<ProjectVersionBuilder> projectVersionProvider;



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

  @Override
  public ProjectDetailDTO createProject(CreateProjectRequestDTO request) {

    ProjectDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);
      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);
      EmployeeRepository employeeRepository = (EmployeeRepository) unitOfWork.getRepository(Employee.class);

      Project project = buildProjectFromRequest(request);

      projectRepository.add(project);

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

  private Project buildProjectFromRequest(CreateProjectRequestDTO request) {

    return projectBuilderProvider.get()
        .setName(request.getName())
        .setDescription(request.getDescription())
        .setCustomerId(request.getCustomerId())
        .setProjectManagerIds(request.getProjectManagerIds())
        .setDevelopersIds(request.getDevelopersIds())
        .setTechLeadIds(request.getTechLeadIds())
        .buildProject();
  }

  @Override
  public ProjectDetailDTO editProject(@PathParam("id") String id, EditProjectRequestDTO request) {

    ProjectDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);
      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);
      EmployeeRepository employeeRepository = (EmployeeRepository) unitOfWork.getRepository(Employee.class);

      Project project = projectRepository.findForUpdate(id);

      if(project == null) {
        throw new EntityNotExistException();
      }

      updateProjectFromRequest(project, request);

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

  private void updateProjectFromRequest(Project project, EditProjectRequestDTO request) {

    projectBuilderProvider.get()
      .setName(request.getName())
      .setDescription(request.getDescription())
      .setCustomerId(request.getCustomerId())
      .setProjectManagerIds(request.getProjectManagerIds())
      .setDevelopersIds(request.getDevelopersIds())
      .setTechLeadIds(request.getTechLeadIds())
      .setVersion(request.getCurrentVersion())
      .editProject(project);
  }

  @Override
  public ProjectDetailDTO createProjectVersion(@PathParam("id") String id, CreateProjectVersionRequestDTO request) {

    ProjectDetailDTO response = null;

    UnitOfWork unitOfWork = unitOfWorkProvider.get();

    try {

      unitOfWork.beginUnitOfWorkTransaction();

      ProjectRepository projectRepository = (ProjectRepository) unitOfWork.getRepository(Project.class);
      CustomerRepository customerRepository = (CustomerRepository) unitOfWork.getRepository(Customer.class);
      EmployeeRepository employeeRepository = (EmployeeRepository) unitOfWork.getRepository(Employee.class);

      Project project = projectRepository.findForUpdate(id);

      ProjectVersion version = buildProjectVersionFromRequest(request);

      project.addProjectVersion(version);

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

  private ProjectVersion buildProjectVersionFromRequest(CreateProjectVersionRequestDTO request) {

    return projectVersionProvider.get()
        .setMajorVersion(request.getMajorVersion())
        .setMinorVersion(request.getMinorVersion())
        .setPatchVersion(request.getPatchVersion())
        .setNote(request.getNote())
        .setVersionLabel(request.getVersionLabel())
        .setReleaseDate(request.getReleaseDate())
        .setStatusId(request.getStatusId())
        .buildProjectVersion();
  }

}
