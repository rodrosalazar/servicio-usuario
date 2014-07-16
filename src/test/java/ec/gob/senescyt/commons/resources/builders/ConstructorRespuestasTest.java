package ec.gob.senescyt.commons.resources.builders;

import ec.gob.senescyt.commons.enums.ElementosRaicesJSONEnum;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ConstructorRespuestasTest{

    @Test
    public void debeRetornarListaDeDatosComoUnHashMap() throws Exception {
        List<String> datosRespuesta = new ArrayList<String>();

        ConstructorRespuestas constructorRespuestas = new ConstructorRespuestas();


        Response respuesta =  constructorRespuestas.construirRespuestaParaArray(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES, datosRespuesta);

        assertThat(respuesta.getEntity() instanceof HashMap, is(true));
        assertThat(((HashMap)respuesta.getEntity()).containsKey(ElementosRaicesJSONEnum.ELEMENTO_RAIZ_CANTONES.getNombre()), is(true));
        assertThat(((HashMap)respuesta.getEntity()).containsValue(datosRespuesta), is(true));

    }
}
