package hu.webuni.hr.andro.rest;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.mapper.EmployeeMapper;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class HrEmployeeRestController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeMapper employeeMapper;

	@GetMapping
	public List<EmployeeDto> getAll(
			@RequestParam(required = false,defaultValue = "-1") int pn, 
			@RequestParam(required = false,defaultValue = "-1") int ps,
			@RequestParam(required = false,defaultValue = "name") String sort){
		return employeeMapper.employeesToDtos(employeeService.getEmployees(pn,ps,sort));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long id) {
		Employee employee=employeeService.getEmployee(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
		}
	}
	
	@PostMapping
	public ResponseEntity<EmployeeDto> createEmployee(@RequestBody @Valid EmployeeDto employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return ResponseEntity.notFound().build();
		}
		employeeService.addEmployee(employeeMapper.dtoToEmployee(employee));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,@RequestBody EmployeeDto employee) {
		Employee emp = employeeService.modifyEmployee(employeeMapper.dtoToEmployee(employee));
		if (emp != null) {
			return ResponseEntity.ok(employeeMapper.employeeToDto(emp));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable long id) {
		Employee employee=employeeService.deleteEmployee(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
		}
	}
	
	@GetMapping("/paymentgreater/{payment}")
	public List<EmployeeDto> getPaymentGreater(@PathVariable int payment) {
		return employeeMapper.employeesToDtos(employeeService.getEmployeesByPaymentGreaterThan(payment));
	}
	
	@GetMapping("/rank/{rank}")
	public List<EmployeeDto> getEmployeesByRank(@PathVariable String rank) {
		return employeeMapper.employeesToDtos(employeeService.getEmployeesByRank(rank));
	}
	
	@GetMapping("/namestart/{part}")
	public List<EmployeeDto> getEmployeesByNameStartsWithIgnoreCase(@PathVariable String part) {
		return employeeMapper.employeesToDtos(employeeService.getEmployeesByNameStartsWithIgnoreCase(part));
	}
	
	@GetMapping("/entrance/{start}/{end}")
	public List<EmployeeDto> getEmployeesByNameStartsWithIgnoreCase(@PathVariable String start,@PathVariable String end) {
		LocalDateTime startDT=LocalDateTime.parse(start);
		LocalDateTime endDT=LocalDateTime.parse(end);
		return employeeMapper.employeesToDtos(employeeService.getEmployeesByEntranceBetween(startDT,endDT));
	}
	
	@GetMapping("/setpaymenttominbypos/{rank}/{payment}")
	public List<EmployeeDto> setPaymentToMinimumByPosition(@PathVariable String rank,@PathVariable int payment) {
		return employeeMapper.employeesToDtos(employeeService.setPaymentToMinimumByPosition(rank,payment));
	}
	
	@PostMapping("/search")
	public List<EmployeeDto> search(@RequestBody EmployeeDto employee) {
		return employeeMapper.employeesToDtos(employeeService.findEmployeesByExample(employeeMapper.dtoToEmployee(employee)));
	}
}
