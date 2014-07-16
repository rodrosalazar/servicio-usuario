package ec.gob.senescyt.commons.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ec.gob.senescyt.commons.Constantes;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JSONFechaSerializer extends JsonSerializer<DateTime> {

    DateTimeFormatter fmt = DateTimeFormat.forPattern(Constantes.DATE_FORMAT);

    @Override
    public void serialize(DateTime date, JsonGenerator gen, SerializerProvider provider) throws IOException, JsonProcessingException {
        gen.writeString(date.toString(fmt));
    }
}
