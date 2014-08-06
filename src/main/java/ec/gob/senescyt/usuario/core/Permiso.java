package ec.gob.senescyt.usuario.core;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private Long moduloId;

    @NotNull
    private Long funcionId;

    @ElementCollection(targetClass = Acceso.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "accesos")
    @Column(name = "accesos")
    @NotEmpty
    @Valid
    private List<Acceso> accesos;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    @JsonBackReference
    private Perfil perfil;

    private Permiso() {}

    public Permiso (Long moduloId, Long funcionId, List<Acceso> accesos) {
        this.moduloId = moduloId;
        this.funcionId = funcionId;
        this.accesos = accesos;
    }

    public long getId() {
        return id;
    }

    public Long getModuloId() {
        return moduloId;
    }

    public Long getFuncionId() {
        return funcionId;
    }

    public List<Acceso> getAccesos() {
        return accesos;
    }

    public Perfil getPerfil() {
        return perfil;
    }

}
