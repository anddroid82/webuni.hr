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
import hu.webuni.hr.andro.service.InitDbService;
import hu.webuni.hr.andro.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	InitDbService initDbService;

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

		initDbService.clearDb();
		initDbService.insertTestData();
		
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

