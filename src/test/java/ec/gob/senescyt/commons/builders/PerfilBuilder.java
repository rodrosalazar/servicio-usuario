package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PerfilBuilder {
    private static PerfilBuilder perfilBuilder;
    public String nombre = "atencion ciudadana";
    public List<Permiso> permisos = new ArrayList<>();

    public static PerfilBuilder nuevoPerfil() {
        perfilBuilder = new PerfilBuilder();
        return perfilBuilder;
    }

    public Perfil generar() {
        return new Perfil(nombre, permisos);
    }

    public PerfilBuilder con(Consumer<PerfilBuilder> consumer){
        consumer.accept(perfilBuilder);
        return perfilBuilder;
    }
}