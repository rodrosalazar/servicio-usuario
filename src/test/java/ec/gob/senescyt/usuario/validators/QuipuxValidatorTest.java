package ec.gob.senescyt.usuario.validators;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by njumbo on 26/06/14.
 */
public class QuipuxValidatorTest {

    private static final String numeroAutorizacionQuipuxValido = "SENESCYT-DFAPO-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxSinNombreInstitucionSENESCYT = "IESS-DFAPO-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxConSiglasUnidadConNumeros = "SENESCYT-DFAP1-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxConSiglasDeMasDe6Letras = "SENESCYT-DFAPOTT-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxConSiglasConCaracteresEspeciales = "SENESCYT-DFAPOT&-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxConSiglasMenoresADosCaracteres = "SENESCYT-D-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxConAnioConLetras = "SENESCYT-DFAPOT-201a-65946-MI";
    private static final String numeroAutorizacionQuipuxConAnioIgualAlActual = "SENESCYT-DFAPO-2014-65946-MI";
    private static final String numeroAutorizacionQuipuxConAnioMenorAlActual = "SENESCYT-DFAPO-1998-65946-MI";
    private static final String numeroAutorizacionQuipuxConAnioMayorAlActual = "SENESCYT-DFAPO-2015-65946-MI";
    private static final String numeroAutorizacionQuipuxConAnioConCaracteresEspeciales = "SENESCYT-DFAPOT-201&-65946-MI";
    private static final String numeroAutorizacionQuipuxConNumeroQuipuxConLetras = "SENESCYT-DFAPO-2014-659a6-MI";
    private static final String numeroAutorizacionQuipuxConNumeroQuipuxConCaracteresEspeciales= "SENESCYT-DFAPO-2014-&5946-MI";
    private static final String numeroAutorizacionConNumeroQuipuxDeLongitudMayorA6 = "SENESCYT-DFAPO-2014-6594677-MI";
    private static final String numeroAutorizacionConSiglasMemorandoInternoIncorrectas = "SENESCYT-DFAPO-2014-65946-MF";

    @Test
    public void debeIndicarCuandoFormatoNumeroQuipuxEsValido() throws Exception {
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxValido);

        assertThat(esValido, is(true));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxDebeTenerNombreInstitucionSENESCYT(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxSinNombreInstitucionSENESCYT);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaSoloLetrasEnSiglasDeUnidadDepartamento(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConSiglasUnidadConNumeros);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaMinimo2CaracteresEnSiglasDeUnidadDepartamento(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConSiglasMenoresADosCaracteres);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaMaximo6LetrasEnSiglasDeUnidadDepartamento(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConSiglasDeMasDe6Letras);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxNoContengaCaracteresEspecialesEnSiglasDeUnidadDepartamento(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConSiglasConCaracteresEspeciales);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxNoContengaLetras(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConAnioConLetras);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxNoContengaCaracteresEspeciales(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConAnioConCaracteresEspeciales);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxSeaMenorOIgualAlAnioActual(){
        Boolean esValidoConAnioIgualAlActual = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConAnioIgualAlActual);
        Boolean esValidoConAnioMenorAlActual = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConAnioMenorAlActual);
        Boolean esValidoConAnioMayorAlActual = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConAnioMayorAlActual);

        assertThat(esValidoConAnioIgualAlActual, is(true));
        assertThat(esValidoConAnioMenorAlActual, is(true));
        assertThat(esValidoConAnioMayorAlActual, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoContengaLetras(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConNumeroQuipuxConLetras);
        assertThat(esValido, is(false));
    }


    @Test
    public void debeVerificarQueNumeroDeQuipuxNoContengaCaracteresEspeciales(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionQuipuxConNumeroQuipuxConCaracteresEspeciales);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoTengaMasDe6Digitos(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionConNumeroQuipuxDeLongitudMayorA6);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueSiglasDeMemorandoInternoSeanMI(){
        Boolean esValido = QuipuxValidator.isValidoNumeroAutorizacionQuipux(numeroAutorizacionConSiglasMemorandoInternoIncorrectas);
        assertThat(esValido, is(false));
    }
}
