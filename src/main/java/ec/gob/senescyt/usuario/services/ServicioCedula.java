package ec.gob.senescyt.usuario.services;

import ec.gob.bsg.accesobsgservice.AccesoBSGService;
import ec.gob.bsg.accesobsgservice.BSG04AccederBSG;
import ec.gob.bsg.accesobsgservice.ValidarPermisoPeticion;
import ec.gob.bsg.accesobsgservice.ValidarPermisoRespuesta;
import ec.gob.registrocivil.consultacedula.Cedula;
import ec.gob.registrocivil.consultacedula.WSRegistroCivilConsultaCedula;
import ec.gob.registrocivil.consultacedula.WSRegistroCivilConsultaCedula_Service;
import ec.gob.senescyt.titulos.dao.ProvinciaDAO;
import ec.gob.senescyt.usuario.client.DatosHeader;
import ec.gob.senescyt.usuario.client.HeaderHandlerResolver;
import ec.gob.senescyt.usuario.configuracion.ConfiguracionBSG;
import ec.gob.senescyt.usuario.core.CedulaInfo;
import ec.gob.senescyt.usuario.exceptions.CedulaInvalidaException;
import ec.gob.senescyt.usuario.exceptions.CredencialesIncorrectasException;
import ec.gob.senescyt.usuario.exceptions.ServicioNoDisponibleException;

import javax.xml.ws.WebServiceException;

public class ServicioCedula {

    public static final String URL_SERVICIO_CONSULTA_CEDULA = "https://www.bsg.gob.ec/sw/RC/BSGSW01_Consultar_Cedula?wsdl";
    private ConfiguracionBSG configuracionBSG;
    private ProvinciaDAO provinciaDAO;
    private AccesoBSGService accesoBSGService;
    private WSRegistroCivilConsultaCedula_Service servicioConsultaCedula;

    public ServicioCedula(ConfiguracionBSG configuracionBSG, ProvinciaDAO provinciaDAO, AccesoBSGService accesoBSGService,
                          WSRegistroCivilConsultaCedula_Service servicioConsultaCedula) {
        this.configuracionBSG = configuracionBSG;
        this.provinciaDAO = provinciaDAO;
        this.accesoBSGService = accesoBSGService;
        this.servicioConsultaCedula = servicioConsultaCedula;
    }

    public CedulaInfo buscar(String cedula) throws CedulaInvalidaException, ServicioNoDisponibleException, CredencialesIncorrectasException {
        BSG04AccederBSG accederBSG = accesoBSGService.getBSG04AccederBSGPort();
        ValidarPermisoPeticion validarPermisoPeticion = construirPeticionAccesoBSG(URL_SERVICIO_CONSULTA_CEDULA);

        try {
            ValidarPermisoRespuesta validarPermisoRespuesta = accederBSG.validarPermiso(validarPermisoPeticion);
            if (!validarPermisoRespuesta.getMensaje().getCodError().equals("000")) {
                throw new CredencialesIncorrectasException(validarPermisoRespuesta.getMensaje().getDesError());
            }
            DatosHeader datosHeader = construirDatosHeader(validarPermisoRespuesta);

            HeaderHandlerResolver headerHandlerResolver = new HeaderHandlerResolver(datosHeader);
            servicioConsultaCedula.setHandlerResolver(headerHandlerResolver);

            WSRegistroCivilConsultaCedula consultarCedulaRegistroCivil =
                    servicioConsultaCedula.getWSRegistroCivilConsultaCedulaPort();
            Cedula respuesta = consultarCedulaRegistroCivil.busquedaPorCedula(cedula, configuracionBSG.getUsuario(),
                    configuracionBSG.getContrasenia());

            if (respuesta.getCodigoError().equals("000")) {
                return construirCedulaInfo(respuesta);
            }
            if (respuesta.getCodigoError().equals("003")) {
                throw new CredencialesIncorrectasException(respuesta.getError());
            }
            throw new CedulaInvalidaException(respuesta.getError());

        } catch (WebServiceException cte) {
            throw new ServicioNoDisponibleException("Servicio no disponible", cte);
        }
    }

    private CedulaInfo construirCedulaInfo(Cedula respuesta) {
        String[] domicilio = respuesta.getDomicilio().split("/");
        String direccionCompleta = String.format("%s, %s", respuesta.getCalleDomicilio(), respuesta.getNumeroDomicilio());

        String provincia = domicilio[0];
        String canton = domicilio[1];
        String parroquia = domicilio[2];

        String idProvincia = provinciaDAO.obtenerIdParaNombre(provincia);

        return new CedulaInfo(respuesta.getNombre(), direccionCompleta, provincia, idProvincia, canton,
                parroquia,
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
