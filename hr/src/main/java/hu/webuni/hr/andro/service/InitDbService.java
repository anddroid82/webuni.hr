package hu.webuni.hr.andro.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.CompanyRepository;
import hu.webuni.hr.andro.repository.EmployeeRepository;

@Service
public class InitDbService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	public void clearDb() {
		employeeRepository.deleteAll();
		companyRepository.deleteAll();
	}
	
	@Transactional
	public void insertTestData() {
		employeeRepository.truncateTable();
		
		List<Employee> employeesForStart = new ArrayList<>();
		employeesForStart
				.add(new Employee(1L, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0),null));
		employeesForStart.add(new Employee(2L, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0),null));
		employeesForStart
				.add(new Employee(3L, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0),null));
		employeesForStart
				.add(new Employee(4L, "Kovács Kázmér", "programozó", 700000, LocalDateTime.of(2014, 3, 13, 0, 0),null));
		employeesForStart
				.add(new Employee(5L, "Fodor Elek", "tesztelő", 600000, LocalDateTime.of(2016, 8, 14, 0, 0),null));
		employeesForStart
				.add(new Employee(6L, "Lukács Tamás", "rendszergazda", 380000, LocalDateTime.of(2010, 2, 26, 0, 0),null));		
		for (Employee e : employeesForStart) {
			employeeRepository.save(e);
		}
		
		//company test data
		Company c1 = new Company(1L, "Teszt Cég 01", "Teszt Cím 01");
		Employee e1 = employeeRepository.getById(1L);
		Employee e2 = employeeRepository.getById(2L);
		c1.addEmployee(e1);
		c1.addEmployee(e2);
		companyRepository.save(c1);
		
		Company c2 = new Company(2L, "Teszt Cég 02", "Teszt Cím 02");
		e1 = employeeRepository.getById(3L);
		e2 = employeeRepository.getById(4L);
		c2.addEmployee(e1);
		c2.addEmployee(e2);
		companyRepository.save(c2);
		
		Company c3 = new Company(3L, "Teszt Cég 03", "Teszt Cím 03");
		e1 = employeeRepository.getById(5L);
		e2 = employeeRepository.getById(6L);
		c3.addEmployee(e1);
		c3.addEmployee(e2);
		companyRepository.save(c3);
	}
}
