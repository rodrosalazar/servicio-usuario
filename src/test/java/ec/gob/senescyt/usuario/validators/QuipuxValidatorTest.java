package ec.gob.senescyt.usuario.validators;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
    private QuipuxValidator quipuxValidator;

    @Before
    public void setUp() throws Exception {
        quipuxValidator = new QuipuxValidator();

    }

    @Test
    public void debeIndicarCuandoFormatoNumeroQuipuxEsValido() throws Exception {
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxValido,null);

        assertThat(esValido, is(true));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxDebeTenerNombreInstitucionSENESCYT(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxSinNombreInstitucionSENESCYT, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaSoloLetrasEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConSiglasUnidadConNumeros, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaMinimo2CaracteresEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConSiglasMenoresADosCaracteres, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaMaximo6LetrasEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConSiglasDeMasDe6Letras, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxNoContengaCaracteresEspecialesEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConSiglasConCaracteresEspeciales, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxNoContengaLetras(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConAnioConLetras, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxNoContengaCaracteresEspeciales(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConAnioConCaracteresEspeciales, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxSeaMenorOIgualAlAnioActual(){
        Boolean esValidoConAnioIgualAlActual = quipuxValidator.isValid(numeroAutorizacionQuipuxConAnioIgualAlActual, null);
        Boolean esValidoConAnioMenorAlActual = quipuxValidator.isValid(numeroAutorizacionQuipuxConAnioMenorAlActual, null);
        Boolean esValidoConAnioMayorAlActual = quipuxValidator.isValid(numeroAutorizacionQuipuxConAnioMayorAlActual, null);

        assertThat(esValidoConAnioIgualAlActual, is(true));
        assertThat(esValidoConAnioMenorAlActual, is(true));
        assertThat(esValidoConAnioMayorAlActual, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoContengaLetras(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConNumeroQuipuxConLetras, null);
        assertThat(esValido, is(false));
    }


    @Test
    public void debeVerificarQueNumeroDeQuipuxNoContengaCaracteresEspeciales(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionQuipuxConNumeroQuipuxConCaracteresEspeciales, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoTengaMasDe6Digitos(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionConNumeroQuipuxDeLongitudMayorA6, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueSiglasDeMemorandoInternoSeanMI(){
        Boolean esValido = quipuxValidator.isValid(numeroAutorizacionConSiglasMemorandoInternoIncorrectas, null);
        assertThat(esValido, is(false));
    }
}
