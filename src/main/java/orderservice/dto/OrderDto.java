package orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private long id;
    private long productId;
    private int quantity;
    private long userId;
    private long shippingAddressId;
}
