package net.learntechnology.genericcrud.services;

import net.learntechnology.genericcrud.BaseIntegrationIT;
import net.learntechnology.genericcrud.domain.Employee;
import net.learntechnology.genericcrud.domain.EmployeeSearchCriteria;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class EmployeeIntegrationIT extends BaseIntegrationIT {
	private final static Logger logger = LoggerFactory.getLogger(EmployeeIntegrationIT.class);

	@Resource
	private EmployeeService employeeService;

	@Test
	public void should_fetch_all() {
		List<Employee> employees = employeeService.fetchAll();
		for(Employee emp: employees) {
			logger.debug("EMP: {}", emp);
		}
		Assert.assertTrue(employees.size() > 1);
	}

	@Test
	public void should_fetch_by_id() {
		Employee emp = employeeService.fetch(1L);
		logger.debug("Emp returned {}", emp);
		Assert.assertEquals("John", emp.getFirstName());
	}

	@Test
	public void should_fetch_by_search_criteria() {
		EmployeeSearchCriteria searchCriteria = new EmployeeSearchCriteria();
		searchCriteria.setFirstName("John");
		List<Employee> employees = employeeService.fetch(searchCriteria);
		for(Employee emp: employees) {
				logger.debug("EMP: {}", emp);
			}
		Assert.assertEquals("John", employees.get(0).getFirstName());
	}


	@Test
	@Transactional
	public void should_insert() {
		Employee emp = new Employee(null, 43, "TestFirstName", "TestLastName");
		employeeService.insert(emp);
		emp = employeeService.fetch(emp.getId());
		logger.debug("Emp returned {}", emp);
		Assert.assertEquals("TestFirstName", emp.getFirstName());
	}

	@Test
	@Transactional
	public void should_delete() {
		Employee emp = new Employee();
		emp.setId(1L);
		employeeService.delete(emp);
		emp = employeeService.fetch(emp.getId());
		Assert.assertNull(emp);
	}

	@Test
	@Transactional
	public void should_update() {
		Employee emp = new Employee(null, 43, "TestFirstName", "TestLastName");
		employeeService.insert(emp);
		emp = employeeService.fetch(emp.getId());
		logger.debug("Emp returned {}", emp);
		emp.setFirstName("modifiedFirst");
		emp.setLastName("modifiedLast");
		emp.setAge(99);
		employeeService.update(emp);
		emp = employeeService.fetch(emp.getId());
		Assert.assertEquals("modifiedFirst", emp.getFirstName());
		Assert.assertEquals("modifiedLast", emp.getLastName());
		Assert.assertTrue(99 == emp.getAge());
	}


}
