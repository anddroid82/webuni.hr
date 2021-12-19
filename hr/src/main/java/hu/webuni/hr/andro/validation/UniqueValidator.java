package hu.webuni.hr.andro.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import hu.webuni.hr.andro.service.EmployeeService;

public class UniqueValidator implements ConstraintValidator<Unique, Long> {

	@Autowired
	EmployeeService employeeService;
	
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		
		return (employeeService.getEmployee(value) == null);
	}
	
}
