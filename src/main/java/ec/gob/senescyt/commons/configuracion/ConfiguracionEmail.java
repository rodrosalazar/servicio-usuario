package ec.gob.senescyt.commons.configuracion;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
public class ConfiguracionEmail {

    @NotNull
    @JsonProperty
    private String servidor;

    @NotNull
    @JsonProperty
    private int puerto;

    @NotNull
    @JsonProperty
    private boolean isDebugModeActivo;

    @NotNull
    @JsonProperty
    private boolean isSSLRequerido;

    @NotNull
    @JsonProperty
    private String nombreUsuario;

    @NotNull
    @JsonProperty
    private String clave;

    @NotNull
    @JsonProperty
    private String correoRemitente;

    @NotNull
    @JsonProperty
    private String nombreRemitente;

    public String getServidor() {
        return servidor;
    }

    public int getPuerto() {
        return puerto;
    }

    public boolean isDebugModeActivo() {
        return isDebugModeActivo;
    }

    public boolean isSSLRequerido() {
        return isSSLRequerido;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getClave() {
        return clave;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }
}
