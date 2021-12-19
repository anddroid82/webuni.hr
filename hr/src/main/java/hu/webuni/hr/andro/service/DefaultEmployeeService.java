package hu.webuni.hr.andro.service;

import org.springframework.stereotype.Service;

import hu.webuni.hr.andro.model.Employee;

@Service
public class DefaultEmployeeService extends EmployeeAbstractService implements EmployeeService {

	@Override
	public int getPayRaisePercent(Employee employee) {
		return 5;
	}

}
