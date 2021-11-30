package hu.webuni.hr.andro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import hu.webuni.hr.andro.service.EmployeeService;
import hu.webuni.hr.andro.service.SmartEmployeeService;

@Configuration
@Profile("smart")
public class SmartConfiguration {
	
	@Bean
	public EmployeeService employeeService() {
		return new SmartEmployeeService();
	}
	
}
