package ec.gob.senescyt.biblioteca.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class ContraseniaToken {
    public static final String REGEX_MAYUSCULA = "^(?=.*[A-Z]).*$";
    public static final String REGEX_MINUSCULA = "^(?=.*[a-z]).*$";
    public static final String REGEX_NUMERO = "^(?=.*\\d).*$";

    @NotEmpty
    String idToken;

    @Pattern.List({
            @Pattern(regexp = ContraseniaToken.REGEX_MAYUSCULA, message = "{ec.gob.senescyt.error.contraseniaMayuscula}"),
            @Pattern(regexp = ContraseniaToken.REGEX_MINUSCULA, message = "{ec.gob.senescyt.error.contraseniaMinuscula}"),
            @Pattern(regexp = ContraseniaToken.REGEX_NUMERO, message = "{ec.gob.senescyt.error.contraseniaNumero}")
    })
    @Length(min = 6, max = 15, message = "{ec.gob.senescyt.error.contraseniaLongitud}")
    @NotEmpty
    private String contrasenia;

    private ContraseniaToken() {
    }

    public ContraseniaToken(String contrasenia, String idToken) {
        this.contrasenia = contrasenia;
        this.idToken = idToken;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getIdToken() {
        return idToken;
    }
}