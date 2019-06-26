package com.accenture.flowershop.be.access.customer;

import com.accenture.flowershop.be.entity.customer.Customer;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static jooq.flyway.db.h2.public_.Tables.*;


@Repository
public class CustomerDAOImpl implements CustomerDAO {


    private Connection con;

    private Connection getConnection() {
        try {
            con = DriverManager.getConnection("jdbc:h2:~/flowershop", "sa", "");
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    @Override
    public void addCustomer(Customer customer) {
        DSL.using(getConnection()).insertInto(CUSTOMER)
                .set(CUSTOMER.FULLNAME, customer.getFullname())
                .set(CUSTOMER.PHONE_NUMBER, customer.getPhoneNumber())
                .set(CUSTOMER.ADDRESS, customer.getAddress())
                .set(CUSTOMER.BALANCE, customer.getBalance())
                .set(CUSTOMER.DISCOUNT, customer.getDiscount())
                .set(CUSTOMER.USER_ID, customer.getUser().getId()).execute();
    }

    @Override
    public Customer findCustomerById(int customerId) {
        Customer customer = DSL.using(getConnection()).selectFrom(CUSTOMER)
                .where(CUSTOMER.ID.eq(customerId)).fetchAny().into(Customer.class);
        return customer;
    }

    @Override
    public Customer saveDetached(Customer customer) {
        return null;
    }

    @Override
    public Customer findCustomerByUserId(int userId) {
        Customer customer = DSL.using(getConnection()).selectFrom(CUSTOMER)
                .where(CUSTOMER.USER_ID.eq(userId)).fetchAny().into(Customer.class);
        return customer;
    }

    @Transactional
    @Override
    public Customer updateCustomer(Customer customer) {
       DSL.using(getConnection()).update(CUSTOMER)
               .set(CUSTOMER.FULLNAME, customer.getFullname())
               .set(CUSTOMER.PHONE_NUMBER, customer.getPhoneNumber())
               .set(CUSTOMER.ADDRESS, customer.getAddress())
               .where(CUSTOMER.ID.eq(customer.getId())).execute();

       return findCustomerById(customer.getId());
    }

    @Override
    public Customer updateBalanceCustomer(Customer customer) {
        DSL.using(getConnection()).update(CUSTOMER)
                .set(CUSTOMER.BALANCE, customer.getBalance())
                .where(CUSTOMER.ID.eq(customer.getId())).execute();

        return findCustomerById(customer.getId());
    }








/*


 @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public Customer findCustomerById(int customerId) {
        TypedQuery<Customer> q = entityManager.createQuery(
                "select c from Customer c where c.id = :id", Customer.class
        );
        q.setParameter("id", customerId);
        return q.getSingleResult();
    }

    @Override
    @Transactional
    public Customer saveDetached(Customer customer) {
        return entityManager.merge(customer);
    }
*/



}
