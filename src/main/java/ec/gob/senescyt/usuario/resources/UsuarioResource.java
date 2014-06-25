package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Usuario;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import ec.gob.senescyt.usuario.validators.CedulaValidator;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private final UsuarioDAO usuarioDAO;

    public UsuarioResource(final UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }


    @GET
    @Path("/validacion")
    public Response verificarCedula(@QueryParam("cedula") final String cedula) {
        if (CedulaValidator.isValidaCedula(cedula)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Debe ingresar una cédula válida").build();
    }

    @POST
    @UnitOfWork
    public Response crearUsuario( final Usuario usuario) {
        if (!usuario.isValido()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Usuario usuarioCreado = usuarioDAO.guardar(usuario);

        return Response.status(Response.Status.CREATED).build();
    }
}
