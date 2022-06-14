package br.com.queirozfood.payments.service;

import br.com.queirozfood.payments.dto.PaymentsDto;
import br.com.queirozfood.payments.model.Payment;
import br.com.queirozfood.payments.model.Status;
import br.com.queirozfood.payments.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
    }

    public Page<PaymentsDto> getAllPayments(Pageable pageable){
        return paymentRepository
                .findAll(pageable)
                .map(payment -> modelMapper.map(payment, PaymentsDto.class));
    }

    public PaymentsDto getById(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(payment, PaymentsDto.class);
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
}
