package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.commons.builders.PortadorTituloBuilder;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PortadorTituloTest {

    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    private PortadorTitulo portadorTituloConCedula;

    @Before
    public void setUp() throws Exception {
        portadorTituloConCedula = PortadorTituloBuilder.nuevoPortadorTitulo().generar();
    }

    @Test
    public void debeDeserializarUnPortadorTituloDesdeJSON() throws Exception {
        PortadorTitulo portadorTituloDeserializado = MAPPER.readValue(fixture("fixtures/portador_titulo_con_cedula.json"),
                PortadorTitulo.class);

        assertThat(portadorTituloDeserializado.getId(), is(portadorTituloConCedula.getId()));
        assertThat(portadorTituloDeserializado.getNombresCompletos(), is(portadorTituloConCedula.getNombresCompletos()));
        assertThat(portadorTituloDeserializado.getIdentificacion(),is(portadorTituloConCedula.getIdentificacion()));
        assertThat(portadorTituloDeserializado.getEmail(),is(portadorTituloConCedula.getEmail()));
        assertThat(portadorTituloDeserializado.getSexo(),is(portadorTituloConCedula.getSexo()));
        assertThat(portadorTituloDeserializado.getIdEtnia(),is(portadorTituloConCedula.getIdEtnia()));
        assertThat(portadorTituloDeserializado.getFechaNacimiento(),is(portadorTituloConCedula.getFechaNacimiento()));
        assertThat(portadorTituloDeserializado.getTelefonoConvencional(),is(portadorTituloConCedula.getTelefonoConvencional()));
        assertThat(portadorTituloDeserializado.getExtension(),is(portadorTituloConCedula.getExtension()));
        assertThat(portadorTituloDeserializado.getTelefonoCelular(),is(portadorTituloConCedula.getTelefonoCelular()));
        assertThat(portadorTituloDeserializado.getDireccion(),is(portadorTituloConCedula.getDireccion()));
    }

    @Test
    public void debeSerializarUnPortadorTituloAJSON() throws Exception {
        String actual = MAPPER.writeValueAsString(portadorTituloConCedula);
        assertThat(actual, is(fixture("fixtures/portador_titulo_con_cedula_con_id.json")));
    }
}
