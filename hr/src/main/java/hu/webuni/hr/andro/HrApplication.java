package hu.webuni.hr.andro;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.service.SalaryService;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {
	
	private List<Employee> employees = new ArrayList<>();
	
	{
		employees.add(new Employee((long) 723, "Teszt Elek", "rendszergazda", 450000, LocalDateTime.of(2019, 10, 2, 0, 0)));
		employees.add(new Employee((long) 561, "Nap Pali", "grafikus", 600000, LocalDateTime.of(2015, 4, 12, 0, 0)));
		employees.add(new Employee((long) 278, "Tra Pista", "rendszer tervező", 800000, LocalDateTime.of(2011, 2, 23, 0, 0)));
	}
	
	@Autowired
	SalaryService salaryService;
	
	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (Employee e : employees) {
			System.out.println("Emelés előtt: "+e);
			salaryService.setEmployeeNewPayment(e);
			System.out.println("Emelés után: "+e);
			System.out.println("");
		}
		
	}
	
}
