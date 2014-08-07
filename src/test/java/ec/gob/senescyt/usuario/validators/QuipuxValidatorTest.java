package ec.gob.senescyt.usuario.validators;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@SuppressWarnings("PMD.TooManyMethods")
public class QuipuxValidatorTest {

    private static final String QUIPUX_VALIDO = "SENESCYT-DFAPO-2014-65946-MI";
    private static final String QUIPUX_SIN_NOMBRE_INSTITUCION_SENESCYT = "IESS-DFAPO-2014-65946-MI";
    private static final String QUIPUX_CON_SIGLAS_UNIDAD_CON_NUMEROS = "SENESCYT-DFAP1-2014-65946-MI";
    private static final String QUIPUX_CON_SIGLAS_DE_MAS_DE_6_LETRAS = "SENESCYT-DFAPOTT-2014-65946-MI";
    private static final String QUIPUX_CON_SIGLAS_CON_CARACTERES_ESPECIALES = "SENESCYT-DFAPOT&-2014-65946-MI";
    private static final String QUIPUX_CON_SIGLAS_MENORES_A_DOS_CARACTERES = "SENESCYT-D-2014-65946-MI";
    private static final String QUIPUX_CON_ANIO_CON_LETRAS = "SENESCYT-DFAPOT-201a-65946-MI";
    private static final String QUIPUX_CON_ANIO_IGUAL_AL_ACTUAL = "SENESCYT-DFAPO-2014-65946-MI";
    private static final String QUIPUX_CON_ANIO_MENOR_AL_ACTUAL = "SENESCYT-DFAPO-1998-65946-MI";
    private static final String QUIPUX_CON_ANIO_MAYOR_AL_ACTUAL = "SENESCYT-DFAPO-2015-65946-MI";
    private static final String QUIPUX_CON_ANIO_CON_CARACTERES_ESPECIALES = "SENESCYT-DFAPOT-201&-65946-MI";
    private static final String QUIPUX_CON_LETRAS = "SENESCYT-DFAPO-2014-659a6-MI";
    private static final String QUIPUX_CON_CARACTERES_ESPECIALES = "SENESCYT-DFAPO-2014-&5946-MI";
    private static final String QUIPUX_DE_LONGITUD_MAYOR_A_6 = "SENESCYT-DFAPO-2014-6594677-MI";
    private static final String QUIPUX_CON_SIGLAS_MEMORANDO_INTERNO_INCORRECTAS = "SENESCYT-DFAPO-2014-65946-MF";
    private QuipuxValidator quipuxValidator;

    @Before
    public void setUp() {
        quipuxValidator = new QuipuxValidator();
    }

    @Test
    public void debeIndicarCuandoFormatoNumeroQuipuxEsValido() {
        Boolean esValido = quipuxValidator.isValid(QUIPUX_VALIDO,null);

        assertThat(esValido, is(true));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxDebeTenerNombreInstitucionSENESCYT(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_SIN_NOMBRE_INSTITUCION_SENESCYT, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaSoloLetrasEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_SIGLAS_UNIDAD_CON_NUMEROS, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaMinimo2CaracteresEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_SIGLAS_MENORES_A_DOS_CARACTERES, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxContengaMaximo6LetrasEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_SIGLAS_DE_MAS_DE_6_LETRAS, null);

        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroAutorizacionQuipuxNoContengaCaracteresEspecialesEnSiglasDeUnidadDepartamento(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_SIGLAS_CON_CARACTERES_ESPECIALES, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxNoContengaLetras(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_ANIO_CON_LETRAS, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxNoContengaCaracteresEspeciales(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_ANIO_CON_CARACTERES_ESPECIALES, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueAnioDeCreacionQuipuxSeaMenorOIgualAlAnioActual(){
        Boolean esValidoConAnioIgualAlActual = quipuxValidator.isValid(QUIPUX_CON_ANIO_IGUAL_AL_ACTUAL, null);
        Boolean esValidoConAnioMenorAlActual = quipuxValidator.isValid(QUIPUX_CON_ANIO_MENOR_AL_ACTUAL, null);
        Boolean esValidoConAnioMayorAlActual = quipuxValidator.isValid(QUIPUX_CON_ANIO_MAYOR_AL_ACTUAL, null);

        assertThat(esValidoConAnioIgualAlActual, is(true));
        assertThat(esValidoConAnioMenorAlActual, is(true));
        assertThat(esValidoConAnioMayorAlActual, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoContengaLetras(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_LETRAS, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoContengaCaracteresEspeciales(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_CARACTERES_ESPECIALES, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueNumeroDeQuipuxNoTengaMasDe6Digitos(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_DE_LONGITUD_MAYOR_A_6, null);
        assertThat(esValido, is(false));
    }

    @Test
    public void debeVerificarQueSiglasDeMemorandoInternoSeanMI(){
        Boolean esValido = quipuxValidator.isValid(QUIPUX_CON_SIGLAS_MEMORANDO_INTERNO_INCORRECTAS, null);
        assertThat(esValido, is(false));
    }
}
