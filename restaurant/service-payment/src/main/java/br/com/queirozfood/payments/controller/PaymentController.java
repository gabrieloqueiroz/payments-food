package br.com.queirozfood.payments.controller;

import br.com.queirozfood.payments.dto.PaymentsDto;
import br.com.queirozfood.payments.model.Payment;
import br.com.queirozfood.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

    PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public Page<PaymentsDto> getAllPayments(@PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return paymentService.getAllPayments(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentsDto> getPaymentById(@PathVariable @NotNull Long id){
       return ResponseEntity.ok(paymentService.getById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentsDto> createPayment(@RequestBody @Valid PaymentsDto paymentsDto, UriComponentsBuilder uriBuilder){
        PaymentsDto newPayment = paymentService.createPayment(paymentsDto);

        URI path = uriBuilder.path("/payments/{id}").buildAndExpand(newPayment.getId()).toUri();

        return ResponseEntity.created(path).body(newPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentsDto> updatePayment(@PathVariable @NotNull Long id, @RequestBody  @Valid PaymentsDto paymentsDto){
        return ResponseEntity.ok(paymentService.updatePayment(id, paymentsDto));
    }

    @PatchMapping("/{id}/confirm")
    public void confirmPayment(@PathVariable @NotNull Long id){
        paymentService.confirmPayment(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentsDto> deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);

        return ResponseEntity.noContent().build();
    }
}
