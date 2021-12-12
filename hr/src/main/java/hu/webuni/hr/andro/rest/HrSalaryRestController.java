package hu.webuni.hr.andro.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.webuni.hr.andro.model.Employee;
import hu.webuni.hr.andro.service.EmployeeService;

@RestController
@RequestMapping("/api/employeepay")
public class HrSalaryRestController {

	@Autowired
	EmployeeService employeeService;
	
	@PostMapping
	public int getEmployeePayRaisePercent(@RequestBody Employee employee) {
		int percent=this.employeeService.getPayRaisePercent(employee);
		return (int)(employee.getPayment()*(1+percent/100.0));
	}
	
	
}
