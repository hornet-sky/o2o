package my.ssm.o2o.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JacksonUtil {
    public static ObjectMapper mapper = new ObjectMapper();
    private JacksonUtil() {}
    public static <T> T parse(String jsonStr, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        return mapper.readValue(jsonStr, clazz);
    }
}
