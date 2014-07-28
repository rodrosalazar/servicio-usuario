package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre", "codigoTipo", "codigoPais"})
@Entity
@Table(name="universidades_extranjeras")
public class UniversidadExtranjera {

    @Id
    private String id;
    private String nombre;
    private String codigoTipo;
    private String codigoPais;


    public UniversidadExtranjera() {
    }

    public UniversidadExtranjera(String id, String nombre, String codigoTipo, String codigoPais) {
        this.id = id;
        this.nombre = nombre;
        this.codigoTipo = codigoTipo;
        this.codigoPais = codigoPais;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoTipo() {
        return codigoTipo;
    }

    public String getCodigoPais() {
        return codigoPais;
    }
}
