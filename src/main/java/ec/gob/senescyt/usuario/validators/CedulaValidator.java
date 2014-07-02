package ec.gob.senescyt.usuario.validators;

import org.apache.commons.lang.math.NumberUtils;

public class CedulaValidator {

    static final int MULT = 2;
    static final int TOTAL_PROVINCES = 24;

    public boolean isValidaCedula(final String cedula) {
        // si no tiene 10 digitos es invalida
        if (cedula.length() != 10) {
            return false;
        }

        if(!NumberUtils.isNumber(cedula)){
            return false;
        }

        String province = cedula.substring(0, 2);

        // si sus dos primeros digitos son invalidos
        int numeroProvincia = Integer.parseInt(province);
        if (numeroProvincia <= 0 || numeroProvincia > TOTAL_PROVINCES) {
            return false;
        }

        Integer verificadorPrivado = Integer.parseInt(cedula.charAt(2) + "");

        if(verificadorPrivado > 5 && verificadorPrivado < 9 ){
            return false;
        }

        if (verificadorPrivado.equals(new Integer(9))) {
            return verificarPersonaJuridica(cedula);
        }

        return verificarPersonaNatural(cedula);
    }

    private static boolean verificarPersonaNatural(String cedula) {
        int totalEven = 0; // pares
        int totalOdd = 0; // impares

        // la ultima posicion no cuenta solo es verificador
        int totalValidNumbers = cedula.length() - 1;
        int verifier = Integer.parseInt(cedula.charAt(9) + "");

        for (int i = 0; i < totalValidNumbers; i++) {
            int digit = Integer.parseInt(cedula.charAt(i) + "");
            if (i % 2 == 0) {// si son pares
                int product = digit * MULT;
                if (product > 9) {
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
        if (totalString.length() > 1) {
            int first = Integer.parseInt(totalString.charAt(0) + "");
            total = Integer.parseInt(first + "0") - total;
            if (total == 10) {
                total = 0;
            }
        }

        int result = total;

        // si el numero verificador es igual al resultado del algoritmo
        // entonces es una cedula valida

        return result == verifier;
    }

    private static boolean verificarPersonaJuridica(String cedula) {
        int[] coeficientes = { 4, 3, 2, 7, 6, 5, 4, 3, 2 };
        int constante = 11;

        // verifica que el último dígito de la cédula sea válido
        int[] d = new int[10];
        int suma = 0;

        // Asignamos el string a un array
        for (int i = 0; i < cedula.length(); i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }

        for (int i = 0; i < d.length - 1; i++) {
            d[i] = d[i] * coeficientes[i];
            suma += d[i];
        }

        int aux, resp;

        aux = suma % constante;
        resp = constante - aux;

        resp = (resp == 10) ? 0 : resp;

        return resp == d[9];
    }
}
