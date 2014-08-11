package ec.gob.senescyt.integration.dao;

import ec.gob.senescyt.UsuarioApplication;
import ec.gob.senescyt.UsuarioConfiguration;
import ec.gob.senescyt.integration.AbstractIntegracionTest;
import ec.gob.senescyt.usuario.core.NivelDeAcceso;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class NivelDeAccesoDAOIntegracionTest extends AbstractIntegracionTest {
    private String nombre;
    @ClassRule
    public static final DropwizardAppRule<UsuarioConfiguration> RULE = new DropwizardAppRule<>(UsuarioApplication.class, resourceFilePath(CONFIGURACION));


    @Before
    public void setUp() throws Exception {
        nombre = RandomStringUtils.random(10).toString();
    }

    @Override
    protected DropwizardAppRule<UsuarioConfiguration> getRule() {
        return RULE;
    }

    @Test
    public void debeCrear() {
        NivelDeAcceso nivelDeAcceso = new NivelDeAcceso(nombre);
        nivelDeAccesoDAO.guardar(nivelDeAcceso);
        NivelDeAcceso registroEncontrado = nivelDeAccesoDAO.encontrarPorId(nivelDeAcceso.getId());
        assertThat(registroEncontrado.getId(), is(nivelDeAcceso.getId()));
    }
}
