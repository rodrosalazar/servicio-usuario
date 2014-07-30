package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import io.dropwizard.jackson.Jackson;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PortadorTituloTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private PortadorTitulo portadorTituloConCedula;
    private PortadorTitulo portadorTituloConPasaporte;
    private String numeroIdentificacion = "ASD123";
    private DateTime fechaFinVigenciaPasaporteValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);
    private DateTime fechaFinVigenciaVisaValida = new DateTime(2015, 3, 16, 0, 0, DateTimeZone.UTC);

    @Before
    public void setUp() throws Exception {


        portadorTituloConCedula = PortadorTituloBuilder.nuevoPortadorTitulo().con(
                p -> p.identificacion = new Cedula("1111111116")
        ).generar();

        portadorTituloConPasaporte = PortadorTituloBuilder.nuevoPortadorTitulo()
                .con(p->p.identificacion =  new Pasaporte(numeroIdentificacion,fechaFinVigenciaPasaporteValida,fechaFinVigenciaVisaValida, "9",false))
                .generar();

    }

    @Test
    public void debeDeserializarUnPortadorTituloConCedulaDesdeJSON() throws Exception {
        PortadorTitulo portadorTituloDeserializado = MAPPER.readValue(fixture("fixtures/portador_titulo_con_cedula.json"),
                PortadorTitulo.class);

        assertThat(portadorTituloDeserializado.getId(), is(portadorTituloConCedula.getId()));
        assertThat(portadorTituloDeserializado.getNombresCompletos(), is(portadorTituloConCedula.getNombresCompletos()));
        assertThat(portadorTituloDeserializado.getIdentificacion().getTipoDocumento(),is(portadorTituloConCedula.getIdentificacion().getTipoDocumento()));
        assertThat(portadorTituloDeserializado.getIdentificacion().getNumeroIdentificacion(),is(portadorTituloConCedula.getIdentificacion().getNumeroIdentificacion()));
        assertThat(portadorTituloDeserializado.getEmail(),is(portadorTituloConCedula.getEmail()));
        assertThat(portadorTituloDeserializado.getGenero(),is(portadorTituloConCedula.getGenero()));
        assertThat(portadorTituloDeserializado.getIdEtnia(),is(portadorTituloConCedula.getIdEtnia()));
        assertThat(portadorTituloDeserializado.getFechaNacimiento(),is(portadorTituloConCedula.getFechaNacimiento()));
        assertThat(portadorTituloDeserializado.getTelefonoConvencional(),is(portadorTituloConCedula.getTelefonoConvencional()));
        assertThat(portadorTituloDeserializado.getExtension(),is(portadorTituloConCedula.getExtension()));
        assertThat(portadorTituloDeserializado.getTelefonoCelular(),is(portadorTituloConCedula.getTelefonoCelular()));
        assertThat(portadorTituloDeserializado.getDireccion(),is(portadorTituloConCedula.getDireccion()));
    }

    @Test
    public void debeSerializarUnPortadorTituloConCedulaAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(portadorTituloConCedula);
        assertThat(actual, is(fixture("fixtures/portador_titulo_con_cedula_con_id.json")));
    }

    @Test
    public void debeDeserializarUnPortadorTituloConPasaporteDesdeJSON() throws Exception {
        PortadorTitulo portadorTituloDeserializado = MAPPER.readValue(fixture("fixtures/portador_titulo_con_pasaporte.json"),
                PortadorTitulo.class);

        assertThat(portadorTituloDeserializado.getId(), is(portadorTituloConPasaporte.getId()));
        assertThat(portadorTituloDeserializado.getNombresCompletos(), is(portadorTituloConPasaporte.getNombresCompletos()));
        assertThat(portadorTituloDeserializado.getEmail(), is(portadorTituloConPasaporte.getEmail()));
        assertThat(portadorTituloDeserializado.getGenero(), is(portadorTituloConPasaporte.getGenero()));
        assertThat(portadorTituloDeserializado.getIdEtnia(), is(portadorTituloConPasaporte.getIdEtnia()));
        assertThat(portadorTituloDeserializado.getFechaNacimiento(), is(portadorTituloConPasaporte.getFechaNacimiento()));
        assertThat(portadorTituloDeserializado.getTelefonoConvencional(), is(portadorTituloConPasaporte.getTelefonoConvencional()));
        assertThat(portadorTituloDeserializado.getExtension(), is(portadorTituloConPasaporte.getExtension()));
        assertThat(portadorTituloDeserializado.getTelefonoCelular(), is(portadorTituloConPasaporte.getTelefonoCelular()));
        assertThat(portadorTituloDeserializado.getDireccion(), is(portadorTituloConPasaporte.getDireccion()));

        assertPasaporte(portadorTituloDeserializado.getIdentificacion(), portadorTituloConPasaporte.getIdentificacion());

    }

    private void assertPasaporte(Identificacion identificacionActual, Identificacion identificacionEsperada) {

        Pasaporte pasaporteActual = (Pasaporte) identificacionActual;
        Pasaporte pasaporteEsperado = (Pasaporte) identificacionEsperada;

        assertThat(pasaporteEsperado.getFinVigenciaPasaporte(),is(pasaporteActual.getFinVigenciaPasaporte()));
        assertThat(pasaporteEsperado.getFinVigenciaVisa(),is(pasaporteActual.getFinVigenciaVisa()));
        assertThat(pasaporteEsperado.getIdTipoVisa(),is(pasaporteActual.getIdTipoVisa()));
        assertThat(pasaporteEsperado.isVisaIndefinida(),is(pasaporteActual.isVisaIndefinida()));
    }

    @Test
    public void debeSerializarUnPortadorTituloConPasaporteAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(portadorTituloConPasaporte);
        assertThat(actual, is(fixture("fixtures/portador_titulo_con_pasaporte_con_id.json")));
    }
}
