package com.telabase.json.formatter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<Date> {

	private SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	@Override
	public void serialize(Date d, JsonGenerator gen, SerializerProvider pro)
			throws IOException, JsonProcessingException {

		gen.writeString(fm.format(d));

	}

}
