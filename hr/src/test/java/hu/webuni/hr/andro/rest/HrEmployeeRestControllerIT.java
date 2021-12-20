package hu.webuni.hr.andro.rest;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.service.EmployeeService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HrEmployeeRestControllerIT {

	private static final String BASE_URI = "/api/employees";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Test
	private void testCreateEmployee() throws Exception {
		
		List<EmployeeDto> employeesBefore = getAllEmployees();
		
		EmployeeDto empDto = new EmployeeDto(112L, "Kovács Kázmér", "programozó", 450000, LocalDateTime.parse("2014-03-14T12:00:00"));
		createEmployee(empDto);
		
		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.subList(0, employeesAfter.size()))
			.containsExactlyElementsOf(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size()-1))
			.isEqualTo(empDto);
		
	}

	private void createEmployee(EmployeeDto empDto) {
		webTestClient
			.post()
			.uri(BASE_URI)
			.bodyValue(empDto)
			.exchange()
			.expectStatus()
			.isOk();
		
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> employees = webTestClient
				.post()
				.uri(BASE_URI)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(EmployeeDto.class)
				.returnResult()
				.getResponseBody();
		return employees;
	}
	
	
}
