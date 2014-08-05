package ec.gob.senescyt.usuario.resources.management;

import ec.gob.senescyt.usuario.dao.PerfilDAO;
import ec.gob.senescyt.usuario.dao.UsuarioDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/limpieza")
public class LimpiezaResource {

    private UsuarioDAO usuarioDAO;
    private PerfilDAO perfilDAO;

    public LimpiezaResource(UsuarioDAO usuarioDAO, PerfilDAO perfilDAO) {
        this.usuarioDAO = usuarioDAO;
        this.perfilDAO = perfilDAO;
    }

    @GET
    @UnitOfWork
    public Response limpiar() {
        usuarioDAO.limpiar();
        perfilDAO.limpiar();
        return Response.noContent().build();
    }

}
