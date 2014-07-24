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
    @Length(max = 255, message = "{ec.gob.senescyt.error.direccion.callePrincipal}")
    private String callePrincipal;
    @NotEmpty
    @Length(max = 50, message = "{ec.gob.senescyt.error.direccion.numeroCasa}")
    private String numeroCasa;
    @NotEmpty
    @Length(max = 255, message = "{ec.gob.senescyt.error.direccion.calleSecundaria}")
    private String calleSecundaria;
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

    public Direccion(String callePrincipal, String numeroCasa, String calleSecundaria, String idProvincia,
                     String idCanton, String idParroquia) {

        this.callePrincipal = callePrincipal;
        this.numeroCasa = numeroCasa;
        this.calleSecundaria = calleSecundaria;
        this.idProvincia = idProvincia;
        this.idCanton = idCanton;
        this.idParroquia = idParroquia;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public String getNumeroCasa() {
        return numeroCasa;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
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

        if (callePrincipal != null ? !callePrincipal.equals(direccion.callePrincipal) : direccion.callePrincipal != null)
            return false;
        if (calleSecundaria != null ? !calleSecundaria.equals(direccion.calleSecundaria) : direccion.calleSecundaria != null)
            return false;
        if (idCanton != null ? !idCanton.equals(direccion.idCanton) : direccion.idCanton != null) return false;
        if (idParroquia != null ? !idParroquia.equals(direccion.idParroquia) : direccion.idParroquia != null)
            return false;
        if (idProvincia != null ? !idProvincia.equals(direccion.idProvincia) : direccion.idProvincia != null)
            return false;
        if (numeroCasa != null ? !numeroCasa.equals(direccion.numeroCasa) : direccion.numeroCasa != null) return false;

        return true;
    }
}
