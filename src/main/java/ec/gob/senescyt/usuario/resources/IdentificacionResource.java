package ec.gob.senescyt.usuario.resources;

import com.google.common.base.Optional;
import ec.gob.senescyt.usuario.core.Credencial;
import ec.gob.senescyt.usuario.dao.CredencialDAO;
import ec.gob.senescyt.usuario.exceptions.LoginIncorrectoException;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/identificacion")
@Produces(MediaType.APPLICATION_JSON)
public class IdentificacionResource {

    private CredencialDAO credencialDAO;

    public IdentificacionResource(CredencialDAO credencialDAO) {
        this.credencialDAO = credencialDAO;
    }

    @POST
    public Response login(@Valid Credencial credencial) throws LoginIncorrectoException {
        Optional token = credencialDAO.validar(credencial);

        if (token.isPresent()) {
            return Response.ok().build();
        }

        throw new LoginIncorrectoException();
    }
}
