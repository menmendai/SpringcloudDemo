package com.mmd.springcloud.controller;

import com.mmd.springcloud.entity.CommonResult;
import com.mmd.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String ServerPort;

    /**
     * 正常的方法，访问一切正常
     */
    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = paymentService.PaymentInfo_OK(id);
        log.info("  *resule："+result);
        return result;
    }

    /**
     * 访问此方法一次需耗时3秒钟
     */
    @GetMapping("/payment/hystrix/timeout/{id}")
    public String PymentInfo_TimeOut(@PathVariable("id") Integer id){
        String result = paymentService.PymentInfo_TimeOut(id);
        log.info("  *resule："+result);
        return result;
    }

    //====服务熔断
    @GetMapping("/payment/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id)
    {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: "+result);
        return result;
    }

}
