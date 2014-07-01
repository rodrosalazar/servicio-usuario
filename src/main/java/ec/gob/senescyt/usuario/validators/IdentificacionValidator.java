package ec.gob.senescyt.usuario.validators;

import ec.gob.senescyt.usuario.core.Identificacion;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
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
                (!TipoDocumentoEnum.CEDULA.equals(identificacion.getTipoDocumento())
                   && !TipoDocumentoEnum.PASAPORTE.equals(identificacion.getTipoDocumento()))) {
            return false;
        }

        if (TipoDocumentoEnum.CEDULA.equals(identificacion.getTipoDocumento())) {
            return cedulaValidator.isValidaCedula(identificacion.getNumeroIdentificacion());
        }

        return true;
    }
}
