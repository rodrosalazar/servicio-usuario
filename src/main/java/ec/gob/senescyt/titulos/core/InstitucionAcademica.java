package ec.gob.senescyt.titulos.core;

@SuppressWarnings({"PMD.CyclomaticComplexity","PMD.NPathComplexity"})
public class InstitucionAcademica {
    private String codigoPais;
    private String idInstitucion;
    private String ciudadInstitucion;
    private TipoDeInstitucion tipoDeInstitucion;

    private InstitucionAcademica() {
    }

    public InstitucionAcademica(String codigoPais, String idInstitucion, String ciudadInstitucion, TipoDeInstitucion tipoDeInstitucion) {
        this.codigoPais = codigoPais;
        this.idInstitucion = idInstitucion;
        this.ciudadInstitucion = ciudadInstitucion;
        this.tipoDeInstitucion = tipoDeInstitucion;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public String getIdInstitucion() {
        return idInstitucion;
    }

    public String getCiudadInstitucion() {
        return ciudadInstitucion;
    }

    public TipoDeInstitucion getTipoDeInstitucion() {
        return tipoDeInstitucion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstitucionAcademica that = (InstitucionAcademica) o;

        if (ciudadInstitucion != null ? !ciudadInstitucion.equals(that.ciudadInstitucion) : that.ciudadInstitucion != null) {
            return false;
        }
        if (codigoPais != null ? !codigoPais.equals(that.codigoPais) : that.codigoPais != null) {
            return false;
        }
        if (idInstitucion != null ? !idInstitucion.equals(that.idInstitucion) : that.idInstitucion != null) {
            return false;
        }
        if (tipoDeInstitucion != that.tipoDeInstitucion) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoPais != null ? codigoPais.hashCode() : 0;
        result = 31 * result + (idInstitucion != null ? idInstitucion.hashCode() : 0);
        result = 31 * result + (ciudadInstitucion != null ? ciudadInstitucion.hashCode() : 0);
        result = 31 * result + (tipoDeInstitucion != null ? tipoDeInstitucion.hashCode() : 0);
        return result;
    }
}
