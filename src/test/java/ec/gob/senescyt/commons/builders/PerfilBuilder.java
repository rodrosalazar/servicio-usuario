package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;

import java.util.List;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

public class PerfilBuilder {

    private static PerfilBuilder perfilBuilder;
    private static final List<Acceso> ACCESOS = newArrayList(Acceso.CREAR, Acceso.ELIMINAR);
    public String nombre = "atencion ciudadana";
    public List<Permiso> permisos = newArrayList(new Permiso(1l, 2l, ACCESOS));

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