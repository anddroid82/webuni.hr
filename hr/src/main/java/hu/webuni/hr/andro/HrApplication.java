package hu.webuni.hr.andro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.andro.model.Company;
import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.repository.CompanyRepository;
import hu.webuni.hr.andro.repository.EmployeeRepository;
import hu.webuni.hr.andro.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CompanyRepository companyRepository;

	/*
	private List<Employee> employees = new ArrayList<>();

	{
		employees.add(
				new Employee((long) 723, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0)));
		employees.add(new Employee((long) 561, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employees.add(
				new Employee((long) 278, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0)));
	}
	*/

	@Autowired
	SalaryService salaryService;

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		 * for (Employee e : employees) { System.out.println("Emelés előtt: " + e);
		 * salaryService.setEmployeeNewPayment(e); System.out.println("Emelés után: " +
		 * e); }
		 */

		this.setEmployeeDefaultData();
		this.setCompanyDefaultData();
	}

	@Transactional
	public void setEmployeeDefaultData() {
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

		employeeRepository.truncateTable();
		for (Employee e : employeesForStart) {
			employeeRepository.save(e);
		}
	}

	@Transactional
	public void setCompanyDefaultData() {
		companyRepository.deleteAll();

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

/**
 * 
 * - konstruktorrral történő injektálásnál nem kell autowired? talán a CompanyController-ben volt Gábornál
- az AbstractEmployeeService-ben a postconstruct 3-szor fut le, miért?
- mikor kell paraméter nélküli default konstruktor? entity esetén kell-e mindig?
- a transactional-t hogyan kell használni? közvetlenül ott kell-e használni ahol a repository-s módosító hívás van, vagy ha közvetve hívódik egy metódus és az módosít, 
    akkor a hívóban elég?
- pgsql-ben a truncate nem reset-eli az id értékét, mi a mgeoldás?
 * 
 * 
 * 
 * 
 * 
 */

