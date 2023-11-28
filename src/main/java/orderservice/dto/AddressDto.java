package orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private long id;
    private String addressLine1;
    private String addressLine2;
    private String street;
    private String city;
    private String state;
    private String country;
    private int postCode;
}
