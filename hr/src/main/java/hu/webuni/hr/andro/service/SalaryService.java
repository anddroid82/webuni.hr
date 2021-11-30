package hu.webuni.hr.andro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;

@Service
public class SalaryService {
	
	@Autowired
	//@Qualifier("smart")
	EmployeeService employeeService;
	
	public void setEmployeeNewPayment(Employee employee) {
		int newPayment=(int)(employee.getPayment()*(1+employeeService.getPayRaisePercent(employee)/100.0));
		employee.setPayment(newPayment);
	}
	
}
