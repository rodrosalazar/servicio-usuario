package ec.gob.senescyt.commons.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ec.gob.senescyt.commons.Constantes;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

public class JSONFechaDeserializer extends JsonDeserializer<DateTime> {

    DateTimeFormatter fmt = DateTimeFormat.forPattern(Constantes.DATE_FORMAT);

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String dateAsString = jsonParser.getText();

        return fmt.parseDateTime(dateAsString).withZone(DateTimeZone.UTC).withTimeAtStartOfDay();
    }
}
