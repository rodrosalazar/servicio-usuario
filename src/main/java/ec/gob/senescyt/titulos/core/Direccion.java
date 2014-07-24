package ec.gob.senescyt.titulos.core;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "direcciones")
public class Direccion {

    @Id
    private long id;
    @NotEmpty
    @Length(max = 255, message = "{ec.gob.senescyt.error.direccion.direccionCompleta}")
    private String direccionCompleta;
    @NotEmpty
    @Length(max = 2, message = "{ec.gob.senescyt.error.direccion.idProvincia}")
    private String idProvincia;
    @NotEmpty
    @Length(max = 4, message = "{ec.gob.senescyt.error.direccion.idCanton}")
    private String idCanton;
    @NotEmpty
    @Length(max = 6, message = "{ec.gob.senescyt.error.direccion.idParroquia}")
    private String idParroquia;

    private Direccion() { }

    public Direccion(String direccionCompleta, String idProvincia,
                     String idCanton, String idParroquia) {

        this.direccionCompleta = direccionCompleta;
        this.idProvincia = idProvincia;
        this.idCanton = idCanton;
        this.idParroquia = idParroquia;
    }

    public String getDireccionCompleta() {
        return direccionCompleta;
    }

    public String getIdProvincia() {
        return idProvincia;
    }

    public String getIdCanton() {
        return idCanton;
    }

    public String getIdParroquia() {
        return idParroquia;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Direccion direccion = (Direccion) o;

        if (direccionCompleta != null ? !direccionCompleta.equals(direccion.direccionCompleta) : direccion.direccionCompleta != null)
            return false;
        if (idCanton != null ? !idCanton.equals(direccion.idCanton) : direccion.idCanton != null) return false;
        if (idParroquia != null ? !idParroquia.equals(direccion.idParroquia) : direccion.idParroquia != null)
            return false;
        if (idProvincia != null ? !idProvincia.equals(direccion.idProvincia) : direccion.idProvincia != null)
            return false;

        return true;
    }
}
