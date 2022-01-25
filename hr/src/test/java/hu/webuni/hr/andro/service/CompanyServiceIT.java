package hu.webuni.hr.andro.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.andro.model.Employee;

@SpringBootTest
@AutoConfigureTestDatabase
public class CompanyServiceIT {

	@Autowired
	CompanyService companyService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Test
	void testAddEmployeeByIdToCompany() throws Exception {
		//9-es employee-t a 21-es céghez rakjuk
		Employee employee = employeeService.getEmployee(9);
		
		
	}
	
}
