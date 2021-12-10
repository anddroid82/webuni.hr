package hu.webuni.hr.andro.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import hu.webuni.hr.andro.dto.EmployeeDto;

@Component
public class EmployeeDtoRepository {

	private List<EmployeeDto> employees = new ArrayList<>();

	{
		employees.add(new EmployeeDto(111L, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0)));
		employees.add(new EmployeeDto(222L, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employees.add(new EmployeeDto(333L, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0)));
		employees.add(new EmployeeDto(444L, "Kovács Kázmér", "programozó", 700000, LocalDateTime.of(2014, 3, 13, 0, 0)));
		employees.add(new EmployeeDto(555L, "Fodor Elek", "tesztelő", 600000, LocalDateTime.of(2016, 8, 14, 0, 0)));
		employees.add(new EmployeeDto(666L, "Lukács Tamás", "webfejlesztő", 380000, LocalDateTime.of(2010, 2, 26, 0, 0)));
	}

	public EmployeeDto addEmployee(EmployeeDto employee) {
		EmployeeDto emp = this.getEmployee(employee.getId());
		if (emp == null) {
			employees.add(employee);
			return employee;
		}
		return null;
	}
	
	public EmployeeDto getEmployee(long id) {
		for (EmployeeDto e : employees) {
			if (e.getId() == id)
				return e;
		}
		return null;
	}
	
	public EmployeeDto deleteEmployee(EmployeeDto emp) {
		if (this.employees.contains(emp)) {
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}
	
	public EmployeeDto deleteEmployee(long id) {
		EmployeeDto emp = this.getEmployee(id);
		if (emp != null) {
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}

	public List<EmployeeDto> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDto> employees) {
		this.employees = employees;
	}

	
}
