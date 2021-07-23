package projectinsight.module.project;

import com.google.inject.AbstractModule;

import com.google.inject.multibindings.MapBinder;
import projectinsight.module.app.commons.uow.Repository;
import projectinsight.module.app.commons.uow.UnitOfWork;
import projectinsight.module.app.commons.uow.UnitOfWorkProvider;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectBuilder;
import projectinsight.module.project.rest.project.ProjectBuilderProvider;
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

    //Providers
    bind(ProjectBuilder.class).toProvider(ProjectBuilderProvider.class);
    bind(UnitOfWork.class).toProvider(UnitOfWorkProvider.class);

    //Repository
    bind(ProjectRepository.class).to(ProjectRespositoryImpl.class).asEagerSingleton();
    bind(EmployeeRepository.class).to(EmployeeRepositoryImpl.class).asEagerSingleton();
    bind(CustomerRepository.class).to(CustomerRepositoryImpl.class).asEagerSingleton();

    //map repository in unit of work
    MapBinder<Class, Repository> unitOfWorkRepositoryMap = MapBinder.newMapBinder(binder(), Class.class, Repository.class);
    unitOfWorkRepositoryMap.addBinding(Customer.class).to(CustomerRepository.class);
    unitOfWorkRepositoryMap.addBinding(Project.class).to(ProjectRepository.class);
    unitOfWorkRepositoryMap.addBinding(Employee.class).to(EmployeeRepository.class);
  }

}
