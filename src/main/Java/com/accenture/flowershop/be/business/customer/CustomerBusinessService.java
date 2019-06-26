package com.accenture.flowershop.be.business.customer;

import com.accenture.flowershop.be.entity.customer.Customer;

public interface CustomerBusinessService {
    //поиск по id
    Customer findCustomerById(int customerId);

    public void addCustomer(Customer customer);

    Customer saveDetached(Customer customer);

    Customer findCustomerByUserId(int userId);

    Customer updateCustomer(Customer customer);

    Customer updateBalanceCustomer(Customer customer);

}
