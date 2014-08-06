package ec.gob.senescyt.usuario.validators;

import org.apache.commons.lang.math.NumberUtils;

import java.util.ArrayList;
import java.util.List;

public class CedulaValidator {

    private static final int MULTIPLICADOR = 2;
    private static final int TOTAL_PROVINCIAS = 24;
    private static final int LONGITUD_REQUERIDA = 10;
    private static final int LIMITE_DECENA = 10;

    public boolean isValidaCedula(final String cedula) {
        // si no tiene 10 digitos es invalida
        if (cedula.length() != LONGITUD_REQUERIDA) {
            return false;
        }

        if(!NumberUtils.isNumber(cedula)){
            return false;
        }

        if(!isValidaProvincia(cedula)) {
            return false;
        }

        Integer verificadorPrivado = Integer.parseInt(cedula.charAt(2) + "");

        if(verificadorPrivado > 5 && verificadorPrivado < 9 ){
            return false;
        }

        if (verificadorPrivado.equals(Integer.valueOf(9))) {
            return verificarPersonaJuridica(cedula);
        }

        return verificarPersonaNatural(cedula);
    }

    private boolean isValidaProvincia(String cedula) {
        String provincia = cedula.substring(0, 2);
        int numeroProvincia = Integer.parseInt(provincia);
        return !(numeroProvincia <= 0 || numeroProvincia > TOTAL_PROVINCIAS);
    }

    private static boolean verificarPersonaNatural(String cedula) {
        int totalEven = 0; // pares
        int totalOdd = 0; // impares

        // la ultima posicion no cuenta solo es verificador
        int totalValidNumbers = cedula.length() - 1;
        int verifier = Integer.parseInt(cedula.charAt(9) + "");

        for (int i = 0; i < totalValidNumbers; i++) {
            int digit = Integer.parseInt(cedula.charAt(i) + "");
            if (esPar(i)) {// si son pares
                int product = digit * MULTIPLICADOR;
                if (product >= LIMITE_DECENA) {
                    product = product - 9;
                }
                totalEven += product;
            } else { // si son impares
                totalOdd += digit;
            }
        }

        int total = totalOdd + totalEven;

        String totalString = String.valueOf(total + 10);

        // se verifica la decena superior
        if (esMultiDigito(totalString)) {
            int first = Integer.parseInt(totalString.charAt(0) + "");
            total = Integer.parseInt(first + "0") - total;
            if (total == LIMITE_DECENA) {
                total = 0;
            }
        }

        int result = total;

        // si el numero verificador es igual al resultado del algoritmo
        // entonces es una cedula valida

        return result == verifier;
    }

    private static boolean esMultiDigito(String totalString) {
        return totalString.length() > 1;
    }

    private static boolean esPar(int numero) {
        return numero % 2 == 0;
    }

    private static boolean verificarPersonaJuridica(String cedula) {
        int[] coeficientes = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
        int constante = 11;

        // verifica que el último dígito de la cédula sea válido
        List<Integer> digitos = new ArrayList<>();
        int suma = 0;

        // Asignamos el string a un array
        for (int i = 0; i < cedula.length(); i++) {
            digitos.add(Integer.parseInt(cedula.charAt(i) + ""));
        }

        for (int i = 0; i < digitos.size() - 1; i++) {
            digitos.set(i, digitos.get(i) * coeficientes[i]);
            suma += digitos.get(i);
        }

        int aux, resp;

        aux = suma % constante;
        resp = constante - aux;

        resp = (resp == 10) ? 0 : resp;

        return resp == digitos.get(9);
    }
}
