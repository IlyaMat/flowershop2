package com.accenture.flowershop.fe.servlets.cart;

import com.accenture.flowershop.fe.dto.customer.CustomerDTO;
import com.accenture.flowershop.fe.dto.user.UserDTO;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/clear")
public class ClearCartServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        CustomerDTO customer = user.getCustomer();
        customer.getCart().clearCart();
        user.setCustomer(customer);

        //System.out.println("customer.getCart().getBasketGoods().size() = " + customer.getCart().getBasketGoods().size());

        req.setAttribute("user", user);
        resp.sendRedirect("/cart");
       //req.getRequestDispatcher("/cart").forward(req, resp);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
