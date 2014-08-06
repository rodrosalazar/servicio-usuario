package ec.gob.senescyt.usuario.services;

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
    public void setUp(){
        servicioCifrado = new ServicioCifrado();
        cadenaCifrada = null;
    }

    @Test
    public void debeCifrarCadenaDeCaracteresYNoEsNulo() throws Exception{
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);

        assertThat(cadenaCifrada, is(notNullValue()));
    }

    @Test
    public void debeCifrarCadenaYNoEsVacio() throws Exception{
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);

        assertThat(cadenaCifrada.isEmpty(), is(false));
    }

    @Test
    public void debeDescifrarCadenaYNoEsNulo() throws Exception {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada,is(notNullValue()));
    }

    @Test
    public void debeDescifrarCadenaYNoEsVacio() throws Exception {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada.isEmpty(), is(false));
    }

    @Test
    public void debeCadenaDescifradaSerIgualACadenaPlana() throws Exception {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada, is(cadenaPlana));
    }
}
