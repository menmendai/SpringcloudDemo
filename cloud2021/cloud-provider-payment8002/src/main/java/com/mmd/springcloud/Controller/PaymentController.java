package com.mmd.springcloud.Controller;

import com.mmd.springcloud.entity.CommonResult;
import com.mmd.springcloud.entity.Payment;
import com.mmd.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private DiscoveryClient discoveryClient;

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

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("*******服务名称有："+service);
        }
        String serviceId = "CLOUD-PAYMENT-SERVICE";
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info("CLOUD-PAYMENT-SERVICE里有："+instance.getServiceId()+"\t"+
                    "主机地址："+instance.getHost()+"\t"+
                    "端口："+instance.getPort()+"\t"+
                    "URI："+instance.getUri()+"\t");
        }
        return discoveryClient;
    }
}