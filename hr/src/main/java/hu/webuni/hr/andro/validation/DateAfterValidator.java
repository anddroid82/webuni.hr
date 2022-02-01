package hu.webuni.hr.andro.validation;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
public class DateAfterValidator implements ConstraintValidator<DateAfter, Object> {
	
	private String firstFieldName;
	private String secondFieldName;
	
	@Override
	public void initialize(DateAfter constraintAnnotation) {
		this.firstFieldName=constraintAnnotation.firstField();
		this.secondFieldName=constraintAnnotation.secondField();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
			Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
			if (firstObj != null && secondObj != null) {
				LocalDate firstDate=(LocalDate)firstObj;
				LocalDate secondDate=(LocalDate)secondObj;
				return secondDate.isAfter(firstDate);
			}
		}
		catch(Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		}
		return false; 
	}
	
}
