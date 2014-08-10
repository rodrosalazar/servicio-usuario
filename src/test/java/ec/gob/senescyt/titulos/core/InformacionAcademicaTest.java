package ec.gob.senescyt.titulos.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.gob.senescyt.commons.builders.InformacionAcademicaBuilder;
import io.dropwizard.jackson.Jackson;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class InformacionAcademicaTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private InformacionAcademica informacionAcademica;

    @Before
    public void setUp() {
        informacionAcademica = InformacionAcademicaBuilder.nuevaInformacionAcademica().generar();
    }

    @Test
    public void debeDeserializarInformacionAcademicaDesdeJSON() throws IOException {
        InformacionAcademica informacionAcademicaDeserializada =
                MAPPER.readValue(fixture("fixtures/informacion_academica_con_id_para_deserializar.json"), InformacionAcademica.class);

        assertThat(informacionAcademicaDeserializada.getId(), is(informacionAcademica.getId()));
        assertThat(informacionAcademicaDeserializada.getInstitucionAcademica(), is(informacionAcademica.getInstitucionAcademica()));
        assertThat(informacionAcademicaDeserializada.getFacultadDepartamento(), is(informacionAcademica.getFacultadDepartamento()));
        assertThat(informacionAcademicaDeserializada.getPersonaContacto(), is(informacionAcademica.getPersonaContacto()));
        assertThat(informacionAcademicaDeserializada.getTituloAcademico(), is(informacionAcademica.getTituloAcademico()));
    }

    @Test
    public void debeSerializarInformacionAcademicaAJSON() throws IOException {
        String actual = MAPPER.writeValueAsString(informacionAcademica);
        assertThat(actual, is(fixture("fixtures/informacion_academica_con_id_serializada.json")));
    }

    @Test
    public void debenSerIgualesDosObjectosConLosMismoValores() {
        InformacionAcademica otraInformacionAcademica = InformacionAcademicaBuilder
                                                            .nuevaInformacionAcademica()
                                                            .generar();

        assertThat(informacionAcademica, is(otraInformacionAcademica));
    }

    @Test
    public void debeSerIgualesObjectoConsigoMismo() {
        assertThat(informacionAcademica, is(informacionAcademica));
    }

    @Test
    public void debeNoSerIgualesCuandoUnObjectoEsNulo() {
        InformacionAcademica informacionAcademicaNula = null;

        assertThat(informacionAcademica, is(not(informacionAcademicaNula)));
    }
}
