package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.dao.PerfilDAO;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/perfiles")
@Produces(MediaType.APPLICATION_JSON)
public class PerfilResource extends BaseResource<Perfil, PerfilDAO> {
    public PerfilResource(PerfilDAO dao) {
        super(dao);
    }
}
