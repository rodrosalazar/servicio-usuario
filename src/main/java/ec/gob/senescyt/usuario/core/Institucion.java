package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@JsonPropertyOrder({"id", "nombre", "regimenId", "regimen", "estadoId", "estado", "categoriaId", "categoria"})
@Entity
@Table(name = "instituciones")
public class Institucion {

    @Id
    private long id;
    private String nombre;
    private long regimenId;
    private String regimen;
    private long estadoId;
    private String estado;
    @Column(nullable = true)
    private Long categoriaId;
    private String categoria;

    private Institucion() {}

    public Institucion(long id, String nombre, long regimenId, String regimen, long estadoId, String estado, long categoriaId, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.regimenId = regimenId;
        this.regimen = regimen;
        this.estadoId = estadoId;
        this.estado = estado;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public long getRegimenId() {
        return regimenId;
    }

    public String getRegimen() {
        return regimen;
    }

    public long getEstadoId() {
        return estadoId;
    }

    public String getEstado() {
        return estado;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoria() {
        return categoria;
    }
}
