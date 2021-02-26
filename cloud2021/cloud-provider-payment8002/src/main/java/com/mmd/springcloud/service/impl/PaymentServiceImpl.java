package com.mmd.springcloud.service.impl;


import com.mmd.springcloud.entity.Payment;
import com.mmd.springcloud.mapper.PaymentMapper;
import com.mmd.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public int insertPayment(Payment payment) {
        return paymentMapper.insertPayment(payment);
    }

    public Payment getPayment(Long id){
        return paymentMapper.getPayment(id);
    }
}
