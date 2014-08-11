package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class ServicioCifradoTest {

    private ServicioCifrado servicioCifrado;
    private String cadenaPlana = "Hola Mundo";
    private String cadenaCifrada;

    @Before
    public void setUp() throws CifradoErroneoException {
        servicioCifrado = new ServicioCifrado();
        cadenaCifrada = null;
    }

    @Test
    public void debeCifrarCadenaDeCaracteresYNoEsNulo() throws CifradoErroneoException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);

        assertThat(cadenaCifrada, is(notNullValue()));
    }

    @Test
    public void debeCifrarCadenaYNoEsVacio() throws CifradoErroneoException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);

        assertThat(cadenaCifrada.isEmpty(), is(false));
    }

    @Test
    public void debeDescifrarCadenaYNoEsNulo() throws CifradoErroneoException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada,is(notNullValue()));
    }

    @Test
    public void debeDescifrarCadenaYNoEsVacio() throws CifradoErroneoException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada.isEmpty(), is(false));
    }

    @Test
    public void debeCadenaDescifradaSerIgualACadenaPlana() throws CifradoErroneoException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada, is(cadenaPlana));
    }

    @Test
    public void alCifrarYDescifrar2CadenasConElMismoValorElResultadoDebeSerElMismo() throws CifradoErroneoException {
        String cadena1 = "paraCifrar";
        String cadena2 = "paraCifrar";

        String cifradoCadena1 = servicioCifrado.cifrar(cadena1);

        ServicioCifrado servicioCifrado1 = new ServicioCifrado();
        String cifradoCadena2 = servicioCifrado1.cifrar(cadena2);

        String cadenaDescifrada1 = servicioCifrado.descifrar(cifradoCadena1);
        String cadenaDescifrada2 = servicioCifrado1.descifrar(cifradoCadena2);

        assertThat(cadenaDescifrada1.equals(cadenaDescifrada2), is(true));
    }
}
