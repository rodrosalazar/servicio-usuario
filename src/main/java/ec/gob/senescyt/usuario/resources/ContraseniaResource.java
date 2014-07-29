package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.TokenUsuario;
import ec.gob.senescyt.usuario.dao.TokenUsuarioDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contrasenia")
@Produces(MediaType.APPLICATION_JSON)
public class ContraseniaResource {

    private TokenUsuarioDAO tokenUsuarioDAO;

    public ContraseniaResource(TokenUsuarioDAO tokenUsuarioDAO) {
        this.tokenUsuarioDAO = tokenUsuarioDAO;
    }

    @GET
    @Path("/{token}")
    public Response consultar(@PathParam("token") String token) {
        TokenUsuario tokenUsuario = tokenUsuarioDAO.buscar(token);
        return Response.ok(tokenUsuario).build();
    }
}
