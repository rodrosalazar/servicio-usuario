package ec.gob.senescyt.commons.email;

import org.junit.Before;
import org.junit.Test;
import org.jvnet.mock_javamail.Mailbox;

import javax.mail.Message;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DespachadorEmailTest {

    private DespachadorEmail despachadorEmail;
    private String emailUsuarioCreado = "test@example.com";
    private String userName = "USUARIOTEST";
    private String nombresCompletosUsuario = "NOMBRES COMPLETOS USUARIO TEST";
    private ConstructorDeContenidoDeEmail constructorDeContenidoDeEmail;

    @Before
    public void setUp() throws Exception {
        constructorDeContenidoDeEmail = new ConstructorDeContenidoDeEmail();
        despachadorEmail = new DespachadorEmail(constructorDeContenidoDeEmail);
        Mailbox.clearAll();
    }

    @Test
    public void debeEnviarMailDeNotificacionAUsuario() throws Exception {
        String respuesta = despachadorEmail.enviarEmailNotificacionUsuarioCreado(emailUsuarioCreado, userName, nombresCompletosUsuario);

        assertThat(respuesta.isEmpty(), is(not(true)));

        List<Message> bandejaEntradaTest = Mailbox.get(emailUsuarioCreado);
        assertThat(bandejaEntradaTest.size() , is(1));
    }
}
