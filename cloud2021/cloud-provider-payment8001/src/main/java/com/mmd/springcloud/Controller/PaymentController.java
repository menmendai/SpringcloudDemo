package com.mmd.springcloud.Controller;

import com.mmd.springcloud.entity.CommonResult;
import com.mmd.springcloud.entity.Payment;
import com.mmd.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.PastValidatorForYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;


    /**
     * 新增一笔账单
     * @param payment 前端传过来的账单信息
     * @return  新增的账单id
     */
    @PostMapping("/payment/insertPayment")
    public CommonResult<Integer> insertPayment(@RequestBody Payment payment){
        log.info("插入--------------------------"+payment);
        int result = paymentService.insertPayment(payment);
        log.info("插入结果："+result);
        if (result > 0) {
            return new CommonResult<>(200, "新增成功,serverPort: " + serverPort, result);
        }
        return new CommonResult<>(404, "新增失败");
    }

    /**
     * 查询一笔账单
     * @param id 查询账单的id
     * @return
     */
    @GetMapping("/payment/getPayment/{id}")
    public CommonResult<Payment> getPayment(@PathVariable(name="id") Long id){
        Payment payment = paymentService.getPayment(id);
        log.info("获取结果："+payment);
        if (payment != null){
            return new CommonResult<>(200, "查询成功！,serverPort: " + serverPort, payment);
        }
        return new CommonResult<>(404, "查询失败,查询的id为:"+id);
    }

    /**
     * 故意写一个需要3秒才能返回的程序，测试Feign的超时控制
     */
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            return serverPort;
        }
    }
}
