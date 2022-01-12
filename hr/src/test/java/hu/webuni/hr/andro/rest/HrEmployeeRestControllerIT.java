package hu.webuni.hr.andro.rest;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import hu.webuni.hr.andro.dto.EmployeeDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HrEmployeeRestControllerIT {

	private static final String BASE_URI = "/api/employees";
	
	@Autowired
	WebTestClient webTestClient;

	@Test
	void testCreateEmployeeSuccess() throws Exception {
		List<EmployeeDto> employeesBefore = getAllEmployees();

		EmployeeDto empDto = new EmployeeDto(112L, "Kiss Attila", "programozó", 450000,
				LocalDateTime.parse("2014-03-14T12:00:00"),null);
		createEmployee(empDto);

		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter.subList(0, employeesBefore.size()))
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(employeesBefore);
		assertThat(employeesAfter.get(employeesAfter.size() - 1))
			.usingRecursiveComparison()
			.isEqualTo(empDto);
	}
	
	
	@Test
	void testCreateEmployeeFail() throws Exception {
		//létező felhasználót akarunk hozzáadni
		List<EmployeeDto> employeesBefore = getAllEmployees();
		EmployeeDto empDto = new EmployeeDto(112L, "Kiss Attila", "programozó", 450000,
				LocalDateTime.parse("2014-03-14T12:00:00"),null);
		createEmployeeFail(empDto);
		List<EmployeeDto> employeesAfter = getAllEmployees();
		assertThat(employeesAfter)
			.usingRecursiveFieldByFieldElementComparator()
			.containsExactlyElementsOf(employeesBefore);
			
	}
	
	
	@Test
	void testModifyEmployeeSuccess() throws Exception {
		EmployeeDto empDto = new EmployeeDto(111L, "Fehér Edit", "programozó", 450000,
				LocalDateTime.parse("2014-03-14T12:00:00"),null);
		modifyEmployee(empDto);

		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter)
			.contains(empDto)
			.usingRecursiveFieldByFieldElementComparator();
	}
	
	@Test
	void testModifyEmployeeFail() throws Exception {
		//nem létező felhasználót akarunk módosítani
		EmployeeDto empDto = new EmployeeDto(118L, "Fehér Edit", "programozó", 450000,
				LocalDateTime.parse("2014-03-14T12:00:00"),null);
		modifyEmployeeFail(empDto);

		List<EmployeeDto> employeesAfter = getAllEmployees();
		
		assertThat(employeesAfter)
			.doesNotContain(empDto)
			.usingRecursiveFieldByFieldElementComparator();
	}
	
	private void modifyEmployee(EmployeeDto empDto) {
		webTestClient.put().uri(BASE_URI+"/"+empDto.getId()).bodyValue(empDto).exchange().expectStatus().isOk();
	}
	
	private void modifyEmployeeFail(EmployeeDto empDto) {
		webTestClient.put().uri(BASE_URI+"/"+empDto.getId()).bodyValue(empDto).exchange().expectStatus().isNotFound();
	}

	private void createEmployee(EmployeeDto empDto) {
		webTestClient.post().uri(BASE_URI).bodyValue(empDto).exchange().expectStatus().isOk();
	}
	
	private void createEmployeeFail(EmployeeDto empDto) {
		webTestClient.post().uri(BASE_URI).bodyValue(empDto).exchange().expectStatus().isNotFound();
	}

	private List<EmployeeDto> getAllEmployees() {
		List<EmployeeDto> employees = webTestClient.get().uri(BASE_URI).exchange().expectStatus().isOk()
				.expectBodyList(EmployeeDto.class).returnResult().getResponseBody();
		return employees;
	}
}
