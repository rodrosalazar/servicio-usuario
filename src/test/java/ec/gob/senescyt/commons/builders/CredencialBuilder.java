package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.usuario.core.Credencial;

import java.util.function.Consumer;

public class CredencialBuilder {
    private static CredencialBuilder credencialBuilder;

    public String nombreUsuario = "nombreUsuarioValido";
    public String contrasenia = "Clave567";
    public String idToken = "e590f1a6-517d-4c52-95ad-32c05504a2dc";


    public Credencial generar() {
        return new Credencial(nombreUsuario, contrasenia, idToken);
    }

    public static CredencialBuilder nuevaCredencial() {
        credencialBuilder = new CredencialBuilder();
        return credencialBuilder;
    }

    public CredencialBuilder con(Consumer<CredencialBuilder> consumidor) {
        consumidor.accept(credencialBuilder);
        return credencialBuilder;
    }
}