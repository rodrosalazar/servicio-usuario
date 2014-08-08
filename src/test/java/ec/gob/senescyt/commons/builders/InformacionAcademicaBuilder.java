package ec.gob.senescyt.commons.builders;

import ec.gob.senescyt.titulos.core.FacultadDepartamento;
import ec.gob.senescyt.titulos.core.InformacionAcademica;
import ec.gob.senescyt.titulos.core.InstitucionAcademica;
import ec.gob.senescyt.titulos.core.PersonaContacto;
import ec.gob.senescyt.titulos.core.TipoDeInstitucion;
import ec.gob.senescyt.titulos.core.TipoDeTitulo;
import ec.gob.senescyt.titulos.core.NivelDeFormacion;
import ec.gob.senescyt.titulos.core.ModalidadEducacion;
import ec.gob.senescyt.titulos.core.TipoDeMecanismo;
import ec.gob.senescyt.titulos.core.TituloAcademico;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.function.Consumer;
@SuppressWarnings("PMD.TooManyFields")
public class InformacionAcademicaBuilder {
    private static InformacionAcademicaBuilder informacionAcademicaBuilder;

    private String codigoPais = "ES";
    private String idInstitucion = "1234";
    private String ciudadInstitucion = "Quito";
    private TipoDeInstitucion tipoDeInstitucion = TipoDeInstitucion.PRIVADA;
    private String nombreFacultadDepartamento = "Sistemas";
    private String direccionFacultadDepartamento = "Quito";
    private String telefonoFacultadDepartamento = "123123123";
    private String nombrePersonaContacto = "Persona Contacto";
    private String emailPersonaContacto = "email@gmail.com";
    private TipoDeTitulo tipoDeTitulo = TipoDeTitulo.PROFESIONAL;
    private String nombreTitulo = "Ingeniero en Sistemas";
    private DateTime fechaTitulo = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);
    private NivelDeFormacion nivelDeFormacion = NivelDeFormacion.TERCER_NIVEL;
    private ModalidadEducacion modalidadEducacion = ModalidadEducacion.PRESENCIAL;
    private TipoDeMecanismo tipoDeMecanismo = TipoDeMecanismo.APOSTILLA;
    private String numeroTipoDeMecanismo = "123123";
    private DateTime fechaTipoDeMecanismo = new DateTime(1990, 3, 16, 0, 0, DateTimeZone.UTC);

    public InformacionAcademica generar() {
        InformacionAcademica informacionAcademica = new InformacionAcademica(
                new InstitucionAcademica(codigoPais, idInstitucion,ciudadInstitucion, tipoDeInstitucion),
                new FacultadDepartamento(nombreFacultadDepartamento, direccionFacultadDepartamento,
                telefonoFacultadDepartamento),
                new PersonaContacto(nombrePersonaContacto, emailPersonaContacto),new TituloAcademico(tipoDeTitulo,
                nombreTitulo, fechaTitulo, nivelDeFormacion, modalidadEducacion, tipoDeMecanismo,
                numeroTipoDeMecanismo, fechaTipoDeMecanismo));

        return informacionAcademica;
    }

    public static InformacionAcademicaBuilder nuevaInformacionAcademica() {
        informacionAcademicaBuilder = new InformacionAcademicaBuilder();
        return informacionAcademicaBuilder;
    }

    public InformacionAcademicaBuilder con(Consumer<InformacionAcademicaBuilder> consumidor) {
        consumidor.accept(informacionAcademicaBuilder);
        return informacionAcademicaBuilder;
    }
}