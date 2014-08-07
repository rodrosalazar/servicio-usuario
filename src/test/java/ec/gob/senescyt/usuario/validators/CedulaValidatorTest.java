package ec.gob.senescyt.usuario.validators;


import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CedulaValidatorTest {

    private CedulaValidator cedulaValidator;

    @Before
    public void setUp(){
       cedulaValidator = new CedulaValidator();
    }

    @Test
    public void debeVerificarQueCedulaSeaCorrectaConAlgoritmoDelUltimoDigito(){

        String cedulaCorrecta = "1718642174";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaCorrecta);

        assertThat(esValidaCedula, is(true));
    }

    @Test
    public void debeVerificarQueCedulaSeaIncorrectaCuandoElNumeroDeProvinciaSeaIncorrecto(){
        String cedulaConProvincia0 = "0000000000";
        String cedulaConProvincia25 = "2500000000";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaConProvincia0);
        assertThat(esValidaCedula, is(false));

        esValidaCedula = cedulaValidator.isValidaCedula(cedulaConProvincia25);
        assertThat(esValidaCedula, is(false));
    }

    @Test
    public void debeVerificarQueCedulaVaciaNoEsValida(){
        String cedulaVacia = "";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaVacia);

        assertThat(esValidaCedula, is(false));
    }

    @Test
    public void debeVerificarQueCedulaVaciaNoEsValidaCuandoElTercerDigitoEs6o7o8(){
        String cedulaCon6 = "1264676677";
        String cedulaCon7 = "1274676677";
        String cedulaCon8 = "1284676677";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaCon6);
        assertThat(esValidaCedula, is(false));

        esValidaCedula = cedulaValidator.isValidaCedula(cedulaCon7);
        assertThat(esValidaCedula, is(false));

        esValidaCedula = cedulaValidator.isValidaCedula(cedulaCon8);
        assertThat(esValidaCedula, is(false));
    }

    @Test
    public void debeVerificarQueCedulaConMasDe10DigitosNoEsValida() {
        String cedulaConMasDeDiezDigitos = "11111111111111111111111";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaConMasDeDiezDigitos);

        assertThat(esValidaCedula, is(false));
    }

    @Test
    public void debeVerificarQueCedulaConMenosDe10DigitosNoEsValida() {
        String cedulaConMenosDeDiezDigitos = "11";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaConMenosDeDiezDigitos);

        assertThat(esValidaCedula, is(false));
    }

    @Test
    public void debeVerificarQueCedulaConCaracteresQueNoSonNumerosNoEsValida() {
        String cedulaConCaracteresQueNosSonNumeros = "12345t78*9";

        boolean esValidaCedula = cedulaValidator.isValidaCedula(cedulaConCaracteresQueNosSonNumeros);

        assertThat(esValidaCedula, is(false));
    }

}
