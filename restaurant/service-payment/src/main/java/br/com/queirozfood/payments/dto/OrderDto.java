package br.com.queirozfood.payments.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {

    private Long id;
    private Integer quantity;
    private String description;
}
