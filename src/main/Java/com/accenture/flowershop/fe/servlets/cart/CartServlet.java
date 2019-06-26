package com.accenture.flowershop.fe.servlets.cart;

import com.accenture.flowershop.be.business.customer.CustomerBusinessService;
import com.accenture.flowershop.be.business.order.OrderBusinessService;
import com.accenture.flowershop.be.business.user.UserBusinessService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.fe.dto.cart.FlowerItem;
import com.accenture.flowershop.fe.dto.order.OrderDTO;
import com.accenture.flowershop.fe.dto.user.UserDTO;
import com.accenture.flowershop.fe.enums.Order.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

@WebServlet("/cart")//и изменяем статус заказа на СОЗДАННЫЙ
public class CartServlet extends HttpServlet {

    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private CustomerBusinessService customerBusinessService;
    @Autowired
    private UserBusinessService userBusinessService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        //получаем поля фомы
        String fullname = req.getParameter("fullname");
        String phoneNumber = req.getParameter("phoneNumber");
        String address = req.getParameter("address");

        UserDTO user = (UserDTO) session.getAttribute("user");


        User user1 = userBusinessService.findUserById((int) user.getId());////////////

        Customer customer = customerBusinessService.findCustomerById(user.getCustomer().getId());
        customer.setFullname(fullname);
        customer.setPhone_number(phoneNumber);
        customer.setAddress(address);
        customer.setUser(user1);
        //данные покупателя же нужно сохранить
        customerBusinessService.updateCustomer(customer);


        //OrderDTO
        OrderDTO order = new OrderDTO();
        order.setCustomer(user.getCustomer());

        // смотрим есть ли у покупателя скидка
        BigDecimal priceWithDiscount = user.getCustomer().getCart().getTotalPriceWithDiscount();
        BigDecimal priceWithoutDiscount = user.getCustomer().getCart().getTotalPrice();
        if (priceWithDiscount != null) {
            order.setTotalPrice(priceWithDiscount);
        } else {
            order.setTotalPrice(priceWithoutDiscount);
        }


        order.setCreatedAt(new Date());//а возвращает timestamp
        order.setStatus((short)OrderStatus.СОЗДАН.ordinal());

        //
        orderBusinessService.createOrder(order);

        //resp.sendRedirect("/cart.jsp");

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

    }
}
