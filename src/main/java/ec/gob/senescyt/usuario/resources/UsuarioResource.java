package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.validators.CedulaValidator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @GET
    @Path("/validacion")
    public Response verificarCedula(@QueryParam("cedula") final String cedula) {
        if (CedulaValidator.isValidaCedula(cedula)) {
            return Response.status(Response.Status.OK).build();
        }

        return Response.status(Response.Status.BAD_REQUEST).entity("Debe ingresar una cédula válida").build();
                //status(Response.Status.BAD_REQUEST).entity("Debe ingresar una cédula válida").build();
    }

}
