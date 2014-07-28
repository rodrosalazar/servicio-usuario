package ec.gob.senescyt.contracts;

import ec.gob.senescyt.usuario.configuracion.ConfiguracionBSG;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;
import ec.gob.senescyt.usuario.services.ServicioCedula;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

public class ServicioCedulaTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ServicioCedula servicioCedula;
    private ConfiguracionBSG configuracionBSG = mock(ConfiguracionBSG.class);

    @Before
    public void setUp() throws Exception {
        reset(configuracionBSG);
        servicioCedula = new ServicioCedula(configuracionBSG);
    }

    @Test
    public void debeLanzarExcepcionDeCredencialesIncorrectasCuandoAccedemosAlBSGConDatosIncorrectos() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "11111111111";
        String numeroAccesoInvalido = "invalido";
        when(configuracionBSG.getNumeroAcceso()).thenReturn(numeroAccesoInvalido);

        expectedException.expect(CredencialesIncorrectasException.class);
        expectedException.expectMessage("Cedula enviada no corresponde a un usuario no existe o no esta registrado");

        servicioCedula.buscar(cedulaIndiferente);
    }

    @Test
    public void debeLanzarExcepcionDeCredencialesIncorrectasCuandoConsultamosCedulaConCredencialesInvalidas() throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "1111111111";
        String numeroAccesoValido = "1001633807";
        when(configuracionBSG.getNumeroAcceso()).thenReturn(numeroAccesoValido);
        when(configuracionBSG.getUsuario()).thenReturn("invalido");
        when(configuracionBSG.getContrasenia()).thenReturn("invalido");

        expectedException.expect(CredencialesIncorrectasException.class);
        expectedException.expectMessage("USUARIO O CONTRASEÑA INCORRECTOS");

        servicioCedula.buscar(cedulaIndiferente);
    }
}
