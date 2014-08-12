package ec.gob.senescyt.usuario.core;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "niveles_de_acceso")
public class NivelDeAcceso extends Rol {

    public NivelDeAcceso(String nombre) {
        super(nombre);
    }
}
