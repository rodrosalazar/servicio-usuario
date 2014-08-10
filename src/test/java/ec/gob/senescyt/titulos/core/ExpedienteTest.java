package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.commons.builders.InformacionAcademicaBuilder;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExpedienteTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private Expediente expediente;

    @Before
    public void setUp() throws Exception {
        expediente = new Expediente(PortadorTituloBuilder.nuevoPortadorTitulo().con(
                p -> p.identificacion = new Cedula("1111111116")).generar(),
                InformacionAcademicaBuilder.nuevaInformacionAcademica().generar());
    }

    @Test
    public void debeDeserializarExpedienteDesdeJSON() throws IOException {
        Expediente expedienteDeserializado =
                MAPPER.readValue(fixture("fixtures/expediente_con_id_para_deserializar.json"), Expediente.class);

        assertThat(expedienteDeserializado.getId(), is(expediente.getId()));
        assertThat(expedienteDeserializado.getInformacionAcademica(), is(expediente.getInformacionAcademica()));

        assertThat(expedienteDeserializado.getPortadorTitulo().getId(), is(expediente.getPortadorTitulo().getId()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getNombresCompletos(), is(expediente.getPortadorTitulo().getNombresCompletos()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getIdentificacion().getTipoDocumento(), is(expediente.getPortadorTitulo().getIdentificacion().getTipoDocumento()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getIdentificacion().getNumeroIdentificacion(), is(expediente.getPortadorTitulo().getIdentificacion().getNumeroIdentificacion()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getEmail(), is(expediente.getPortadorTitulo().getEmail()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getGenero(), is(expediente.getPortadorTitulo().getGenero()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getIdEtnia(), is(expediente.getPortadorTitulo().getIdEtnia()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getFechaNacimiento(), is(expediente.getPortadorTitulo().getFechaNacimiento()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getTelefonoConvencional(), is(expediente.getPortadorTitulo().getTelefonoConvencional()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getExtension(), is(expediente.getPortadorTitulo().getExtension()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getTelefonoCelular(), is(expediente.getPortadorTitulo().getTelefonoCelular()));
        assertThat(expedienteDeserializado.getPortadorTitulo().getDireccion(), is(expediente.getPortadorTitulo().getDireccion()));
    }

    @Test
    public void debeSerializarExpedienteAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(expediente);
        assertThat(actual, is(fixture("fixtures/expediente_con_id_serializado.json")));
    }
}
