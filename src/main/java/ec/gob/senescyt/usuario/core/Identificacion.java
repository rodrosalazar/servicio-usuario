package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ec.gob.senescyt.usuario.enums.TipoDocumentoEnum;
import ec.gob.senescyt.usuario.validators.CedulaValidator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class Identificacion implements Serializable {

    @Column
    @Enumerated(EnumType.STRING)
    private TipoDocumentoEnum tipoDocumento;

    @Column
    private String numeroIdentificacion;

    private Identificacion(){};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identificacion that = (Identificacion) o;

        if (numeroIdentificacion != null ? !numeroIdentificacion.equals(that.numeroIdentificacion) : that.numeroIdentificacion != null)
            return false;
        if (tipoDocumento != that.tipoDocumento) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public Identificacion(TipoDocumentoEnum tipoDocumento, String numeroIdentificacion) {
        this.tipoDocumento = tipoDocumento;
        this.numeroIdentificacion = numeroIdentificacion;
    }

    public TipoDocumentoEnum getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    @JsonIgnore
    public boolean isValid(CedulaValidator cedulaValidator) {
        return tipoDocumento == TipoDocumentoEnum.PASAPORTE
                || cedulaValidator.isValidaCedula(numeroIdentificacion);
    }
}
