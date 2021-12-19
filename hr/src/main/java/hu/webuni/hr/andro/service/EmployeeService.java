package hu.webuni.hr.andro.service;

import java.util.List;

import hu.webuni.hr.andro.model.Employee;

public interface EmployeeService {

	int getPayRaisePercent(Employee employee);
	
	Employee addEmployee(Employee employee);
	Employee getEmployee(long id);
	Employee deleteEmployee(Employee emp);
	Employee deleteEmployee(long id);
	List<Employee> getEmployees();
	void setEmployees(List<Employee> employees);
}
