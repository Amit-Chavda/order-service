package orderservice.controller;

import lombok.RequiredArgsConstructor;
import orderservice.dto.GenericResponse;
import orderservice.dto.OrderDto;
import orderservice.service.OrderService;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<GenericResponse> placeOrder(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(new GenericResponse(true, "Order placed successfully", orderService.placeOrder(orderDto), HttpStatus.OK.value(), LocalDateTime.now()), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<GenericResponse> getOrders(@PathVariable long userId) {
        return new ResponseEntity<>(new GenericResponse(true, "Orders returned successfully", orderService.getOrders(userId), HttpStatus.OK.value(), LocalDateTime.now()), HttpStatus.OK);
    }
}
