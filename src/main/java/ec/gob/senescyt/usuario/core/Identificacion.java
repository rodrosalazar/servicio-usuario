package ec.gob.senescyt.usuario.core;

import ec.gob.senescyt.usuario.enums.TipoDocumento;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class Identificacion implements Serializable {

    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoDocumento tipoDocumento;

    @NotEmpty
    @Length(max = 20)
    private String numeroIdentificacion;

    private Identificacion(){};

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Identificacion that = (Identificacion) o;

        if (numeroIdentificacion != null ? !numeroIdentificacion.equals(that.numeroIdentificacion) : that.numeroIdentificacion != null) {
            return false;
        }
        return tipoDocumento == that.tipoDocumento;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public Identificacion(TipoDocumento tipoDocumento, String numeroIdentificacion) {
        this.tipoDocumento = tipoDocumento;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

}
