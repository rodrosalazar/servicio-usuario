package ec.gob.senescyt.usuario.validators;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumento;
import ec.gob.senescyt.usuario.validators.annotations.IdentificacionValida;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IdentificacionValidator implements ConstraintValidator<IdentificacionValida, Identificacion> {

    private CedulaValidator cedulaValidator;

    @Override
    public void initialize(IdentificacionValida constraintAnnotation) {
        cedulaValidator = new CedulaValidator();
    }

    @Override
    public boolean isValid(Identificacion identificacion, ConstraintValidatorContext context) {

        if (identificacion.getTipoDocumento() == null ||
                (!TipoDocumento.CEDULA.equals(identificacion.getTipoDocumento())
                   && !TipoDocumento.PASAPORTE.equals(identificacion.getTipoDocumento()))) {
            return false;
        }

        if (TipoDocumento.CEDULA.equals(identificacion.getTipoDocumento())) {
            return cedulaValidator.isValidaCedula(identificacion.getNumeroIdentificacion());
        }

        return true;
    }
}
