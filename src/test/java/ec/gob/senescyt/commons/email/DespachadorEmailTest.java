package ec.gob.senescyt.commons.email;

import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import ec.gob.senescyt.commons.helpers.ResourceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import javax.mail.Message;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;

public class DespachadorEmailTest {

    private DespachadorEmail despachadorEmail;
    private String emailDestinatario = "test@example.com";
    private String nombreDestinario = "NOMBRES COMPLETOS USUARIO TEST";
    private String asunto = "Creación de usuario para el sistema SNIESE";
    private String mensaje = "Mensaje";
    private ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail;
    private ConfiguracionEmail configuracionEmail;

    @Before
    public void setUp() throws Exception {
        constructorDeContenidoDeEmail = new ConstructorDeContenidoDeEmail();
        configuracionEmail = spy(new ConfiguracionEmail());
        despachadorEmail = new DespachadorEmail(configuracionEmail);
    }

    @After
    public void tearDown() throws Exception {
        Mailbox.clearAll();
    }

    @Test
    public void debeEnviarMailDeNotificacionAUsuario() throws Exception {
        ResourceTestHelper.mockConfiguracionMail(configuracionEmail);

        String respuesta = despachadorEmail.enviarEmail(emailDestinatario, nombreDestinario, asunto, mensaje);
        assertThat(respuesta.isEmpty(), is(not(true)));

        List<Message> bandejaEntradaTest = Mailbox.get(emailDestinatario);
        assertThat(bandejaEntradaTest.size(), is(1));
    }
}
