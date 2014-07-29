package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Token;
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
    @Path("/{idToken}")
    public Response consultar(@PathParam("idToken") String idToken) {
        Token token = tokenDAO.buscar(idToken);
        return Response.ok(token).build();
    }
}
