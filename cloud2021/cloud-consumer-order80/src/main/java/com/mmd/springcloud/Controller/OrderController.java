package com.mmd.springcloud.Controller;

import com.mmd.springcloud.entity.CommonResult;
import com.mmd.springcloud.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class OrderController {

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/insertPayment")
    public CommonResult<Integer> insertPayment(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/insertPayment",payment,CommonResult.class);
    }

    @GetMapping("/consumer/payment/getPayment/{id}")
    public CommonResult<Payment> getPayment(@PathVariable(name="id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/getPayment/"+id,CommonResult.class);
    }

    /**
     * 利用getForEntity远程调用
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id)
    {
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL+"/payment/getPayment/"+id,CommonResult.class);

        if(entity.getStatusCode().is2xxSuccessful()){
            log.info("         "+entity.toString()+"            ");
            return entity.getBody();
        }else{
            return CommonResult.FAIL("操作失败");
        }
    }
}
