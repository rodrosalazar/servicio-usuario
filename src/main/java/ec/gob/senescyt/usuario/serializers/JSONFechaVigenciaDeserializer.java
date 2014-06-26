package ec.gob.senescyt.usuario.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by njumbo on 26/06/14.
 */
public class JSONFechaVigenciaDeserializer extends JsonDeserializer<DateTime> {

    DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateAsString = jsonParser.getText();

        return fmt.parseDateTime(dateAsString).withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    }
}
