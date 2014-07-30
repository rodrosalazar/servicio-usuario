package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.usuario.core.Perfil;
import ec.gob.senescyt.usuario.dao.PerfilDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/perfiles")
@Produces(MediaType.APPLICATION_JSON)
public class PerfilResource {

    private final PerfilDAO perfilDAO;
    private final ConstructorRespuestas constructorRespuestas;

    public PerfilResource(PerfilDAO perfilDAO, ConstructorRespuestas constructorRespuestas) {
        this.perfilDAO = perfilDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @POST
    @UnitOfWork
    public Response crearPerfil(@Valid Perfil perfil) {
        Perfil perfilCreado = perfilDAO.guardar(perfil);
        return Response.status(Response.Status.CREATED).entity(perfilCreado).build();
    }

    @GET
    @UnitOfWork
    public Response obtenerTodos() throws JsonProcessingException {
        List<Perfil> perfiles = perfilDAO.obtenerTodos();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_PERFILES, perfiles);
    }
}
