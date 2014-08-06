package ec.gob.senescyt.titulos.core;

import ec.gob.senescyt.usuario.enums.TipoDocumento;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CEDULA")
public class Cedula extends Identificacion {
    private Cedula() {
        super();
    }

    public Cedula(String numeroIdentificacion) {
        super(numeroIdentificacion, TipoDocumento.CEDULA);
    }
}
