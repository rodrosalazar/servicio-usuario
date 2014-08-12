package ec.gob.senescyt.usuario.core;

import java.util.Set;

public class UsuarioPerfil extends Rol {
    private Set<Modulo> modulos;

    public UsuarioPerfil(String nombre) {
        super(nombre);
    }

    public void setModulos(Set<Modulo> modulos) {
        this.modulos = modulos;
    }

    public Set<Modulo> getModulos() {
        return modulos;
    }
}
