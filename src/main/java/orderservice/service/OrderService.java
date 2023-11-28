package orderservice.service;

import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import orderservice.converter.OrderConverter;
import orderservice.dto.*;
import orderservice.entity.Order;
import orderservice.repository.OrderRepository;
import orderservice.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final RestTemplate restTemplate;

    @Transactional
    @Retry(name = "retryPlaceOrder")
    public OrderResponse placeOrder(OrderDto orderDto) {

        ResponseEntity<GenericResponse> productResponseEntity = restTemplate.getForEntity(JsonUtil.PRODUCT_ENDPOINT + orderDto.getProductId(), GenericResponse.class);
        ResponseEntity<GenericResponse> userResponseEntity = restTemplate.getForEntity(JsonUtil.USER_ENDPOINT + orderDto.getUserId(), GenericResponse.class);
        ResponseEntity<GenericResponse> addressResponseEntity = restTemplate.getForEntity(JsonUtil.ADDRESS_ENDPOINT + orderDto.getShippingAddressId(), GenericResponse.class);

        if (isValidResponse(productResponseEntity) && isValidResponse(userResponseEntity) && isValidResponse(addressResponseEntity)) {
        } else {
            throw new IllegalStateException("Invalid header response");
        }

        if (productResponseEntity.getStatusCode().is2xxSuccessful() && userResponseEntity.getStatusCode().is2xxSuccessful() && addressResponseEntity.getStatusCode().is2xxSuccessful()) {

            Object productObject = Objects.requireNonNull(productResponseEntity.getBody()).getData();
            ProductDto productDto = JsonUtil.toProductDto(productObject);

            Object userObject = Objects.requireNonNull(userResponseEntity.getBody()).getData();
            UserDto userDto = JsonUtil.toUserDto(userObject);


            Object addressObject = Objects.requireNonNull(addressResponseEntity.getBody()).getData();
            AddressDto addressDto = JsonUtil.toAddressDto(addressObject);

            String customerName = userDto.getFirstName() + " " + userDto.getLastName();
            double totalAmount = orderDto.getQuantity() * productDto.getPrice();

            Order order = orderRepository.save(
                    Order.builder()
                            .userId(orderDto.getUserId())
                            .customerName(customerName)
                            .productId(orderDto.getProductId())
                            .productName(productDto.getProductName())
                            .unitPrice(productDto.getPrice())
                            .shippingAddressId(addressDto.getId())
                            .quantity(orderDto.getQuantity())
                            .totalAmount(totalAmount).build()
            );

            return OrderResponse.builder()
                    .orderId(order.getId())
                    .productId(orderDto.getProductId())
                    .productName(productDto.getProductName())
                    .unitPrice(productDto.getPrice())
                    .userId(orderDto.getUserId())
                    .customerName(customerName)
                    .quantity(orderDto.getQuantity())
                    .totalAmount(totalAmount)
                    .shippingAddress(addressDto)
                    .build();
        }
        throw new IllegalStateException("Temporary failure in placing order! Please try after sometime.");
    }

    @Retry(name = "retryGetOrders")
    public List<OrderResponse> getOrders(long userId) {
        return orderRepository.findByUserId(userId).stream().map(order -> {
            ResponseEntity<GenericResponse> addressResponseEntity = restTemplate.getForEntity(JsonUtil.ADDRESS_ENDPOINT + order.getShippingAddressId(), GenericResponse.class);
            Object addressObject = Objects.requireNonNull(addressResponseEntity.getBody()).getData();
            AddressDto addressDto = JsonUtil.toAddressDto(addressObject);
            return orderConverter.convert(order, addressDto);
        }).toList();
    }

    public boolean isValidResponse(ResponseEntity<GenericResponse> responseEntity) {
        String headerValue = responseEntity.getHeaders().getFirst("X-VALID");
        return StringUtils.isNotEmpty(headerValue) && headerValue.equals("ABC123");
    }
}
