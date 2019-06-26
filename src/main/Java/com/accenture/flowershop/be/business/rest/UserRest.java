package com.accenture.flowershop.be.business.rest;

import com.accenture.flowershop.be.business.user.UserBusinessService;
import com.accenture.flowershop.be.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.ws.rs.QueryParam;

@Controller
@EnableWebMvc
@RequestMapping("/user")
public class UserRest {

    @Autowired
    private UserBusinessService userBusinessService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public boolean login(@QueryParam("username") String username, @QueryParam("password") String password) {

        User user = userBusinessService.login(username, password);
        if (user != null) {
            return true;
        }
        return false;


    }

}
