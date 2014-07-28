package ec.gob.senescyt.usuario.services;

import ec.gob.bsg.accesobsgservice.AccesoBSGService;
import ec.gob.bsg.accesobsgservice.BSG04AccederBSG;
import ec.gob.bsg.accesobsgservice.ValidarPermisoPeticion;
import ec.gob.bsg.accesobsgservice.ValidarPermisoRespuesta;
import ec.gob.registrocivil.consultacedula.Cedula;
import ec.gob.registrocivil.consultacedula.WSRegistroCivilConsultaCedula;
import ec.gob.registrocivil.consultacedula.WSRegistroCivilConsultaCedula_Service;
import ec.gob.senescyt.usuario.client.DatosHeader;
import ec.gob.senescyt.usuario.client.HeaderHandlerResolver;
import ec.gob.senescyt.usuario.configuracion.ConfiguracionBSG;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;

import javax.xml.ws.WebServiceException;

public class ServicioCedula {

    private ConfiguracionBSG configuracionBSG;

    public ServicioCedula(ConfiguracionBSG configuracionBSG) {
        this.configuracionBSG = configuracionBSG;
    }

    public CedulaInfo buscar(String cedula) throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        AccesoBSGService accesoBSGService = new AccesoBSGService();
        BSG04AccederBSG accederBSG = accesoBSGService.getBSG04AccederBSGPort();
        ValidarPermisoPeticion validarPermisoPeticion = construirPeticionAccesoBSG("https://www.bsg.gob.ec/sw/RC/BSGSW01_Consultar_Cedula?wsdl");

        try {
            ValidarPermisoRespuesta validarPermisoRespuesta = accederBSG.validarPermiso(validarPermisoPeticion);
            if (!validarPermisoRespuesta.getMensaje().getCodError().equals("000")) {
                throw new CredencialesIncorrectasException(validarPermisoRespuesta.getMensaje().getDesError());
            }
            DatosHeader datosHeader = construirDatosHeader(validarPermisoRespuesta);

            WSRegistroCivilConsultaCedula_Service wsRegistroCivilConsultaCedula_service = new WSRegistroCivilConsultaCedula_Service();
            HeaderHandlerResolver headerHandlerResolver = new HeaderHandlerResolver(datosHeader);
            wsRegistroCivilConsultaCedula_service.setHandlerResolver(headerHandlerResolver);

            WSRegistroCivilConsultaCedula consultarCedulaRegistroCivil = wsRegistroCivilConsultaCedula_service.getWSRegistroCivilConsultaCedulaPort();
            Cedula respuesta = consultarCedulaRegistroCivil.busquedaPorCedula(cedula, configuracionBSG.getUsuario(), configuracionBSG.getContrasenia());

            if (respuesta.getCodigoError().equals("000")) {
                return construirCedulaInfo(respuesta);
            }
            if (respuesta.getCodigoError().equals("003")) {
                throw new CredencialesIncorrectasException(respuesta.getError());
            }
            throw new CedulaInvalidaException(respuesta.getError());

        } catch (WebServiceException cte) {
            throw new ServicioNoDisponibleException("Servicio no disponible");
        }
    }

    private CedulaInfo construirCedulaInfo(Cedula respuesta) {
        String[] domicilio = respuesta.getDomicilio().split("/");
        String direccionCompleta = String.format("%s, %s", respuesta.getCalleDomicilio(), respuesta.getNumeroDomicilio());
        return new CedulaInfo(respuesta.getNombre(), direccionCompleta, domicilio[0], domicilio[1], domicilio[2],
                respuesta.getFechaNacimiento(), respuesta.getGenero(), respuesta.getNacionalidad());
    }

    private DatosHeader construirDatosHeader(ValidarPermisoRespuesta validarPermisoRespuesta) {
        DatosHeader datosHeader = new DatosHeader();

        datosHeader.setDigest(validarPermisoRespuesta.getDigest());
        datosHeader.setFecha(validarPermisoRespuesta.getFecha());
        datosHeader.setFechaf(validarPermisoRespuesta.getFechaF());
        datosHeader.setNonce(validarPermisoRespuesta.getNonce());
        datosHeader.setUsuario(configuracionBSG.getNumeroAcceso());

        return datosHeader;
    }

    private ValidarPermisoPeticion construirPeticionAccesoBSG(String urlServicioAConsultar) {
        ValidarPermisoPeticion validarPermisoPeticion = new ValidarPermisoPeticion();
        validarPermisoPeticion.setCedula(configuracionBSG.getNumeroAcceso());
        validarPermisoPeticion.setUrlsw(urlServicioAConsultar);
        return validarPermisoPeticion;
    }
}
