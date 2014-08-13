package ec.gob.senescyt.usuario.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permisos")
public class Permiso extends Entidad {

    private String nombre;
    private Acceso acceso;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Permiso() {
        // Por Jackson
    }

    public Permiso(String nombre, Acceso acceso) {
        this.nombre = nombre;
        this.acceso = acceso;
    }

    public String getNombre() {
        return nombre;
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public Long getId() {
        return id;
    }
}
