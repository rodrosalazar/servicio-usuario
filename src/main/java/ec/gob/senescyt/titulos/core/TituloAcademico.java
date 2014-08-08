package ec.gob.senescyt.titulos.core;

import org.joda.time.DateTime;

@SuppressWarnings({"PMD.CyclomaticComplexity","PMD.NPathComplexity"})
public class TituloAcademico {
    private TipoDeTitulo tipoDeTitulo;
    private String nombreTitulo;
    private DateTime fechaTitulo;
    private NivelDeFormacion nivelDeFormacion;
    private ModalidadEducacion modalidadEducacion;
    private TipoDeMecanismo tipoDeMecanismo;
    private String numeroTipoDeMecanismo;
    private DateTime fechaTipoDeMecanismo;

    private TituloAcademico() {
    }

    public TituloAcademico(TipoDeTitulo tipoDeTitulo, String nombreTitulo, DateTime fechaTitulo,
                           NivelDeFormacion nivelDeFormacion, ModalidadEducacion modalidadEducacion,
                           TipoDeMecanismo tipoDeMecanismo, String numeroTipoDeMecanismo,
                           DateTime fechaTipoDeMecanismo) {
        this.tipoDeTitulo = tipoDeTitulo;
        this.nombreTitulo = nombreTitulo;
        this.fechaTitulo = fechaTitulo;
        this.nivelDeFormacion = nivelDeFormacion;
        this.modalidadEducacion = modalidadEducacion;
        this.tipoDeMecanismo = tipoDeMecanismo;
        this.numeroTipoDeMecanismo = numeroTipoDeMecanismo;
        this.fechaTipoDeMecanismo = fechaTipoDeMecanismo;
    }

    public TipoDeTitulo getTipoDeTitulo() {
        return tipoDeTitulo;
    }

    public String getNombreTitulo() {
        return nombreTitulo;
    }

    public DateTime getFechaTitulo() {
        return fechaTitulo;
    }

    public NivelDeFormacion getNivelDeFormacion() {
        return nivelDeFormacion;
    }

    public ModalidadEducacion getModalidadEducacion() {
        return modalidadEducacion;
    }

    public TipoDeMecanismo getTipoDeMecanismo() {
        return tipoDeMecanismo;
    }

    public String getNumeroTipoDeMecanismo() {
        return numeroTipoDeMecanismo;
    }

    public DateTime getFechaTipoDeMecanismo() {
        return fechaTipoDeMecanismo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TituloAcademico that = (TituloAcademico) o;

        if (fechaTipoDeMecanismo != null ? !fechaTipoDeMecanismo.equals(that.fechaTipoDeMecanismo) : that.fechaTipoDeMecanismo != null) {
            return false;
        }
        if (fechaTitulo != null ? !fechaTitulo.equals(that.fechaTitulo) : that.fechaTitulo != null) {
            return false;
        }
        if (modalidadEducacion != that.modalidadEducacion) {
            return false;
        }
        if (nivelDeFormacion != that.nivelDeFormacion) {
            return false;
        }
        if (nombreTitulo != null ? !nombreTitulo.equals(that.nombreTitulo) : that.nombreTitulo != null) {
            return false;
        }
        if (numeroTipoDeMecanismo != null ? !numeroTipoDeMecanismo.equals(that.numeroTipoDeMecanismo) : that.numeroTipoDeMecanismo != null) {
            return false;
        }
        if (tipoDeMecanismo != that.tipoDeMecanismo) {
            return false;
        }
        if (tipoDeTitulo != that.tipoDeTitulo) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = tipoDeTitulo != null ? tipoDeTitulo.hashCode() : 0;
        result = 31 * result + (nombreTitulo != null ? nombreTitulo.hashCode() : 0);
        result = 31 * result + (fechaTitulo != null ? fechaTitulo.hashCode() : 0);
        result = 31 * result + (nivelDeFormacion != null ? nivelDeFormacion.hashCode() : 0);
        result = 31 * result + (modalidadEducacion != null ? modalidadEducacion.hashCode() : 0);
        result = 31 * result + (tipoDeMecanismo != null ? tipoDeMecanismo.hashCode() : 0);
        result = 31 * result + (numeroTipoDeMecanismo != null ? numeroTipoDeMecanismo.hashCode() : 0);
        result = 31 * result + (fechaTipoDeMecanismo != null ? fechaTipoDeMecanismo.hashCode() : 0);
        return result;
    }
}
