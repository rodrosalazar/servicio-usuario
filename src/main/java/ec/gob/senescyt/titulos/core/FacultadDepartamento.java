package ec.gob.senescyt.titulos.core;

@SuppressWarnings("PMD.CyclomaticComplexity")
public class FacultadDepartamento {
    private String nombreFacultadDepartamento;
    private String direccionFacultadDepartamento;
    private String telefonoFacultadDepartamento;

    private FacultadDepartamento() {
    }

    public FacultadDepartamento(String nombreFacultadDepartamento, String direccionFacultadDepartamento, String telefonoFacultadDepartamento) {
        this.nombreFacultadDepartamento = nombreFacultadDepartamento;
        this.direccionFacultadDepartamento = direccionFacultadDepartamento;
        this.telefonoFacultadDepartamento = telefonoFacultadDepartamento;
    }

    public String getNombreFacultadDepartamento() {
        return nombreFacultadDepartamento;
    }

    public String getDireccionFacultadDepartamento() {
        return direccionFacultadDepartamento;
    }

    public String getTelefonoFacultadDepartamento() {
        return telefonoFacultadDepartamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FacultadDepartamento that = (FacultadDepartamento) o;

        if (direccionFacultadDepartamento != null ? !direccionFacultadDepartamento.equals(that.direccionFacultadDepartamento) : that.direccionFacultadDepartamento != null) {
            return false;
        }
        if (nombreFacultadDepartamento != null ? !nombreFacultadDepartamento.equals(that.nombreFacultadDepartamento) : that.nombreFacultadDepartamento != null) {
            return false;
        }
        if (telefonoFacultadDepartamento != null ? !telefonoFacultadDepartamento.equals(that.telefonoFacultadDepartamento) : that.telefonoFacultadDepartamento != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombreFacultadDepartamento != null ? nombreFacultadDepartamento.hashCode() : 0;
        result = 31 * result + (direccionFacultadDepartamento != null ? direccionFacultadDepartamento.hashCode() : 0);
        result = 31 * result + (telefonoFacultadDepartamento != null ? telefonoFacultadDepartamento.hashCode() : 0);
        return result;
    }
}
