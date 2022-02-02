package hu.webuni.hr.andro.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;

import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.dto.VacationDto;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.service.EmployeeService;
import hu.webuni.hr.andro.service.VacationService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class HrVacationRestControllerIT {
	
	private static final String BASE_URI = "/api/vacations";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	VacationService vacationService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Test
	void testCreateVacationSuccess() throws Exception {
		
		List<VacationDto> vacationBefore = getAllVacations();

		VacationDto vacationDto = new VacationDto(
				VacationState.NEW,
				LocalDate.parse("2022-03-01"), 
				LocalDate.parse("2022-03-05"), 
				LocalDateTime.parse("2022-01-02T08:00:00"), 
				employeeMapper.employeeToDto(employeeService.getEmployee(9))
		);
		createVacation(vacationDto);

		List<VacationDto> vacationAfter = getAllVacations();
		
		assertThat(vacationAfter.subList(0, vacationBefore.size()))
			.usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
			.containsExactlyElementsOf(vacationBefore);
		assertThat(vacationAfter.get(vacationAfter.size() - 1))
			.usingRecursiveComparison().ignoringFields("id").isEqualTo(vacationDto);
		
	}
	
	@Test
	void testConfirmVacation() throws Exception {
		//22/true/13
		long vacId = 22;
		boolean confirm = true;
		long empId = 13;
		VacationDto vacationBefore = getVacation(vacId);
		confirmVacation(vacId, confirm, empId);
		vacationBefore.setState(VacationState.AGREE);
		vacationBefore.setConfirmator(employeeMapper.employeeToDto(employeeService.getEmployee(empId)));
		VacationDto vacationAfter = getVacation(vacId);
		assertThat(vacationAfter).usingRecursiveComparison().isEqualTo(vacationBefore);
		
	}
	
	
	private void confirmVacation(long vacId,boolean confirm, long employeeId) {
		webTestClient.get().uri(BASE_URI+"/"+vacId+"/"+confirm+"/"+employeeId).exchange().expectStatus().isOk();
	}

	private void createVacation(VacationDto vacDto) {
		webTestClient.post().uri(BASE_URI).bodyValue(vacDto).exchange().expectStatus().isOk();
	}
	
	/*private void createVacationFail(VacationDto vacDto) {
		webTestClient.post().uri(BASE_URI).bodyValue(vacDto).exchange().expectStatus().isNotFound();
	}*/

	private List<VacationDto> getAllVacations() {
		List<VacationDto> vacations = webTestClient.get().uri(BASE_URI).exchange().expectStatus().isOk()
				.expectBodyList(VacationDto.class).returnResult().getResponseBody();
		return vacations;
	}
	
	private VacationDto getVacation(long id) {
		VacationDto vacation = webTestClient.get().uri(BASE_URI+"/"+id).exchange().expectStatus().isOk()
				.expectBody(VacationDto.class).returnResult().getResponseBody();
		return vacation;
	}
	
}
