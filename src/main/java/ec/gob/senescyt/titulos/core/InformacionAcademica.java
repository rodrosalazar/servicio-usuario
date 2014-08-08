package ec.gob.senescyt.titulos.core;

@SuppressWarnings({"PMD.CyclomaticComplexity","PMD.NPathComplexity"})
public class InformacionAcademica {
    private long id;

    private InstitucionAcademica institucionAcademica;


    private FacultadDepartamento facultadDepartamento;


    private PersonaContacto personaContacto;


    private TituloAcademico tituloAcademico;

    private InformacionAcademica() {
    }

    public InformacionAcademica(InstitucionAcademica institucionAcademica, FacultadDepartamento facultadDepartamento,
                                PersonaContacto personaContacto, TituloAcademico tituloAcademico) {
        this.institucionAcademica = institucionAcademica;
        this.facultadDepartamento = facultadDepartamento;
        this.personaContacto = personaContacto;
        this.tituloAcademico = tituloAcademico;
    }

    public long getId() {
        return id;
    }

    public InstitucionAcademica getInstitucionAcademica() {
        return institucionAcademica;
    }

    public FacultadDepartamento getFacultadDepartamento() {
        return facultadDepartamento;
    }

    public PersonaContacto getPersonaContacto() {
        return personaContacto;
    }

    public TituloAcademico getTituloAcademico() {
        return tituloAcademico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InformacionAcademica that = (InformacionAcademica) o;

        if (id != that.id) {
            return false;
        }
        if (facultadDepartamento != null ? !facultadDepartamento.equals(that.facultadDepartamento) : that.facultadDepartamento != null) {
            return false;
        }
        if (institucionAcademica != null ? !institucionAcademica.equals(that.institucionAcademica) : that.institucionAcademica != null) {
            return false;
        }
        if (personaContacto != null ? !personaContacto.equals(that.personaContacto) : that.personaContacto != null) {
            return false;
        }
        if (tituloAcademico != null ? !tituloAcademico.equals(that.tituloAcademico) : that.tituloAcademico != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (institucionAcademica != null ? institucionAcademica.hashCode() : 0);
        result = 31 * result + (facultadDepartamento != null ? facultadDepartamento.hashCode() : 0);
        result = 31 * result + (personaContacto != null ? personaContacto.hashCode() : 0);
        result = 31 * result + (tituloAcademico != null ? tituloAcademico.hashCode() : 0);
        return result;
    }
}
