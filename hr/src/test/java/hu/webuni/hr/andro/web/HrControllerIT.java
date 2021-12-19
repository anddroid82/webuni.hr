package hu.webuni.hr.andro.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import hu.webuni.hr.andro.service.EmployeeService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HrControllerIT {

	@Autowired
	EmployeeService employeeService;
	
	@Test
	private void testCreateEmployee() throws Exception {
		
	}
	
}
