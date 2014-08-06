package ec.gob.senescyt.usuario.resources;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import ec.gob.senescyt.commons.resources.builders.ConstructorRespuestas;
import ec.gob.senescyt.titulos.core.CategoriaVisa;
import ec.gob.senescyt.titulos.core.TipoVisa;
import ec.gob.senescyt.titulos.dao.CategoriaVisaDAO;
import ec.gob.senescyt.titulos.dao.TipoVisaDAO;
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
    @UnitOfWork
    public Response obtenerTodos() {
        List<TipoVisa> tiposVisa = this.tipoVisaDAO.obtenerTodos();
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_TIPO_VISA, tiposVisa);
    }

    @GET
    @Path("{idTipoVisa}/categorias")
    @UnitOfWork
    public Response obtenerCategoriasParaTipoVisa(@PathParam("idTipoVisa") String idTipoVisa) {
        List<CategoriaVisa> categoriasParaTipoVisa = this.categoriaVisaDAO.obtenerPorTipoVisa(idTipoVisa);
        return constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CATEGORIA_VISA, categoriasParaTipoVisa);
    }

}
