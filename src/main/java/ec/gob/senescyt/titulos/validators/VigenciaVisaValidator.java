package ec.gob.senescyt.titulos.validators;

import ec.gob.senescyt.titulos.core.Pasaporte;
import ec.gob.senescyt.titulos.validators.anotaciones.VigenciaVisaValida;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VigenciaVisaValidator implements ConstraintValidator<VigenciaVisaValida, Pasaporte> {
    @Override
    public void initialize(VigenciaVisaValida constraintAnnotation) {
        // Do nothing.
    }

    @Override
    public boolean isValid(Pasaporte pasaporte, ConstraintValidatorContext context) {
        DateTime finVigenciaVisa = pasaporte.getFinVigenciaVisa();

        if (pasaporte.isVisaIndefinida()) {
            return finVigenciaVisa == null;
        }

        return finVigenciaVisa != null && !estaEnElPasado(finVigenciaVisa);
    }

    private boolean estaEnElPasado(DateTime finVigenciaVisa) {
        return finVigenciaVisa.isBefore(new DateTime().withZone(DateTimeZone.UTC).withTimeAtStartOfDay());
    }
}
