package hu.webuni.hr.andro.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy=BeforeValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeNow {
    String message() default "múltbéli dátumot kell megadni";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    //String value();
}
