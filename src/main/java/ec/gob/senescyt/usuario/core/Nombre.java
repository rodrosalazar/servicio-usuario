package ec.gob.senescyt.usuario.core;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by njumbo on 25/06/14.
 */
@Embeddable
public class Nombre implements Serializable{

    @Column
    @NotEmpty
    private String primerNombre;

    @Column
    private String segundoNombre;

    @Column
    @NotEmpty
    private String primerApellido;

    @Column
    private String segundoApellido;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Nombre nombre = (Nombre) o;

        if (primerApellido != null ? !primerApellido.equals(nombre.primerApellido) : nombre.primerApellido != null)
            return false;
        if (primerNombre != null ? !primerNombre.equals(nombre.primerNombre) : nombre.primerNombre != null)
            return false;
        if (segundoApellido != null ? !segundoApellido.equals(nombre.segundoApellido) : nombre.segundoApellido != null)
            return false;
        if (segundoNombre != null ? !segundoNombre.equals(nombre.segundoNombre) : nombre.segundoNombre != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    private Nombre() {}

    public Nombre(String primerNombre, String segundoNombre, String primerApellido, String segundoApellido) {
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }
}
