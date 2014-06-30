package ec.gob.senescyt.usuario.validators;

import ec.gob.senescyt.usuario.validators.annotations.QuipuxValido;
import org.apache.commons.validator.routines.RegexValidator;
import org.joda.time.DateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class QuipuxValidator  implements ConstraintValidator<QuipuxValido, String>{

    @Override
    public void initialize(QuipuxValido constraintAnnotation) {

    }

    @Override
    public boolean isValid(String numeroQuipux, ConstraintValidatorContext context) {
        String regexParaNumeroAutorizacionQuipux = "SENESCYT-[A-Z]{2,6}-[1-2][0-9][0-9]{2}-[0-9]{1,6}-MI";
        RegexValidator regexValidator = new RegexValidator(regexParaNumeroAutorizacionQuipux);

        Boolean isFormatoValido = regexValidator.isValid(numeroQuipux);

        if(isFormatoValido){
            Integer anioCreacionQuipux = Integer.valueOf(numeroQuipux.split("-")[2]);
            if(anioCreacionQuipux > DateTime.now().getYear()){
                return false;
            }
        }

        return isFormatoValido;
    }
}
