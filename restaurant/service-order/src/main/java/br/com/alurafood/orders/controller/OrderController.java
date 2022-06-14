package br.com.alurafood.orders.controller;

import br.com.alurafood.orders.dto.OrderDto;
import br.com.alurafood.orders.dto.StatusDto;
import br.com.alurafood.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderDto> listAll() {
            return service.getAll();
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDto> listById(@PathVariable @NotNull Long id) {
            OrderDto dto = service.getById(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto dto, UriComponentsBuilder uriBuilder) {
            OrderDto orderCreated = service.createOrder(dto);

            URI uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderCreated.getId()).toUri();

            return ResponseEntity.created(uri).body(orderCreated);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDto> updateStatus(@PathVariable Long id, @RequestBody StatusDto status){
           OrderDto dto = service.updateStatus(id, status);

            return ResponseEntity.ok(dto);
        }


        @PutMapping("/{id}/paid")
        public ResponseEntity<Void> approvedPayment(@PathVariable @NotNull Long id) {
            service.approvedPaymentOrder(id);

            return ResponseEntity.ok().build();
        }
}
