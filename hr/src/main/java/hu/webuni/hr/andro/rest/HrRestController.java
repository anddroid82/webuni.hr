package hu.webuni.hr.andro.rest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@RestController
@RequestMapping("/api/employees")
public class HrRestController {

	private Map<Long,EmployeeDto> employees = new HashMap<>();

	{
		employees.put(723L,new EmployeeDto(723L, "Teszt Elek", "rendszergazda", 450000,
				LocalDateTime.of(2019, 10, 2, 0, 0)));
		employees.put(561L,new EmployeeDto(561L, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employees.put(278L,new EmployeeDto(278L, "Tra Pista", "rendszer tervező", 800000,
				LocalDateTime.of(2011, 2, 23, 0, 0)));
	}

	@GetMapping
	public List<EmployeeDto> getAll(){
		return new ArrayList<>(employees.values());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long id) {
		EmployeeDto employeeDto=employees.get(id);
		if (employeeDto == null) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(employeeDto);
		}
	}
	
	@PostMapping
	public EmployeeDto createEmployee(@RequestBody EmployeeDto employee) {
		employees.put(employee.getId(), employee);
		return employee;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<EmployeeDto> modifyEmployee(@PathVariable long id,@RequestBody EmployeeDto employee) {
		if (employees.containsKey(id)) {
			employee.setId(id);
			employees.put(id, employee);
			return ResponseEntity.ok(employee);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<EmployeeDto> deleteEmployee(@PathVariable long id) {
		EmployeeDto employee=employees.get(id);
		if (employee == null) {
			return ResponseEntity.notFound().build();
		}else {
			employees.remove(id);
			return ResponseEntity.ok(employee);
		}
	}
	
	@GetMapping("/paymentgreater/{payment}")
	public List<EmployeeDto> getPaymentGreater(@PathVariable int payment) {
		ArrayList<EmployeeDto> greaterEmployee=new ArrayList<>();
		for (EmployeeDto e : employees.values()) {
			if (e.getPayment()>payment) {
				greaterEmployee.add(e);
			}
		}
		return greaterEmployee;
	}
	
}
