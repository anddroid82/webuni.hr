package hu.webuni.hr.andro.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.dto.EmployeeDto;
import hu.webuni.hr.andro.repository.EmployeeDtoRepository;

@RestController
@RequestMapping("/api/employees")
public class HrEmployeeRestController {

	@Autowired
	private EmployeeDtoRepository employeeRepo;

	@GetMapping
	public List<EmployeeDto> getAll(){
		return new ArrayList<>(employeeRepo.getEmployees());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long id) {
		EmployeeDto employeeDto=employeeRepo.getEmployee(id);
		if (employeeDto == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employeeDto);
		}
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employee) {
		employeeRepo.addEmployee(employee);
		return employee;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,@RequestBody EmployeeDto employee) {
		EmployeeDto emp = employeeRepo.getEmployee(id);
		if (emp != null) {
			employee.setId(id);
			employeeRepo.deleteEmployee(employee);
			employeeRepo.addEmployee(employee);
			return ResponseEntity.ok(employee);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable long id) {
		EmployeeDto employee=employeeRepo.getEmployee(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}else {
			employeeRepo.deleteEmployee(id);
			return ResponseEntity.ok(employee);
		}
	}
	
	@GetMapping("/paymentgreater/{payment}")
	public List<EmployeeDto> getPaymentGreater(@PathVariable int payment) {
		ArrayList<EmployeeDto> greaterEmployee=new ArrayList<>();
		for (EmployeeDto e : employeeRepo.getEmployees()) {
			if (e.getPayment()>payment) {
				greaterEmployee.add(e);
			}
		}
		return greaterEmployee;
	}
	
}
