package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.commons.builders.MensajeErrorBuilder;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.dto.CredencialLogin;
import ec.gob.senescyt.usuario.services.ServicioCredencial;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private CredencialDAO credencialDAO;
    private ServicioCredencial servicioCredencial;
    private MensajeErrorBuilder mensajeErrorBuilder;

    public LoginResource(CredencialDAO credencialDAO, ServicioCredencial servicioCredencial, MensajeErrorBuilder mensajeErrorBuilder) {
        this.credencialDAO = credencialDAO;
        this.servicioCredencial = servicioCredencial;
        this.mensajeErrorBuilder = mensajeErrorBuilder;
    }


    @POST
    @UnitOfWork
    public Response buscar(@Valid CredencialLogin credencialLogin) {
        Credencial credencialAlmacenada = credencialDAO.obtenerPorNombreUsuario(credencialLogin.getNombreUsuario());
        boolean esCredencialValido = servicioCredencial.verificarContrasenia(credencialLogin.getContrasenia(), credencialAlmacenada.getHash());

        if (!esCredencialValido) {
           String entidadError = mensajeErrorBuilder.mensajeLoginIncorrecto();
            return Response.status(BAD_REQUEST).entity(entidadError).build();
//            return Response.status(BAD_REQUEST).build();
        }
        return Response.status(CREATED).build();
    }
}
