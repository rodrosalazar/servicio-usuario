package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long moduloId;

    @Column
    private long funcionId;

    @ElementCollection(targetClass = Acceso.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "accesos")
    @Column(name = "accesos")
    private List<Acceso> accesos;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonBackReference
    private Perfil perfil;

    private Permiso() {}

    public Permiso (long moduloId,long funcionId, List<Acceso> accesos) {
        this.moduloId = moduloId;
        this.funcionId = funcionId;
        this.accesos = accesos;
    }

    public long getId() {
        return id;
    }

    public long getModuloId() {
        return moduloId;
    }

    public long getFuncionId() {
        return funcionId;
    }

    public List<Acceso> getAccesos() {
        return accesos;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    @JsonIgnore
    public boolean isValido(){
        return this.getAccesos() != null && !this.getAccesos().isEmpty();
    }
}
