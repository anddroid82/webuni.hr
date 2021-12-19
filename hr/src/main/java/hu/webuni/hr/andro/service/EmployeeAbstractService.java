package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;

@Service
public abstract class EmployeeAbstractService {

	private List<Employee> employees = new ArrayList<>();

	{
		employees.add(new Employee(111L, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0)));
		employees.add(new Employee(222L, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employees.add(new Employee(333L, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0)));
		employees.add(new Employee(444L, "Kovács Kázmér", "programozó", 700000, LocalDateTime.of(2014, 3, 13, 0, 0)));
		employees.add(new Employee(555L, "Fodor Elek", "tesztelő", 600000, LocalDateTime.of(2016, 8, 14, 0, 0)));
		employees.add(new Employee(666L, "Lukács Tamás", "webfejlesztő", 380000, LocalDateTime.of(2010, 2, 26, 0, 0)));
	}

	public Employee addEmployee(Employee employee) {
		Employee emp = this.getEmployee(employee.getId());
		if (emp == null) {
			employees.add(employee);
			return employee;
		}
		return null;
	}
	
	public Employee getEmployee(long id) {
		for (Employee e : employees) {
			if (e.getId() == id)
				return e;
		}
		return null;
	}
	
	public Employee deleteEmployee(Employee emp) {
		if (this.employees.contains(emp)) {
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}
	
	public Employee deleteEmployee(long id) {
		Employee emp = this.getEmployee(id);
		if (emp != null) {
			this.employees.remove(emp);
			return emp;
		}
		return null;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
