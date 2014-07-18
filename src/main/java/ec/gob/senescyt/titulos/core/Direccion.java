package ec.gob.senescyt.titulos.core;

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
    private String callePrincipal;
    @NotEmpty
    private String numeroCasa;
    @NotEmpty
    private String calleSecundaria;
    @NotEmpty
    private String idProvincia;
    @NotEmpty
    private String idCanton;
    @NotEmpty
    private String idParroquia;
    @NotEmpty
    private String idPais;

    private Direccion() { }

    public Direccion(String callePrincipal, String numeroCasa, String calleSecundaria, String idProvincia,
                     String idCanton, String idParroquia, String idPais) {

        this.callePrincipal = callePrincipal;
        this.numeroCasa = numeroCasa;
        this.calleSecundaria = calleSecundaria;
        this.idProvincia = idProvincia;
        this.idCanton = idCanton;
        this.idParroquia = idParroquia;
        this.idPais = idPais;
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

    public String getIdPais() {
        return idPais;
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
        if (idPais != null ? !idPais.equals(direccion.idPais) : direccion.idPais != null) return false;
        if (idParroquia != null ? !idParroquia.equals(direccion.idParroquia) : direccion.idParroquia != null)
            return false;
        if (idProvincia != null ? !idProvincia.equals(direccion.idProvincia) : direccion.idProvincia != null)
            return false;
        if (numeroCasa != null ? !numeroCasa.equals(direccion.numeroCasa) : direccion.numeroCasa != null) return false;

        return true;
    }
}
