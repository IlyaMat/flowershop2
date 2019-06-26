package com.accenture.flowershop.fe.servlets.user;

import com.accenture.flowershop.be.business.customer.CustomerBusinessService;
import com.accenture.flowershop.be.business.jms.JmsService;
import com.accenture.flowershop.be.business.product.ProductBusinessService;
import com.accenture.flowershop.be.business.user.Marshalling.UserMarshallingServiceImpl;
import com.accenture.flowershop.be.business.user.UserBusinessService;
import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.be.entity.user.User;
import com.accenture.flowershop.fe.dto.cart.Cart;
import com.accenture.flowershop.fe.dto.customer.CustomerDTO;
import com.accenture.flowershop.fe.dto.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Autowired
    private UserBusinessService userBusinessService;
    @Autowired
    private CustomerBusinessService customerBusinessService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserDTO userDTO = new UserDTO();
        CustomerDTO customerDTO = new CustomerDTO();

        userDTO.setUsername(username);
        userDTO.setPassword(password);

        User user = userBusinessService.login(userDTO.getUsername(), userDTO.getPassword());
        if (user == null) {
            resp.setContentType("text/html;charset=UTF-8");
            req.setAttribute("errorMsg", "Такой пользователь не найден!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);

        } else {
            userDTO.setUserRole(user.getRole());

            System.out.println(user.getId());
            //customerDTO
            Customer customer = customerBusinessService.findCustomerByUserId(user.getId());

            customerDTO.setId(customer.getId());
            customerDTO.setFullname(customer.getFullname());
            customerDTO.setPhoneNumber(customer.getPhoneNumber());
            customerDTO.setAddress(customer.getAddress());
            customerDTO.setBalance(customer.getBalance());
            customerDTO.setDiscount(customer.getDiscount());
            //


            userDTO.setId(user.getId());
            userDTO.setCustomer(customerDTO);
            //Cart
            Cart cart = new Cart();
            cart.setDiscount(customer.getDiscount());
            //
            userDTO.getCustomer().setCart(cart);

            HttpSession session = req.getSession();
            session.setAttribute("user", userDTO);

            // jmsService.sendMessageJMS("heffg");

            //перенаправляем
            resp.sendRedirect("/products");

        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

}
