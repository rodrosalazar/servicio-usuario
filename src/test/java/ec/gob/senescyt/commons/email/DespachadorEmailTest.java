package ec.gob.senescyt.commons.email;

import ec.gob.senescyt.commons.configuracion.ConfiguracionEmail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import javax.mail.Message;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DespachadorEmailTest {

    private DespachadorEmail despachadorEmail;
    private String emailUsuarioCreado = "test@example.com";
    private String userName = "USUARIOTEST";
    private String nombresCompletosUsuario = "NOMBRES COMPLETOS USUARIO TEST";
    private ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail;
    private ConfiguracionEmail configuracionEmail;

    @Before
    public void setUp() throws Exception {
        constructorDeContenidoDeEmail = new ConstructorDeContenidoDeEmail();
        configuracionEmail = spy(new ConfiguracionEmail());
        despachadorEmail = new DespachadorEmail(constructorDeContenidoDeEmail, configuracionEmail);
    }

    @After
    public void tearDown() throws Exception {
        Mailbox.clearAll();
    }

    @Test
    public void debeEnviarMailDeNotificacionAUsuario() throws Exception {
        when(configuracionEmail.getServidor()).thenReturn("puertoTest");
        when(configuracionEmail.getPuerto()).thenReturn(123);
        when(configuracionEmail.getNombreUsuario()).thenReturn("usuarioTest");
        when(configuracionEmail.getClave()).thenReturn("claveTest");
        when(configuracionEmail.getCorreoRemitente()).thenReturn("test@test.com");
        when(configuracionEmail.getNombreRemitente()).thenReturn("remitenteTest");

        String respuesta = despachadorEmail.enviarEmailNotificacionUsuarioCreado(emailUsuarioCreado, userName, nombresCompletosUsuario);
        assertThat(respuesta.isEmpty(), is(not(true)));

        List<Message> bandejaEntradaTest = Mailbox.get(emailUsuarioCreado);
        assertThat(bandejaEntradaTest.size(), is(1));
    }
}
