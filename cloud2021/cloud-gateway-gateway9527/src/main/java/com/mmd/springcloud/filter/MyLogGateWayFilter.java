package com.mmd.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {
    /**
     * 每次访问都必须待uname这个key，才能通过这个过滤器去往下一个
     * 如果没带，那么修改响应，返回
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("**********come in MyLogGateWayFilter"+new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname"); // 获取Request的uname的值
        if (uname == null){
            log.info("*****用户名为null，非法用户！");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE); // NOT_ACCEPTABLE(406, "Not Acceptable"),
            return exchange.getResponse().setComplete(); // 修改respon请求，返回
        }

        return chain.filter(exchange); // 去下一个过滤器
    }

    /**
     * 加载过滤器的顺序，数字越小，优先度越高，最小值int的最小值，最大同理
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
