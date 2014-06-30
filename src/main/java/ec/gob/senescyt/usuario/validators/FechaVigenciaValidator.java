package ec.gob.senescyt.usuario.validators;

import ec.gob.senescyt.usuario.validators.annotations.FechaVigenciaValida;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FechaVigenciaValidator  implements ConstraintValidator<FechaVigenciaValida, DateTime> {
    @Override
    public void initialize(FechaVigenciaValida constraintAnnotation) {

    }

    @Override
    public boolean isValid(DateTime fechaFinVigencia, ConstraintValidatorContext context) {
        return !fechaFinVigencia.isBefore(new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay());
    }
}
