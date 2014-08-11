package ec.gob.senescyt.usuario.services;

import ec.gob.senescyt.commons.cifrado.GeneradorClavesSecretas;
import ec.gob.senescyt.usuario.exceptions.CifradoErroneoException;
import org.apache.commons.codec.binary.Base64;

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

public class ServicioCifrado {

    public static final String METODO_CIFRADO_AES = "AES/CBC/PKCS5Padding";
    private byte[] ivBytes;
    private GeneradorClavesSecretas generadorClavesSecretas = new GeneradorClavesSecretas();
    private SecretKeySpec secretKeySpec;
    private Cipher cipher;

    public  ServicioCifrado() throws CifradoErroneoException {
        try {
            secretKeySpec = generadorClavesSecretas.generarClaveSecretaSpec();
        } catch (IOException e) {
            throw new CifradoErroneoException(e.getMessage(), e);
        }
    }

    private Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        if (cipher == null) {
            cipher = Cipher.getInstance(METODO_CIFRADO_AES);
        }
        return cipher;
    }


    public String cifrar(String cadenaPlana) throws CifradoErroneoException {
        try {

            getCipher().init(Cipher.ENCRYPT_MODE, secretKeySpec);
            AlgorithmParameters params = cipher.getParameters();
            ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
            byte[] encryptedTextBytes = cipher.doFinal(cadenaPlana.getBytes("UTF-8"));
            return new Base64().encodeAsString(encryptedTextBytes);

        } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException |
                InvalidParameterSpecException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new CifradoErroneoException(e.getMessage(), e);
        }
    }

    public String descifrar(String cadenaCifrada) throws CifradoErroneoException {
        byte[] cadenaCifradaBytes = new Base64().decodeBase64(cadenaCifrada);

        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(ivBytes));

            return new String(cipher.doFinal(cadenaCifradaBytes), "UTF-8");
        } catch (InvalidKeyException | InvalidAlgorithmParameterException|
                BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            throw new CifradoErroneoException(e.getMessage(), e);
        }
    }
}
