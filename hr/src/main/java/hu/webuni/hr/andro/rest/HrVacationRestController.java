package hu.webuni.hr.andro.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.dto.VacationDto;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.mapper.VacationMapper;
import hu.webuni.hr.andro.model.Vacation;
import hu.webuni.hr.andro.model.VacationState;
import hu.webuni.hr.andro.service.EmployeeService;
import hu.webuni.hr.andro.service.VacationService;

@RestController
@RequestMapping("/api/vacations")
public class HrVacationRestController {

	@Autowired
	VacationMapper vacationMapper;
	
	@Autowired
	VacationService vacationService;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping
	public ResponseEntity<List<VacationDto>> getAll(){
		return ResponseEntity.ok(vacationMapper.vacationsToDtos(vacationService.getAll()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<VacationDto> get(@PathVariable long id) {
		Vacation vacation = vacationService.getById(id);
		if (vacation != null) {
			return ResponseEntity.ok(vacationMapper.vacationToDto(vacation));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<VacationDto> createVacation(@RequestBody @Valid VacationDto vacationDto, BindingResult bindingResult) {
		vacationDto.setState(VacationState.NEW);
		EmployeeDto employeeDto = employeeMapper.employeeToDto(employeeService.getEmployee(vacationDto.getOwner().getId()));
		vacationDto.setOwner(employeeDto);
		if (bindingResult.hasErrors()) {
			return ResponseEntity.notFound().build();
		}else {
			Vacation vacation = vacationService.addVacation(vacationMapper.dtoToVacation(vacationDto));
			return ResponseEntity.ok(vacationMapper.vacationToDto(vacation));
		}
	}
	
}
