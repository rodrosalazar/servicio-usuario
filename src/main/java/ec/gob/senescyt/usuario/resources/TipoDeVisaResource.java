package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.usuario.core.CategoriaVisa;
import ec.gob.senescyt.usuario.dao.CategoriaVisaDAO;
import ec.gob.senescyt.usuario.dao.TipoVisaDAO;
import ec.gob.senescyt.usuario.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.usuario.resources.builders.ConstructorRespuestas;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/tiposDeVisa")
@Produces(MediaType.APPLICATION_JSON)
public class TipoDeVisaResource {

    private TipoVisaDAO tipoVisaDAO;
    private CategoriaVisaDAO categoriaVisaDAO;
    private final ConstructorRespuestas constructorRespuestas;

    public TipoDeVisaResource(TipoVisaDAO tipoVisaDAO, CategoriaVisaDAO categoriaVisaDAO, ConstructorRespuestas constructorRespuestas) {
        this.tipoVisaDAO = tipoVisaDAO;
        this.categoriaVisaDAO = categoriaVisaDAO;
        this.constructorRespuestas = constructorRespuestas;
    }

    @GET
    @Path("{idTipoVisa}/categorias")
    @UnitOfWork
    public Response obtenerCategoriasParaTipoVisa(@PathParam("idTipoVisa") String idTipoVisa) {
        List<CategoriaVisa> categoriasParaTipoVisa = this.categoriaVisaDAO.obtenerPorTipoVisa(idTipoVisa);
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CATEGORIA_VISA, categoriasParaTipoVisa);
    }
}
