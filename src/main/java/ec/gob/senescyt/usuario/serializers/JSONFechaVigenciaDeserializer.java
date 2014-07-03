package ec.gob.senescyt.usuario.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JSONFechaVigenciaDeserializer extends JsonDeserializer<DateTime> {

    DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String dateAsString = jsonParser.getText();

        return fmt.parseDateTime(dateAsString).withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    }
}
