package br.com.queirozfood.payments.service;

import br.com.queirozfood.payments.client.OrderClient;
import br.com.queirozfood.payments.dto.DetailPaymentDto;
import br.com.queirozfood.payments.dto.OrderDto;
import br.com.queirozfood.payments.dto.PaymentsDto;
import br.com.queirozfood.payments.model.Payment;
import br.com.queirozfood.payments.model.Status;
import br.com.queirozfood.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;
    private OrderClient orderClient;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper, OrderClient orderClient) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
        this.orderClient = orderClient;
    }

    public Page<PaymentsDto> getAllPayments(Pageable pageable){
        return paymentRepository
                .findAll(pageable)
                .map(payment -> modelMapper.map(payment, PaymentsDto.class));
    }

    public DetailPaymentDto detailPaymentById(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderDto> itemsById = orderClient.getItemsById(payment.getOrderId());
        PaymentsDto paymentsDto = modelMapper.map(payment, PaymentsDto.class);

        return new DetailPaymentDto(paymentsDto, itemsById);
    }

    public PaymentsDto createPayment(PaymentsDto paymentsDto){
        Payment newPayment = modelMapper.map(paymentsDto, Payment.class);
        newPayment.setStatus(Status.CREATED);
        paymentRepository.save(newPayment);

        return modelMapper.map(newPayment, PaymentsDto.class);
    }

    public PaymentsDto updatePayment(Long id, PaymentsDto paymentsDto){
        Payment paymentToUpdate = modelMapper.map(paymentsDto, Payment.class);
        paymentToUpdate.setId(id);
        paymentRepository.save(paymentToUpdate);

        return modelMapper.map(paymentToUpdate, PaymentsDto.class);
    }

    public void deletePayment(Long id){
        paymentRepository.deleteById(id);
    }

    public void confirmPayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if (!payment.isPresent()){
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED);
        paymentRepository.save(payment.get());
        orderClient.updatePaidPayment(payment.get().getOrderId());
    }

    public void fallBackStatus(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (!payment.isPresent()){
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.PARTIALLY_CONFIRMED);
        paymentRepository.save(payment.get());
    }
}
