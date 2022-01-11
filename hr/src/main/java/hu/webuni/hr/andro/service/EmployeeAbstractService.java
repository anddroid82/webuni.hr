package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.EmployeeRepository;

@Service
public abstract class EmployeeAbstractService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	//kerdes: a Transactional-t pontosan hol kell használni? ha metódus hív másikat, és az változtat, akkor a hívó is rá lehet rakni? 
	@Transactional
	public Employee addEmployee(Employee employee) {
		employee.setId(null);
		return employeeRepository.save(employee);
	}

	@Transactional
	public Employee modifyEmployee(Employee employee) {
		if (employeeRepository.existsById(employee.getId())) {
			return employeeRepository.save(employee);
		}
		return null;
	}

	public Employee getEmployee(long id) {
		if (employeeRepository.existsById(id)) {
			return employeeRepository.getById(id);
		}
		return null;
	}

	public Employee deleteEmployee(Employee emp) {
		return this.deleteEmployee(emp.getId());
	}

	@Transactional
	public Employee deleteEmployee(long id) {
		if (employeeRepository.existsById(id)) {
			Employee emp = employeeRepository.getById(id);
			employeeRepository.deleteById(id);
			return emp;
		}
		return null;
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

	public void setEmployees(List<Employee> employees) {
		// this.employees = employees;
	}

	public List<Employee> getEmployeesByRank(String rank) {
		return employeeRepository.findByRank(rank);
	}

	public List<Employee> getEmployeesByNameStartsWithIgnoreCase(String name) {
		return employeeRepository.findByNameStartsWithIgnoreCase(name);
	}

	public List<Employee> getEmployeesByEntranceBetween(LocalDateTime start, LocalDateTime end) {
		return employeeRepository.findByEntranceBetween(start, end);
	}
	
	public List<Employee> getEmployeesByPaymentGreaterThan(int payment) {
		return employeeRepository.findByPaymentGreaterThan(payment);
	}

}
