package com.zhl.order.service;

import com.zhl.order.pojo.OrderInfo;
import com.zhl.utils.E3Result;

public interface OrderService {

    E3Result createOrder(OrderInfo orderInfo);
}
