package ec.gob.senescyt.usuario.services;

import ec.gob.bsg.accesobsgservice.AccesoBSGService;
import ec.gob.bsg.accesobsgservice.BSG04AccederBSG;
import ec.gob.bsg.accesobsgservice.MensajeError;
import ec.gob.bsg.accesobsgservice.ValidarPermisoRespuesta;
import ec.gob.registrocivil.consultacedula.Cedula;
import ec.gob.registrocivil.consultacedula.WSRegistroCivilConsultaCedula;
import ec.gob.registrocivil.consultacedula.WSRegistroCivilConsultaCedula_Service;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import ec.gob.senescyt.usuario.configuracion.ConfiguracionBSG;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.jws.WebParam;
import javax.xml.ws.WebServiceException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class ServicioCedulaTest {

    public static final String CREDENCIALES_INVALIDAS = "Credenciales Invalidas";
    public static final String CEDULA_INVALIDA = "Cedula Invalida";
    private static final String NOMBRE_EN_CEDULA = "nombre en cedula";
    private static final String INDIFERENTE = "indiferente";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ServicioCedula servicioCedula;
    private ConfiguracionBSG configuracionBSG = mock(ConfiguracionBSG.class);
    private ProvinciaDAO provinciaDAO = mock(ProvinciaDAO.class);
    private AccesoBSGService accesoBSGService = mock(AccesoBSGService.class);
    private WSRegistroCivilConsultaCedula_Service consultaCedulaService = mock(WSRegistroCivilConsultaCedula_Service.class);

    @Before
    public void setUp() {
        reset(configuracionBSG);
        servicioCedula = new ServicioCedula(configuracionBSG, provinciaDAO, accesoBSGService, consultaCedulaService);
    }

    @Test
    public void debeLanzarExcepcionDeServicioNoDisponibleCuandoElServidorEstaCaido()
            throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "1111111111";
        BSG04AccederBSG accederBSG = crearAccessPointQueLanzaUnaExcepcion();
        when(accesoBSGService.getBSG04AccederBSGPort()).thenReturn(accederBSG);

        expectedException.expect(ServicioNoDisponibleException.class);
        expectedException.expectMessage("Servicio no disponible");

        servicioCedula.buscar(cedulaIndiferente);
    }

    @Test
    public void debeLanzarExcepcionDeCredencialesIncorrectasCuandoAccedemosAlBSGConDatosIncorrectos()
            throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "11111111111";
        String numeroAccesoInvalido = "invalido";
        when(configuracionBSG.getNumeroAcceso()).thenReturn(numeroAccesoInvalido);
        BSG04AccederBSG accederBSG = crearAccessPointConNumeroDeIdentificacionInvalido();
        when(accesoBSGService.getBSG04AccederBSGPort()).thenReturn(accederBSG);

        expectedException.expect(CredencialesIncorrectasException.class);
        expectedException.expectMessage(CREDENCIALES_INVALIDAS);

        servicioCedula.buscar(cedulaIndiferente);
    }

    @Test
    public void debeLanzarExcepcionDeCredencialesIncorrectasCuandoConsultamosCedulaConCredencialesInvalidas()
            throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaIndiferente = "1111111111";
        String numeroAccesoValido = "valido";
        String invalido = "invalido";
        BSG04AccederBSG accederBSG = crearAccessPointConNumeroDeIdentificacionValido();
        when(configuracionBSG.getNumeroAcceso()).thenReturn(numeroAccesoValido);
        when(configuracionBSG.getUsuario()).thenReturn(invalido);
        when(configuracionBSG.getContrasenia()).thenReturn(invalido);
        when(accesoBSGService.getBSG04AccederBSGPort()).thenReturn(accederBSG);
        WSRegistroCivilConsultaCedula accessPointConsultaCedula = crearAccessPointConsultaCedulaQueRespondeConCredencialesInvalidas();
        when(consultaCedulaService.getWSRegistroCivilConsultaCedulaPort()).thenReturn(accessPointConsultaCedula);

        expectedException.expect(CredencialesIncorrectasException.class);
        expectedException.expectMessage(CREDENCIALES_INVALIDAS);

        servicioCedula.buscar(cedulaIndiferente);
    }

    @Test
    public void debeLanzarExcepcionCuandoLaCedulaAConsultarEsInvalida() throws CedulaInvalidaException,
            ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaInvalida = "1111111111";
        String numeroAccesoValido = "valido";
        String invalido = "invalido";
        BSG04AccederBSG accederBSG = crearAccessPointConNumeroDeIdentificacionValido();
        when(configuracionBSG.getNumeroAcceso()).thenReturn(numeroAccesoValido);
        when(configuracionBSG.getUsuario()).thenReturn(invalido);
        when(configuracionBSG.getContrasenia()).thenReturn(invalido);
        when(accesoBSGService.getBSG04AccederBSGPort()).thenReturn(accederBSG);
        WSRegistroCivilConsultaCedula accessPointConsultaCedula = crearAccessPointConsultaCedulaQueRespondeConCedulaInvalida();
        when(consultaCedulaService.getWSRegistroCivilConsultaCedulaPort()).thenReturn(accessPointConsultaCedula);

        expectedException.expect(CedulaInvalidaException.class);
        expectedException.expectMessage(CEDULA_INVALIDA);

        servicioCedula.buscar(cedulaInvalida);
    }

    @Test
    public void debeDevolverLosDatosDeLaCedulaCuandoLaInformacionEsCorrecta() throws CedulaInvalidaException,
            ServicioNoDisponibleException, CredencialesIncorrectasException {
        String cedulaInvalida = "1111111111";
        String numeroAccesoValido = "valido";
        String invalido = "invalido";
        BSG04AccederBSG accederBSG = crearAccessPointConNumeroDeIdentificacionValido();
        when(configuracionBSG.getNumeroAcceso()).thenReturn(numeroAccesoValido);
        when(configuracionBSG.getUsuario()).thenReturn(invalido);
        when(configuracionBSG.getContrasenia()).thenReturn(invalido);
        when(accesoBSGService.getBSG04AccederBSGPort()).thenReturn(accederBSG);
        WSRegistroCivilConsultaCedula accessPointConsultaCedula = crearAccessPointConsultaCedulaQueRespondeDatosValidos();
        when(consultaCedulaService.getWSRegistroCivilConsultaCedulaPort()).thenReturn(accessPointConsultaCedula);

        CedulaInfo cedulaInfo = servicioCedula.buscar(cedulaInvalida);
        assertThat(cedulaInfo.getNombre(), is(NOMBRE_EN_CEDULA));
    }


    private BSG04AccederBSG crearAccessPointQueLanzaUnaExcepcion() {
        return validarPermisoPeticion -> {
            throw new WebServiceException();
        };
    }

    private BSG04AccederBSG crearAccessPointConNumeroDeIdentificacionInvalido() {
        return validarPermisoPeticion -> {
            ValidarPermisoRespuesta validarPermisoRespuesta = new ValidarPermisoRespuesta();
            MensajeError mensajeError = new MensajeError();
            mensajeError.setCodError("error");
            mensajeError.setDesError(CREDENCIALES_INVALIDAS);
            validarPermisoRespuesta.setMensaje(mensajeError);
            return validarPermisoRespuesta;
        };
    }

    private BSG04AccederBSG crearAccessPointConNumeroDeIdentificacionValido() {
        return validarPermisoPeticion -> {
            ValidarPermisoRespuesta validarPermisoRespuesta = new ValidarPermisoRespuesta();
            MensajeError mensajeError = new MensajeError();
            mensajeError.setCodError("000");
            validarPermisoRespuesta.setMensaje(mensajeError);
            validarPermisoRespuesta.setFecha(INDIFERENTE);
            validarPermisoRespuesta.setDigest(INDIFERENTE);
            validarPermisoRespuesta.setNonce(INDIFERENTE);
            validarPermisoRespuesta.setFechaF(INDIFERENTE);
            validarPermisoRespuesta.setTienePermiso(INDIFERENTE);
            return validarPermisoRespuesta;

        };
    }

    private WSRegistroCivilConsultaCedula crearAccessPointConsultaCedulaQueRespondeConCredencialesInvalidas() {
        return new WSRegistroCivilConsultaCedula() {
            public Cedula busquedaPorCedula(@WebParam(name = "Cedula", targetNamespace = "") String cedula, @WebParam(name = "Usuario", targetNamespace = "") String usuario, @WebParam(name = "Contrasenia", targetNamespace = "") String contrasenia) {
                Cedula cedulaConError = new Cedula();
                cedulaConError.setCodigoError("003");
                cedulaConError.setError(CREDENCIALES_INVALIDAS);
                return cedulaConError;
            }

            public Cedula wsUp() {
                return null;
            }
        };
    }

    private WSRegistroCivilConsultaCedula crearAccessPointConsultaCedulaQueRespondeConCedulaInvalida() {
        return new WSRegistroCivilConsultaCedula() {
            public Cedula busquedaPorCedula(@WebParam(name = "Cedula", targetNamespace = "") String cedula, @WebParam(name = "Usuario", targetNamespace = "") String usuario, @WebParam(name = "Contrasenia", targetNamespace = "") String contrasenia) {
                Cedula cedulaConError = new Cedula();
                cedulaConError.setCodigoError("Error");
                cedulaConError.setError(CEDULA_INVALIDA);
                return cedulaConError;
            }

            public Cedula wsUp() {
                return null;
            }
        };
    }

    private WSRegistroCivilConsultaCedula crearAccessPointConsultaCedulaQueRespondeDatosValidos() {
        return new WSRegistroCivilConsultaCedula() {
            public Cedula busquedaPorCedula(@WebParam(name = "Cedula", targetNamespace = "") String cedula, @WebParam(name = "Usuario", targetNamespace = "") String usuario, @WebParam(name = "Contrasenia", targetNamespace = "") String contrasenia) {
                Cedula cedulaValida = new Cedula();
                cedulaValida.setCodigoError("000");
                cedulaValida.setDomicilio("1/2/3");
                cedulaValida.setCalleDomicilio(INDIFERENTE);
                cedulaValida.setNumeroDomicilio(INDIFERENTE);
                cedulaValida.setNombre(NOMBRE_EN_CEDULA);
                cedulaValida.setFechaNacimiento(INDIFERENTE);
                cedulaValida.setNacionalidad(INDIFERENTE);
                cedulaValida.setGenero(INDIFERENTE);
                return cedulaValida;
            }

            public Cedula wsUp() {
                return null;
            }
        };
    }


}
