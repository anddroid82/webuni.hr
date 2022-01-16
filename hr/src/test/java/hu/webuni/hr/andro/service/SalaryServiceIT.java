package hu.webuni.hr.andro.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import hu.webuni.hr.andro.model.Employee;

@SpringBootTest
public class SalaryServiceIT {

	@Autowired
	SalaryService salaryService;
	
	@Test
	public void testEmployeeNewPayment() throws Exception {
		/*Employee employee = new Employee(11L, "Teszt", "teszt", 100000, LocalDateTime.parse("2015-05-10T12:00:00"),null);
		salaryService.setEmployeeNewPayment(employee);
		assertThat(employee.getPayment()).isEqualTo(105000);*/
	}
	
}
