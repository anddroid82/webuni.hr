package hu.webuni.hr.andro.validation;

import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BeforeValidator implements ConstraintValidator<BeforeNow, LocalDateTime> {
	
	@Override
	public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
		if (value.isBefore(LocalDateTime.now())) {
			return true;
		}else {
			return false;
		}
	}
	
	

}
