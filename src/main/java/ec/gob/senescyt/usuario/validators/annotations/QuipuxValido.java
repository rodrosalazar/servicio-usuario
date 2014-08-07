package ec.gob.senescyt.usuario.validators.annotations;

import ec.gob.senescyt.usuario.validators.QuipuxValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = QuipuxValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface QuipuxValido {
    String message() default "{ec.gob.senescyt.error.quipux}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    @Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface List {
        QuipuxValido[] value();
    }

}