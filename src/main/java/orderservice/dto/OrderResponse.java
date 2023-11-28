package orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long orderId;

    private long productId;
    private String productName;
    private int quantity;
    private double unitPrice;

    private long userId;
    private String customerName;
    private double totalAmount;

    private AddressDto shippingAddress;
}
