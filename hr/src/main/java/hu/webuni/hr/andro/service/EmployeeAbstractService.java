package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.EmployeeRepository;

@Service
public abstract class EmployeeAbstractService {
	
	private static int INIT_COUNT = 0;
	
	//egy pár kezdő adat az adatbázisba
	private List<Employee> employeesForStart = new ArrayList<>();
	{
		employeesForStart.add(new Employee(111L, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0)));
		employeesForStart.add(new Employee(222L, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employeesForStart.add(new Employee(333L, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0)));
		employeesForStart.add(new Employee(444L, "Kovács Kázmér", "programozó", 700000, LocalDateTime.of(2014, 3, 13, 0, 0)));
		employeesForStart.add(new Employee(555L, "Fodor Elek", "tesztelő", 600000, LocalDateTime.of(2016, 8, 14, 0, 0)));
		employeesForStart.add(new Employee(666L, "Lukács Tamás", "rendszergazda", 380000, LocalDateTime.of(2010, 2, 26, 0, 0)));
	}
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	//kerdes: valamiért 3-szor is lefut a postConstruct, miért? Az alapértelmezett Bean scope a singleton!
	@PostConstruct
	public void postConstruct() {
		if (INIT_COUNT == 0) {
			System.out.println("POST_CON");
			employeesCreateForStart();
			INIT_COUNT++;
		}
	}
	
	//kerdes: mire kell pontosan a @Transactional? Mert ha erre használom, akkor nem elég, a truncateTable-ra kell, akkor nem ad hibát
	private void employeesCreateForStart() {
		employeeRepository.truncateTable();
		for (Employee e : employeesForStart) {
			employeeRepository.save(e);
		}
	}
	
	@Transactional
	public Employee addEmployee(Employee employee) {
		if (!employeeRepository.existsById(employee.getId())) {
			return employeeRepository.save(employee);
		}
		return null;
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
		//this.employees = employees;
	}
	
	public List<Employee> getEmployeesByRank(String rank) {
		return employeeRepository.findByRank(rank);
	}
	
	public List<Employee> getEmployeesByNameStartsWithIgnoreCase(String name) {
		return employeeRepository.findByNameStartsWithIgnoreCase(name);
	}
	
	public List<Employee> getEmployeesByEntranceBetween(LocalDateTime start,LocalDateTime end) {
		return employeeRepository.findByEntranceBetween(start, end);
	}
	
	
}
