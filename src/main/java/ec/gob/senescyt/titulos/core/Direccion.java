package ec.gob.senescyt.titulos.core;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "direcciones")
@SuppressWarnings("PMD.CyclomaticComplexity")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Direccion direccion = (Direccion) o;

        if (id != direccion.id) { return false; }
        if (!direccionCompleta.equals(direccion.direccionCompleta)) { return false; }
        if (!idCanton.equals(direccion.idCanton)) { return false; }
        if (!idParroquia.equals(direccion.idParroquia)) { return false; }
        if (!idProvincia.equals(direccion.idProvincia)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
