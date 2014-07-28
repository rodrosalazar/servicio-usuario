package ec.gob.senescyt.usuario.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.UniversidadExtranjera;
import ec.gob.senescyt.titulos.core.UniversidadExtranjeraDAO;
import ec.gob.senescyt.usuario.core.Institucion;
import ec.gob.senescyt.usuario.dao.InstitucionDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/instituciones")
@Produces(MediaType.APPLICATION_JSON)
public class InstitucionResource {
    private InstitucionDAO institucionDAO;

    private ConstructorRespuestas constructorRespuestas;
    private UniversidadExtranjeraDAO universidadExtranjeraDAO;

    public InstitucionResource(InstitucionDAO institucionDAO, ConstructorRespuestas constructorRespuestas,
                               UniversidadExtranjeraDAO universidadExtranjeraDAO) {
        this.institucionDAO = institucionDAO;
        this.constructorRespuestas = constructorRespuestas;
        this.universidadExtranjeraDAO = universidadExtranjeraDAO;
    }
    @GET
    @UnitOfWork
    public Response obtenerTodas() throws JsonProcessingException {
        List<Institucion> instituciones = institucionDAO.obtenerTodas();

        return Response.ok(instituciones).build();
    }

    @GET
    @Path("/convenio")
    @UnitOfWork
    public Response obtenerUniversidadesConvenio() {
        List<UniversidadExtranjera> universidadesConvenio = universidadExtranjeraDAO.obtenerUniversidadesConvenio();
        return constructorRespuestas
                .construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_CONVENIO, universidadesConvenio);
    }

    @GET
    @Path("/listado")
    @UnitOfWork
    public Response obtenerUniversidadesListado() {
        List<UniversidadExtranjera> universidadesListado = universidadExtranjeraDAO.obtenerUniversidadesListado();
        return constructorRespuestas
                .construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_UNIVERSIDADES_LISTADO, universidadesListado);
    }

}
