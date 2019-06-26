package com.accenture.flowershop.be.business.order;

import com.accenture.flowershop.be.access.order.OrderDAO;
import com.accenture.flowershop.be.business.customer.CustomerBusinessService;
import com.accenture.flowershop.be.business.orderproduct.OrderProductBusinessService;
import com.accenture.flowershop.be.business.stock.StockBusinessService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.entity.orderproduct.OrderProduct;
import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.fe.dto.cart.FlowerItem;
import com.accenture.flowershop.fe.dto.order.OrderDTO;
import com.accenture.flowershop.fe.dto.product.ProductDTO;
import com.accenture.flowershop.fe.enums.Order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderBusinessServiceImpl implements OrderBusinessService {

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private CustomerBusinessService customerBusinessService;
    @Autowired
    private StockBusinessService stockBusinessService;
    @Autowired
    private OrderProductBusinessService orderProductBusinessService;


    @Override
    @Transactional
    public void createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        Customer customer = customerBusinessService.findCustomerById(orderDTO.getCustomer().getId());

        order.setCustomerId(customer.getId());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setCreatedAt(orderDTO.getCreatedAt());
        order.setStatus(orderDTO.getStatus());//CREATED

        order.setId(addOrder(order).getId());//теперь получим id этого сохраненного заказа и установим

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (FlowerItem item : orderDTO.getCustomer().getCart().getBasketGoods().values()) {
            ProductDTO productDTO = item.getProduct();

            Product product = new Product();
            product.setId(productDTO.getId());
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            //OrderProduct
            OrderProduct orderProduct = new OrderProduct();
          // orderProduct.setProduct(product);
            orderProduct.setProductId(product.getId());
            orderProduct.setOrder(order);
            orderProduct.setQuantity(item.getQuantityFlower()); //кол-во этого цветка
            orderProducts.add(orderProduct);

            orderProductBusinessService.addOrderProduct(orderProduct);
        }
    }

    private Order getLastAddedOrder() {
        List<Order> orders = orderDAO.getAllOrders();
        return orders.get(orders.size() - 1);
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        orderDAO.addOrder(order);
        return getLastAddedOrder();
    }

    @Override
    @Transactional
    public Customer payOrder(int orderId, int customerId) {
        Order order = orderDAO.findOrderById(orderId);
        order.setStatus((short) OrderStatus.ОПЛАЧЕН.ordinal());
        updateOrder(order);

        Customer customer = customerBusinessService.findCustomerById(customerId);
        customer.setBalance(customer.getBalance().subtract(order.getTotalPrice()));

        //+изменим кол-во продуктов в наличии
        List<OrderProduct> orderProducts = orderProductBusinessService.findOrderProductByOrderId(orderId);
        for (OrderProduct orderProduct : orderProducts) {
           stockBusinessService.updateQuantityProduct(orderProduct.getProductId(), orderProduct.getQuantity());
        }
        return customerBusinessService.updateBalanceCustomer(customer);
    }

    @Override
    @Transactional
    public Order closeOrder(int orderId) {
        Order order = orderDAO.findOrderById(orderId);
        order.setStatus((short) OrderStatus.ЗАКРЫТ.ordinal());
        order.setClosedAt(new Date());
        orderDAO.updateOrder(order);
        return order;
    }

    @Override
    public Order findOrderById(int id) {
        return orderDAO.findOrderById(id);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orderList = orderDAO.getAllOrders();
        return orderList;
    }

    @Override
    public List<Order> findOrderByCustomer(Customer customer) {
        return orderDAO.findOrderByCustomer(customer);
    }

    @Override
    public void updateOrder(Order order) {
        orderDAO.updateOrder(order);
    }
}
