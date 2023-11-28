package orderservice.converter;


import lombok.RequiredArgsConstructor;
import orderservice.dto.AddressDto;
import orderservice.dto.OrderResponse;
import orderservice.entity.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter implements Converter<Order, OrderResponse> {


    @Override
    public OrderResponse convert(Order source) {
        return new OrderResponse(source.getId(), source.getProductId(), source.getProductName(), source.getQuantity(), source.getUnitPrice(), source.getUserId(), source.getCustomerName(), source.getTotalAmount(), null);
    }


    public OrderResponse convert(Order source, AddressDto addressDto) {
        return new OrderResponse(source.getId(), source.getProductId(), source.getProductName(), source.getQuantity(), source.getUnitPrice(), source.getUserId(), source.getCustomerName(), source.getTotalAmount(), addressDto);
    }
}
