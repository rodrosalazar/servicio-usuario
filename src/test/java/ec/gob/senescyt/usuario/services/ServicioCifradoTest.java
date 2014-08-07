package ec.gob.senescyt.usuario.services;

import org.junit.Before;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

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
    public void debeCifrarCadenaDeCaracteresYNoEsNulo() throws IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);

        assertThat(cadenaCifrada, is(notNullValue()));
    }

    @Test
    public void debeCifrarCadenaYNoEsVacio() throws IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);

        assertThat(cadenaCifrada.isEmpty(), is(false));
    }

    @Test
    public void debeDescifrarCadenaYNoEsNulo() throws IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada,is(notNullValue()));
    }

    @Test
    public void debeDescifrarCadenaYNoEsVacio() throws IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada.isEmpty(), is(false));
    }

    @Test
    public void debeCadenaDescifradaSerIgualACadenaPlana() throws IllegalBlockSizeException, BadPaddingException, InvalidParameterSpecException, InvalidKeyException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException {
        cadenaCifrada = servicioCifrado.cifrar(cadenaPlana);
        String cadenaDescifrada = servicioCifrado.descifrar(cadenaCifrada);

        assertThat(cadenaDescifrada, is(cadenaPlana));
    }
}
