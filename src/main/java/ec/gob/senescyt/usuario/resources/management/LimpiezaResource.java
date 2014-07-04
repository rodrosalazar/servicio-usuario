package ec.gob.senescyt.usuario.resources.management;

import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/limpieza")
public class LimpiezaResource {

    private UsuarioDAO usuarioDAO;

    public LimpiezaResource(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @GET
    @UnitOfWork
    public Response limpiar() {
        usuarioDAO.limpiar();
        return Response.noContent().build();
    }

}
