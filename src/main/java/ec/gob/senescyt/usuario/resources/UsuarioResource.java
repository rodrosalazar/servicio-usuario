package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO;
    private CedulaValidator cedulaValidator;

    public UsuarioResource(final UsuarioDAO usuarioDAO, CedulaValidator cedulaValidator) {
        this.usuarioDAO = usuarioDAO;
        this.cedulaValidator = cedulaValidator;
    }


    @GET
    @Path("/validacion")
    @UnitOfWork
    public Response validarUsuario(@QueryParam("cedula") final String cedula,
                                   @QueryParam("nombreUsuario") final String nombreUsuario,
                                   @QueryParam("numeroIdentificacion") final String numeroIdentificacion) {

        if (cedula!= null && cedulaValidator.isValidaCedula(cedula)) {
            return Response.status(Response.Status.OK).build();
        }

        if(nombreUsuario!=null && !usuarioDAO.isRegistradoNombreUsuario(nombreUsuario)){
            return Response.status(Response.Status.OK).build();
        }

        if(numeroIdentificacion!=null && !usuarioDAO.isRegistradoNumeroIdentificacion(numeroIdentificacion)){
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @UnitOfWork
    public Response crearUsuario(@Valid final Usuario usuario) {
        Usuario usuarioCreado = usuarioDAO.guardar(usuario);

        return Response.status(Response.Status.CREATED).entity(usuarioCreado).build();
    }
}
