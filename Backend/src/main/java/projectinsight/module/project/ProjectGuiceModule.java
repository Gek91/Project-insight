package projectinsight.module.project;

import com.google.inject.AbstractModule;

import com.google.inject.multibindings.MapBinder;
import projectinsight.module.app.commons.persistence.Repository;
import projectinsight.module.app.commons.persistence.UnitOfWork;
import projectinsight.module.app.commons.persistence.UnitOfWorkProvider;
import projectinsight.module.project.domain.customer.model.CustomerBuilder;
import projectinsight.module.project.domain.customer.repository.CustomerRepository;
import projectinsight.module.project.domain.customer.model.Customer;
import projectinsight.module.project.domain.employee.model.Employee;
import projectinsight.module.project.domain.employee.repository.EmployeeRepository;
import projectinsight.module.project.domain.project.model.Project;
import projectinsight.module.project.domain.project.model.ProjectBuilder;
import projectinsight.module.project.domain.project.model.ProjectVersion;
import projectinsight.module.project.domain.project.model.ProjectVersionBuilder;
import projectinsight.module.project.persistence.customer.CustomerMapper;
import projectinsight.module.project.persistence.customer.CustomerMapperProvider;
import projectinsight.module.project.persistence.employee.EmployeeMapper;
import projectinsight.module.project.persistence.employee.EmployeeMapperProvider;
import projectinsight.module.project.persistence.project.*;
import projectinsight.module.project.rest.customer.CustomerBuilderProvider;
import projectinsight.module.project.rest.project.ProjectBuilderProvider;
import projectinsight.module.project.domain.project.repository.ProjectRepository;
import projectinsight.module.project.persistence.customer.CustomerRepositoryImpl;
import projectinsight.module.project.persistence.employee.EmployeeRepositoryImpl;
import projectinsight.module.project.rest.customer.CustomerRestApiImpl;
import projectinsight.module.project.rest.project.ProjectRestApiImpl;
import projectinsight.module.project.rest.project.ProjectVersionBuilderProvider;

public class ProjectGuiceModule extends AbstractModule {


  @Override
	protected void configure() {


	  //Rest API
	  bind(ProjectRestApiImpl.class);
	  bind(CustomerRestApiImpl.class);

	  //Services

    //Repository
    bind(ProjectRepository.class).to(ProjectRespositoryImpl.class).asEagerSingleton();
    bind(EmployeeRepository.class).to(EmployeeRepositoryImpl.class).asEagerSingleton();
    bind(CustomerRepository.class).to(CustomerRepositoryImpl.class).asEagerSingleton();

    //Providers
    bind(CustomerBuilder.class).toProvider(CustomerBuilderProvider.class);
    bind(ProjectBuilder.class).toProvider(ProjectBuilderProvider.class);
    bind(ProjectVersionBuilder.class).toProvider(ProjectVersionBuilderProvider.class);
    bind(UnitOfWork.class).toProvider(UnitOfWorkProvider.class);
    bind(EmployeeMapper.class).toProvider(EmployeeMapperProvider.class);
    bind(CustomerMapper.class).toProvider(CustomerMapperProvider.class);
    bind(ProjectMapper.class).toProvider(ProjectMapperProvider.class);
    bind(ProjectVersionMapper.class).toProvider(ProjectVersionMapperProvider.class);


    //map repository in unit of work
    MapBinder<Class, Repository> unitOfWorkRepositoryMap = MapBinder.newMapBinder(binder(), Class.class, Repository.class);
    unitOfWorkRepositoryMap.addBinding(Customer.class).to(CustomerRepository.class);
    unitOfWorkRepositoryMap.addBinding(Project.class).to(ProjectRepository.class);
    unitOfWorkRepositoryMap.addBinding(Employee.class).to(EmployeeRepository.class);
  }

}
