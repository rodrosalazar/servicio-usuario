package ec.gob.senescyt.usuario.core;


import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "niveles_de_acceso")
public class NivelDeAcceso extends Rol {

    private Set<FuncionNivelDeAcceso> relacionesConFunciones;

    public NivelDeAcceso(String nombre) {
        super(nombre);
    }

    public void setRelacionesConFunciones(Set<FuncionNivelDeAcceso> relacionesConFunciones) {
        this.relacionesConFunciones = relacionesConFunciones;
    }

    public Set<FuncionNivelDeAcceso> getRelacionesConFunciones() {
        return relacionesConFunciones;
    }
}
