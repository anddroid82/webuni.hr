package hu.webuni.hr.andro.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import hu.webuni.hr.andro.model.Employee;

@ExtendWith(MockitoExtension.class)
public class SalaryServiceTest {

	@InjectMocks
	SalaryService salaryService;
	
	@Mock
	EmployeeService employeeService;
	
	@Test
	void testGetPayRaisePercent() throws Exception {
		Employee employee = new Employee(11L, "Teszt", "teszt", 100000, LocalDateTime.parse("2015-05-10T12:00:00"));
		Mockito.when(employeeService.getPayRaisePercent(employee)).thenReturn(5);
		salaryService.setEmployeeNewPayment(employee);
		assertThat(employee.getPayment()).isEqualTo(105000);
	}
	
}
