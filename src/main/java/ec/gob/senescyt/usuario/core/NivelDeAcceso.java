package ec.gob.senescyt.usuario.core;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

@Entity
@Table(name = "niveles_de_acceso")
public class NivelDeAcceso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private final String nombre;

    public NivelDeAcceso(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }
}
