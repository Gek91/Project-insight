package projectinsight.module.project;

import com.google.inject.AbstractModule;

import projectinsight.module.app.commons.uow.UnitOfWork;
import projectinsight.module.app.commons.uow.UnitOfWorkProvider;
import projectinsight.module.project.domain.customer.CustomerRepository;
import projectinsight.module.project.domain.employee.EmployeeRepository;
import projectinsight.module.project.domain.project.model.ProjectBuilder;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.persistence.customer.CustomerRepositoryImpl;
import projectinsight.module.project.persistence.employee.EmployeeRepositoryImpl;
import projectinsight.module.project.persistence.project.ProjectRespositoryImpl;
import projectinsight.module.project.rest.customer.CustomerRestApiImpl;
import projectinsight.module.project.rest.project.ProjectRestApiImpl;

public class ProjectGuiceModule extends AbstractModule {

	@Override
	protected void configure() {


	  //Rest API
	  bind(ProjectRestApiImpl.class);
	  bind(CustomerRestApiImpl.class);

	  //Services

    bind(ProjectBuilder.class);
    bind(UnitOfWork.class).toProvider(UnitOfWorkProvider.class);

    //Repository
    bind(ProjectRepository.class).to(ProjectRespositoryImpl.class).asEagerSingleton();
    bind(EmployeeRepository.class).to(EmployeeRepositoryImpl.class).asEagerSingleton();
    bind(CustomerRepository.class).to(CustomerRepositoryImpl.class).asEagerSingleton();

  }

}
