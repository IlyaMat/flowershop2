package com.accenture.flowershop.be.business.orderproduct;

import com.accenture.flowershop.be.entity.orderproduct.OrderProduct;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderProductBusinessService {
    //поиск по id
    OrderProduct findOrderProductById(int orderProductId);

    //возврат списка всех объектов
    List<OrderProduct> getAllOrdersProducts();

    //сохраняет/добавляет OrderProduct
    void addOrderProduct(OrderProduct orderProduct);

    List<OrderProduct> findOrderProductByOrderId(int id);
}
