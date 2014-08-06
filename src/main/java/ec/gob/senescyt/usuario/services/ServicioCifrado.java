package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.commons.cifrado.GeneradorClavesSecretas;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;

public class ServicioCifrado {

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
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }


    public String cifrar(String cadenaPlana) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(cadenaPlana.getBytes("UTF-8"));

        return new Base64().encodeAsString(encryptedTextBytes);
    }

    public String descifrar(String cadenaCifrada) throws Exception {
        byte[] cadenaCifradaBytes = new Base64().decodeBase64(cadenaCifrada);
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,new IvParameterSpec(ivBytes));

        byte[] cadenaDescrifradaBytes = null;
        try {
            cadenaDescrifradaBytes = cipher.doFinal(cadenaCifradaBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return new String(cadenaDescrifradaBytes);
    }
}
