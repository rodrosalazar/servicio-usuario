package ec.gob.senescyt.commons.resources.builders;


import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructorRespuestas {

    public Response construirRespuestaParaArray(ElementosRaicesJSONEnum elementoRaiz, List listaDeDatosParaRespuesta){

        Map datosDeRespuestaWrapper = new HashMap<String,List>();

        datosDeRespuestaWrapper.put(elementoRaiz.getNombre(), listaDeDatosParaRespuesta);

        return Response.ok().entity(datosDeRespuestaWrapper).build();
    }

}