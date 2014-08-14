package ec.gob.senescyt.usuario.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permisos")
public class Permiso extends Entidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String funcion;
    private Acceso acceso;
    private String modulo;

    public Permiso() {
        // Por Jackson
    }

    public Permiso(String modulo, String funcion, Acceso acceso) {
        this.modulo = modulo;
        this.funcion = funcion;
        this.acceso = acceso;
    }

    public String getFuncion() {
        return funcion;
    }

    public Acceso getAcceso() {
        return acceso;
    }

    public Long getId() {
        return id;
    }

    public String getModulo() {
        return modulo;
    }
}
