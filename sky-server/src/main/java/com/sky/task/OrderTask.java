package com.sky.task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**处理订单超时
     *
     */
    @Scheduled(cron = "0 * * * * ? ") //每分钟触发一次
    public void processTimeout(){
        log.info("处理超时订单");
        List<Orders> orders = orderMapper.getByStatusandOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().minusMinutes(15));

        if(orders.size()>0){
            for(Orders order:orders){
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason("订单超时，自动取消");
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            }
        }
    }

    /**处理一直派送中的订单
     *
     */
    @Scheduled(cron = "0 0 1 * * ? ")
    public void processDeliveryOrder(){
        log.info("处理上一天派送中未确认的订单");

        List<Orders> orders = orderMapper.getByStatusandOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().minusMinutes(65));
        if(orders.size()>0){
            for(Orders order:orders){
                order.setStatus(Orders.COMPLETED);
                orderMapper.update(order);

            }
        }

    }

}
