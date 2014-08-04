package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

@Entity
public class Credencial {

    public static final String REGEX_MAYUSCULA = "^(?=.*[A-Z]).*$";
    public static final String REGEX_MINUSCULA = "^(?=.*[a-z]).*$";
    public static final String REGEX_NUMERO = "^(?=.*\\d).*$";
    @Id
    @NotEmpty
    private String nombreUsuario;

    @NotEmpty
    @Length(min = 6, max = 15, message = "{ec.gob.senescyt.error.contraseniaLongitud}")
    @Pattern.List({
            @Pattern(regexp = REGEX_MAYUSCULA, message = "{ec.gob.senescyt.error.contraseniaMayuscula}"),
            @Pattern(regexp = REGEX_MINUSCULA, message = "{ec.gob.senescyt.error.contraseniaMinuscula}"),
            @Pattern(regexp = REGEX_NUMERO, message = "{ec.gob.senescyt.error.contraseniaNumero}")
    })
    private String contrasenia;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idToken;

    private Credencial() {
    }

    public Credencial(String nombreUsuario, String contrasenia) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    public Credencial(String nombreUsuario, String contrasenia, String idToken) {
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.idToken = idToken;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getIdToken() {
        return idToken;
    }
}
