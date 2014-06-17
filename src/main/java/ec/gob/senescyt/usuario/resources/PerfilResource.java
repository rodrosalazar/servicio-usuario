package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.dao.PerfilDAO;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/perfil")
@Produces(MediaType.APPLICATION_JSON)
public class PerfilResource {

    private PerfilDAO perfilDAO;

    public PerfilResource(PerfilDAO perfilDAO) {

        this.perfilDAO = perfilDAO;
    }

    @POST
    public Response crearPerfil(String nombrePerfil) {

        if (nombrePerfil.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Perfil perfil = new Perfil();
        perfil.setNombre(nombrePerfil);
        perfilDAO.guardar(perfil);
        return Response.status(Response.Status.CREATED).entity(perfil).build();
    }
}
