package projectTemplate.module.project;

import com.google.inject.AbstractModule;

import projectTemplate.module.project.domain.customer.CustomerRepository;
import projectTemplate.module.project.domain.employee.EmployeeRepository;
import projectTemplate.module.project.domain.project.model.ProjectBuilder;
import projectTemplate.module.project.domain.project.repository.ProjectRepository;
import projectTemplate.module.project.persistence.customer.CustomerRepositoryImpl;
import projectTemplate.module.project.persistence.employee.EmployeeRepositoryImpl;
import projectTemplate.module.project.persistence.project.ProjectRespositoryImpl;
import projectTemplate.module.project.rest.customer.CustomerRestApiImpl;
import projectTemplate.module.project.rest.project.ProjectRestApiImpl;

public class ProjectGuiceModule extends AbstractModule {

	@Override
	protected void configure() {


	  //Rest API
	  bind(ProjectRestApiImpl.class);
	  bind(CustomerRestApiImpl.class);

	  //Services

    bind(ProjectBuilder.class);

    //Repository
    bind(ProjectRepository.class).to(ProjectRespositoryImpl.class).asEagerSingleton();
    bind(EmployeeRepository.class).to(EmployeeRepositoryImpl.class).asEagerSingleton();
    bind(CustomerRepository.class).to(CustomerRepositoryImpl.class).asEagerSingleton();

	}

}
