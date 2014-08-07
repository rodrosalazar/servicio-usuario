package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.commons.cifrado.GeneradorClavesSecretas;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

public class ServicioCifrado {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServicioCifrado.class);

    public static final String METODO_CIFRADO_AES = "AES/CBC/PKCS5Padding";
    private byte[] ivBytes;
    private GeneradorClavesSecretas generadorClavesSecretas = new GeneradorClavesSecretas();
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    public ServicioCifrado() {
        try {
            secretKeySpec = generadorClavesSecretas.generarClaveSecretaSpec();
            cipher = Cipher.getInstance(METODO_CIFRADO_AES);
        } catch (IOException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        } catch (NoSuchPaddingException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }

    }


    public String cifrar(String cadenaPlana) throws InvalidKeyException, InvalidParameterSpecException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(cadenaPlana.getBytes("UTF-8"));

        return new Base64().encodeAsString(encryptedTextBytes);
    }

    public String descifrar(String cadenaCifrada) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] cadenaCifradaBytes = new Base64().decodeBase64(cadenaCifrada);

        cipher = Cipher.getInstance(METODO_CIFRADO_AES);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,new IvParameterSpec(ivBytes));

        try {
            return new String(cipher.doFinal(cadenaCifradaBytes), "UTF-8");
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        } catch (BadPaddingException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
