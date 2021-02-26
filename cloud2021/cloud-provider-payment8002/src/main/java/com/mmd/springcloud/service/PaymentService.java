package com.mmd.springcloud.service;

import com.mmd.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {
    /**
     * 新增一笔账单
     * @param payment 账单
     * @return 账单id
     */
    public int insertPayment(Payment payment);

    /**
     * 根据id获取一笔账单的信息
     * @param id id
     * @return 账单
     */
    public Payment getPayment(@Param("id")Long id);
}
