package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.biblioteca.dto.ContraseniaToken;

import java.util.function.Consumer;

public class ContraseniaTokenBuilder {
    private static ContraseniaTokenBuilder contraseniaTokenBuilder;

    public String contrasenia = "Clave567";
    public String idToken = "e590f1a6-517d-4c52-95ad-32c05504a2dc";


    public ContraseniaToken generar() {
        return new ContraseniaToken(contrasenia, idToken);
    }

    public static ContraseniaTokenBuilder nuevaContraseniaToken() {
        contraseniaTokenBuilder = new ContraseniaTokenBuilder();
        return contraseniaTokenBuilder;
    }

    public ContraseniaTokenBuilder con(Consumer<ContraseniaTokenBuilder> consumidor) {
        consumidor.accept(contraseniaTokenBuilder);
        return contraseniaTokenBuilder;
    }
}