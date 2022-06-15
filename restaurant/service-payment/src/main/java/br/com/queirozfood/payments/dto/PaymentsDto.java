package br.com.queirozfood.payments.dto;

import br.com.queirozfood.payments.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentsDto {

    private Long id;
    private BigDecimal amount;
    private String name;
    private String pan;
    private String expirationDate;
    private String cvv;
    private Status status;
    private Long orderId;
    private Long transactionTypeId;
}
