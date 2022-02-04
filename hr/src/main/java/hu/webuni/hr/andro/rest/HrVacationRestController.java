package hu.webuni.hr.andro.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.dto.VacationDto;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.mapper.VacationMapper;
import hu.webuni.hr.andro.model.Vacation;
import hu.webuni.hr.andro.model.VacationSearch;
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
	public ResponseEntity<List<VacationDto>> getAll(@SortDefault("id") Pageable pageable){
		Page<Vacation> page = vacationService.getAll(pageable);
		if (page.hasContent()) {
			return ResponseEntity.ok(vacationMapper.vacationsToDtos(page.getContent()));
		}
		return ResponseEntity.notFound().build();
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
		if (bindingResult.hasErrors()) {
			return ResponseEntity.notFound().build();
		}else {
			Vacation vacation = vacationService.addVacation(vacationMapper.dtoToVacation(vacationDto));
			return ResponseEntity.ok(vacationMapper.vacationToDto(vacation));
		}
	}
	
	@GetMapping("/{id}/{confirm}/{confirmatorId}")
	public ResponseEntity<VacationDto> confirmVacation(@PathVariable long id, @PathVariable boolean confirm, @PathVariable long confirmatorId) {
		Vacation vacation = vacationService.confirmVacation(id, confirm, confirmatorId);
		if (vacation != null) {
			return ResponseEntity.ok(vacationMapper.vacationToDto(vacation));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<VacationDto> deleteVacation(@PathVariable long id) {
		Vacation vacation = vacationService.deleteVacation(id);
		if (vacation != null) {
			return ResponseEntity.ok(vacationMapper.vacationToDto(vacation));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<VacationDto> modifyVacation(@PathVariable long id,@RequestBody VacationDto vacationDto) {
		Vacation vacation = vacationService.modifyVacation(vacationMapper.dtoToVacation(vacationDto));
		if (vacation != null) {
			return ResponseEntity.ok(vacationMapper.vacationToDto(vacation));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/search")
	public ResponseEntity<List<VacationDto>> search(@RequestBody(required = false) VacationSearch vacation,@SortDefault("id") Pageable pageable) {
		Page<Vacation> page = vacationService.findVacationsByExample(vacation,pageable);
		if (page.hasContent()) {
			return ResponseEntity.ok(vacationMapper.vacationsToDtos(page.getContent())); 
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
