package br.com.queirozfood.orders.dto;

import br.com.queirozfood.orders.model.Status;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDateTime dateTime;
    private Status status;
    private List<OrderItemDto> items = new ArrayList<>();
}
