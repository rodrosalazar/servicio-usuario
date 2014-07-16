package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.usuario.core.Acceso;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.core.Permiso;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class PerfilBuilder {
    private static final List<Acceso> ACCESOS = newArrayList(Acceso.CREAR, Acceso.ELIMINAR);
    private static PerfilBuilder perfilBuilder;
    private long id = 0;
    private String nombre = "atencion ciudadana";
    private List<Permiso> permisos = newArrayList(new Permiso(1, 2, ACCESOS), new Permiso(1, 3, ACCESOS));

    public static PerfilBuilder nuevoPerfil() {
        perfilBuilder = new PerfilBuilder();
        return perfilBuilder;
    }

    public PerfilBuilder conId(long id) {
        this.id = id;
        return perfilBuilder;
    }

    public PerfilBuilder conNombre(String nombre) {
        this.nombre = nombre;
        return perfilBuilder;
    }

    public PerfilBuilder conPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
        return perfilBuilder;
    }

    public Perfil generar() {
        Perfil perfil = new Perfil(id, nombre, permisos);
        return perfil;
    }
}