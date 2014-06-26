package ec.gob.senescyt.usuario.validators;

import org.apache.commons.validator.routines.RegexValidator;
import org.joda.time.DateTime;

/**
 * Created by njumbo on 26/06/14.
 */
public class QuipuxValidator {
    public static Boolean isValidoNumeroAutorizacionQuipux(final String numeroQuipux) {
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
