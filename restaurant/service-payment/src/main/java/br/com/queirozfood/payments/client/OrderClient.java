package br.com.queirozfood.payments.client;

import br.com.queirozfood.payments.dto.OrderDto;
import feign.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("order-ms")
public interface OrderClient {
    @RequestMapping(method = RequestMethod.PUT, value = "/orders/{id}/paid")
    void updatePaidPayment(@PathVariable Long id);

    @RequestMapping(method = RequestMethod.GET, value = "/orders/items/{id}")
    List<OrderDto> getItemsById(@PathVariable Long id);
}
