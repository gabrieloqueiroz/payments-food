package br.com.queirozfood.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DetailPaymentDto {
    private PaymentsDto paymentsDto;
    private List<OrderDto> items;
}
