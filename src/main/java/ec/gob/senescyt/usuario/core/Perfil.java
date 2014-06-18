package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "perfil")
public class Perfil {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_seq_gen")
    @SequenceGenerator(name = "perfil_seq_gen", sequenceName = "perfil_id_seq")
    private long id;

    @JsonProperty
    @Column
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
