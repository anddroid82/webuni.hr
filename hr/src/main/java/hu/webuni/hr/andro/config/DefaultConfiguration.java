package hu.webuni.hr.andro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import hu.webuni.hr.andro.repository.EmployeeRepository;
import hu.webuni.hr.andro.service.DefaultEmployeeService;
import hu.webuni.hr.andro.service.EmployeeService;

@Configuration
@Profile("!smart")
public class DefaultConfiguration {
	
	@Bean
	public EmployeeService employeeService() {
		return new DefaultEmployeeService();
	}
	
}
