package com.accenture.flowershop.be.access.order;

import com.accenture.flowershop.be.entity.customer.Customer;
import com.accenture.flowershop.be.entity.order.Order;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import static jooq.flyway.db.h2.public_.Tables._ORDER;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

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
    public Order findOrderById(int orderId) {
        Order order = DSL.using(getConnection()).selectFrom(_ORDER)
                .where(_ORDER.ID.eq(orderId)).fetchAny().into(Order.class);
        return order;
    }

    @Override
    public List<Order> findOrderByCustomer(Customer customer) {
        List<Order> orders = DSL.using(getConnection()).selectFrom(_ORDER)
                .where(_ORDER.CUSTOMER_ID.eq(customer.getId())).fetch().into(Order.class);
        return orders;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = DSL.using(getConnection()).selectFrom(_ORDER).fetch().into(Order.class);
        return orders;
    }

    @Transactional
    @Override
    public void addOrder(Order order) {
       DSL.using(getConnection()).insertInto(_ORDER)
                .set(_ORDER.TOTAL_PRICE, order.getTotalPrice())
                .set(_ORDER.STATUS,order.getStatus())
                .set(_ORDER.CREATED_AT, order.getCreatedAt())
                .set(_ORDER.CLOSED_AT,order.getClosedAt())
                .set(_ORDER.CUSTOMER_ID, order.getCustomerId()).execute();

    }

    @Override
    public void updateOrder(Order order) {
        DSL.using(getConnection()).update(_ORDER)
                .set(_ORDER.CLOSED_AT, order.getClosedAt())
                .set(_ORDER.STATUS, order.getStatus())
                .where(_ORDER.ID.eq(order.getId())).execute();

    }

    /*@PersistenceContext
    EntityManager entityManager;

    @Override
    public Order findOrderById(long orderId) {
        TypedQuery<Order> q = entityManager.createQuery(
                "select o from Order o where o.id = :id",Order.class
        );
        q.setParameter("id",orderId);
        return q.getSingleResult();
    }

    @Override
    public List<Order> findOrderByCustomer(Customer customer) {
        TypedQuery<Order> q = entityManager.createQuery(
                "select o from Order o where o.customer = :customer", Order.class);
        q.setParameter("customer", customer);
        return q.getResultList();
    }

    @Override
    public List<Order> getAllOrders() {
      return entityManager.createQuery(
                "select o from Order o",Order.class
        ).getResultList();
    }

    @Override
    public void addOrder(Order order) {
        entityManager.persist(order);
    }*/
}
