package com.accenture.flowershop.be.business.customer;

import com.accenture.flowershop.be.access.customer.CustomerDAO;
import com.accenture.flowershop.be.entity.customer.Customer;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerBusinessServiceImpl implements CustomerBusinessService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public Customer findCustomerById(int customerId) {
       return customerDAO.findCustomerById(customerId);
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    public Customer saveDetached(Customer customer) {
        return customerDAO.saveDetached(customer);
    }

    @Override
    public Customer findCustomerByUserId(int userId) {
       return customerDAO.findCustomerByUserId(userId);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    @Override
    public Customer updateBalanceCustomer(Customer customer) {
        return  customerDAO.updateBalanceCustomer(customer);
    }


}
