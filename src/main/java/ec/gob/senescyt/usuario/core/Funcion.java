package ec.gob.senescyt.usuario.core;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "funciones")
public class Funcion extends Rol {

    private Set<FuncionNivelDeAcceso> relacionesConNivelDeAccesos;

    public Funcion(String nombre) {
        super(nombre);
    }

    public void setRelacionesConNivelDeAccesos(Set<FuncionNivelDeAcceso> relacionesConNivelDeAccesos) {
        this.relacionesConNivelDeAccesos = relacionesConNivelDeAccesos;
    }

    public Set<FuncionNivelDeAcceso> getRelacionesConNivelDeAccesos() {
        return relacionesConNivelDeAccesos;
    }
}
