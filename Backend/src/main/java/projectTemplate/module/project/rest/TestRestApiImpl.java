package projectTemplate.module.project.rest;

import java.sql.SQLException;
import java.util.Map;

import com.google.inject.Inject;

import projectTemplate.module.app.service.PersistenceService;
import projectTemplate.module.app.service.PropertiesService;
import projectTemplate.module.project.domain.customer.CustomerRepository;
import projectTemplate.module.project.domain.customer.model.Customer;

public class TestRestApiImpl implements TestRestApi {

	@Inject
	private PropertiesService propertiesService;

	@Inject
	private CustomerRepository customerRepository;

	@Inject
	private PersistenceService persistenceService;

	@Override
	public String echo() {
		return propertiesService.getTestValue();
	}

	@Override
	public String testDb() {


		try {
			persistenceService.beginJdbcTransaction();

//			try (Connection connection = persistenceService.getConnection()) {
//
//				try	(PreparedStatement statement = connection.prepareStatement("SELECT count(id) FROM core_users")) {
//
//					statement.execute();
//
//					try (ResultSet resultSet = statement.getResultSet()) {
//
//						while(resultSet.next()) {
//							return "" + resultSet.getLong(1);
//						}
//					}
//				}
//			}

			Map<String, Customer> result = customerRepository.findAll();

			persistenceService.commitJdbcTransaction();

      return "" + result.size();

    } catch (Exception e) {
			try {
				persistenceService.rollbackJdbcTransaction();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return "" + 0;
	}
}
