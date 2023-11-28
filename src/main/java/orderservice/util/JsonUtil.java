package orderservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import orderservice.dto.AddressDto;
import orderservice.dto.ProductDto;
import orderservice.dto.UserDto;

import java.util.Objects;

@Slf4j
public class JsonUtil {

    private JsonUtil() {
        throw new IllegalArgumentException("Utility Class");
    }

    public static final String PRODUCT_ENDPOINT = "http://localhost:5002/product/";
    public static final String USER_ENDPOINT = "http://localhost:5001/user/";

    public static final String ADDRESS_ENDPOINT = "http://localhost:5001/address/";

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static ProductDto toProductDto(Object object) {
        if (Objects.nonNull(object)) {
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(object), ProductDto.class);
            } catch (JsonProcessingException e) {
                log.error("Error while parsing json: " + e.getMessage(), e);
            }
        }
        return new ProductDto();
    }

    public static UserDto toUserDto(Object object) {
        if (Objects.nonNull(object)) {
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(object), UserDto.class);
            } catch (JsonProcessingException e) {
                log.error("Error while parsing json: " + e.getMessage(), e);
            }
        }
        return new UserDto();
    }

    public static AddressDto toAddressDto(Object object) {
        if (Objects.nonNull(object)) {
            try {
                return objectMapper.readValue(objectMapper.writeValueAsString(object), AddressDto.class);
            } catch (JsonProcessingException e) {
                log.error("Error while parsing json: " + e.getMessage(), e);
            }
        }
        return new AddressDto();
    }

}
