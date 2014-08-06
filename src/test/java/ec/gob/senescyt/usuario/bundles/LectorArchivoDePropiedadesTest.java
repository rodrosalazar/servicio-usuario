package ec.gob.senescyt.usuario.bundles;

import ec.gob.senescyt.commons.lectores.LectorArchivoDePropiedades;
import ec.gob.senescyt.commons.lectores.enums.ArchivosPropiedadesEnum;
import ec.gob.senescyt.usuario.enums.MensajesErrorEnum;
import org.junit.Test;

import java.util.MissingResourceException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class LectorArchivoDePropiedadesTest {

    LectorArchivoDePropiedades lectorValidationMessages;

    @Test(expected = MissingResourceException.class)
    public void debeLanzarExcepcionCuandoElArchivoNoExiste() throws Exception {
        String nombreArchivoValidaciones = "ArchivoNoExistente";
        lectorValidationMessages = new LectorArchivoDePropiedades(nombreArchivoValidaciones);
    }

    @Test
    public void debeLeerDesdeArchivoDePropiedades() throws Exception {
        lectorValidationMessages = new LectorArchivoDePropiedades(ArchivosPropiedadesEnum.ARCHIVO_VALIDACIONES.getBaseName());
        String numeroIdentificacion  = lectorValidationMessages.leerPropiedad(MensajesErrorEnum.CAMPO_NUMERO_IDENTIFICACION.getKey());

        assertThat(numeroIdentificacion, is(lectorValidationMessages.leerPropiedad(MensajesErrorEnum.CAMPO_NUMERO_IDENTIFICACION.getKey())));
    }
}
