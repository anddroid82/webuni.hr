package hu.webuni.hr.andro.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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
	public List<EmployeeDto> getAll(){
		return employeeMapper.employeesToDtos(employeeService.getEmployees());
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
		Employee emp = employeeService.getEmployee(employee.getId());
		if (bindingResult.hasErrors() || emp!=null) {
			return ResponseEntity.notFound().build();
		}
		employeeService.addEmployee(employeeMapper.employeeDtoToEmployee(employee));
		return ResponseEntity.ok(employee);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,@RequestBody EmployeeDto employee) {
		Employee emp = employeeService.getEmployee(id);
		if (emp != null) {
			emp = employeeService.modifyEmployee(employeeMapper.employeeDtoToEmployee(employee));
			return ResponseEntity.ok(employeeMapper.employeeToDto(emp));
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable long id) {
		Employee employee=employeeService.getEmployee(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}else {
			employeeService.deleteEmployee(id);
			return ResponseEntity.ok(employeeMapper.employeeToDto(employee));
		}
	}
	
	@GetMapping("/paymentgreater/{payment}")
	public List<EmployeeDto> getPaymentGreater(@PathVariable int payment) {
		ArrayList<EmployeeDto> greaterEmployee=new ArrayList<>();
		for (Employee e : employeeService.getEmployees()) {
			if (e.getPayment()>payment) {
				greaterEmployee.add(employeeMapper.employeeToDto(e));
			}
		}
		return greaterEmployee;
	}
	
	@GetMapping("/rank/{rank}")
	public List<Employee> getEmployeesByRank(@PathVariable String rank) {
		return employeeService.getEmployeesByRank(rank);
	}
	
	@GetMapping("/name/{part}")
	public List<Employee> getEmployeesByNameStartsWithIgnoreCase(@PathVariable String part) {
		return employeeService.getEmployeesByNameStartsWithIgnoreCase(part);
	}
	
	@GetMapping("/entrance/{start}/{end}")
	public List<Employee> getEmployeesByNameStartsWithIgnoreCase(@PathVariable LocalDateTime start,@PathVariable LocalDateTime end) {
		return employeeService.getEmployeesByEntranceBetween(start,end);
	}
}
