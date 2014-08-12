package ec.gob.senescyt.usuario.core;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "funciones")
public class Funcion extends Rol {

    public Funcion(String nombre) {
        super(nombre);
    }
}
