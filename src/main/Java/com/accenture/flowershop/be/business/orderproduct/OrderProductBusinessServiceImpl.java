package com.accenture.flowershop.be.business.orderproduct;

import com.accenture.flowershop.be.access.orderproduct.OrderProductDAO;
import com.accenture.flowershop.be.business.order.OrderBusinessService;
import com.accenture.flowershop.be.entity.orderproduct.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductBusinessServiceImpl implements OrderProductBusinessService {

    @Autowired
    private OrderProductDAO orderProductDAO;

    @Override
    public OrderProduct findOrderProductById(int orderProductId) {
        return orderProductDAO.findOrderProductById(orderProductId);
    }

    @Override
    public List<OrderProduct> getAllOrdersProducts() {
        List<OrderProduct> orderProducts = orderProductDAO.getAllOrdersProducts();
        return orderProducts;
    }

    @Override
    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductDAO.addOrderProduct(orderProduct);
    }

    @Override
    public List<OrderProduct> findOrderProductByOrderId(int id) {
        List<OrderProduct> orderProducts = orderProductDAO.findOrderProductByOrderId(id);
        return orderProducts;
    }
}
