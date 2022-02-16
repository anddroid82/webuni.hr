package hu.webuni.hr.andro.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;

import hu.webuni.hr.andro.dto.VacationDto;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.mapper.VacationMapper;
import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.security.EmployeeUserDetails;
import hu.webuni.hr.andro.security.JwtService;
import hu.webuni.hr.andro.service.EmployeeService;
import hu.webuni.hr.andro.service.VacationService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class HrVacationRestControllerIT {
	
	private static final String BASE_URI = "/api/vacations";
	
	private static final String username = "tesztelek";
	private static final String password = "tesztelek";
	
	private static final String adminusername = "fodorelek";
	private static final String adminpassword = "fodorelek";
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	VacationService vacationService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	VacationMapper vacationMapper;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtService jwtService;
	
	private String jwtToken = null;
	
	@Test
	void testCreateVacationSuccess() throws Exception {
		
		this.jwtToken = this.createToken();
		
		List<VacationDto> vacationBefore = getAllVacationsJwt();
		
		VacationDto vacationDto = new VacationDto(
				VacationState.NEW,
				LocalDate.parse("2022-03-01"), 
				LocalDate.parse("2022-03-05"), 
				LocalDateTime.parse("2022-01-02T08:00:00"), 
				employeeMapper.employeeToDto(employeeService.getEmployee(9))
		);
		
		createVacation(vacationDto);
		
		vacationDto = vacationMapper.vacationToDto(vacationMapper.dtoToVacation(vacationDto));
		
		List<VacationDto> vacationAfter = getAllVacationsJwt();
		
		assertThat(vacationAfter.subList(0, vacationBefore.size()))
			.usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
			.containsExactlyElementsOf(vacationBefore);
		assertThat(vacationAfter.get(vacationAfter.size() - 1))
			.usingRecursiveComparison().ignoringFields("id").isEqualTo(vacationDto);
		
	}
	
	/*
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
	*/
	
	private void createVacation(VacationDto vacDto) {
		webTestClient.mutate()
			.defaultHeader("Authorization", this.jwtToken)
			.build()
			.post().uri(BASE_URI).bodyValue(vacDto).exchange().expectStatus().isOk();
	}
	
	private List<VacationDto> getAllVacationsJwt() {
		List<VacationDto> vacations = 
				webTestClient.mutate()
				.defaultHeader("Authorization", this.jwtToken)
				.build()
				.get().uri(BASE_URI).exchange().expectStatus().isOk()
				.expectBodyList(VacationDto.class).returnResult().getResponseBody();
		return vacations;
	}
	
	private String createToken() {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		EmployeeUserDetails emp = (EmployeeUserDetails)authentication.getPrincipal();
		Authentication auth = new UsernamePasswordAuthenticationToken(emp, null, emp.getAuthorities());
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		String token = jwtService.createToken(emp); 
		
		return token;
	}
	
	
	private void confirmVacation(long vacId,boolean confirm, long employeeId) {
		webTestClient.mutate().filter(ExchangeFilterFunctions.basicAuthentication(adminusername, adminpassword))
		.build()
			.get().uri(BASE_URI+"/"+vacId+"/"+confirm+"/"+employeeId).exchange().expectStatus().isOk();
	}
	
	

	/*
	private List<VacationDto> getAllVacations() {
		List<VacationDto> vacations = 
				webTestClient.mutate().filter(ExchangeFilterFunctions.basicAuthentication(username, password))
				.build()
				.get().uri(BASE_URI).exchange().expectStatus().isOk()
				.expectBodyList(VacationDto.class).returnResult().getResponseBody();
		return vacations;
	}
	
	private VacationDto getVacation(long id) {
		VacationDto vacation =
				webTestClient.mutate().filter(ExchangeFilterFunctions.basicAuthentication(username, password))
				.build()
				.get().uri(BASE_URI+"/"+id).exchange().expectStatus().isOk()
				.expectBody(VacationDto.class).returnResult().getResponseBody();
		return vacation;
	}
	*/
	
}
