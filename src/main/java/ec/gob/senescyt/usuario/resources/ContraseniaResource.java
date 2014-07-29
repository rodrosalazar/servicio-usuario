package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.dao.TokenDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contrasenia")
@Produces(MediaType.APPLICATION_JSON)
public class ContraseniaResource {

    private TokenDAO tokenDAO;

    public ContraseniaResource(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @GET
    @Path("/{token}")
    public Response consultar(@PathParam("token") String token) {
        tokenDAO.buscar(token);
        return Response.ok().entity(1).build();
    }
}
