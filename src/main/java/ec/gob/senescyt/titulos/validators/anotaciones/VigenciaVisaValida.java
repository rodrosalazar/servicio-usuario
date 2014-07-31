package ec.gob.senescyt.titulos.validators.anotaciones;

import ec.gob.senescyt.titulos.validators.VigenciaVisaValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = VigenciaVisaValidator.class)
@Target({ TYPE })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface VigenciaVisaValida {
    String message() default "{ec.gob.senescyt.error.pasaporte.finVigenciaVisa}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ TYPE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        VigenciaVisaValida[] value();
    }

}