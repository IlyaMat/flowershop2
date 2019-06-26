package com.accenture.flowershop.be.access.orderproduct;

import com.accenture.flowershop.be.entity.orderproduct.OrderProduct;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static jooq.flyway.db.h2.public_.Tables._ORDER_PRODUCT;


@Repository
public class OrderProductDAOImpl implements OrderProductDAO {


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
    public OrderProduct findOrderProductById(int orderProductId) {
        OrderProduct orderProduct = DSL.using(getConnection()).selectFrom(_ORDER_PRODUCT)
                .where(_ORDER_PRODUCT.ID.eq(orderProductId)).fetchAny().into(OrderProduct.class);
        return orderProduct;
    }

    @Override
    public List<OrderProduct> getAllOrdersProducts() {
        List<OrderProduct> orderProducts = DSL.using(getConnection()).selectFrom(_ORDER_PRODUCT).fetch().into(OrderProduct.class);
        return orderProducts;
    }

    @Override
    public void addOrderProduct(OrderProduct orderProduct) {
        DSL.using(getConnection()).insertInto(_ORDER_PRODUCT)
                .set(_ORDER_PRODUCT.ORDER_ID, orderProduct.getOrder().getId())
                .set(_ORDER_PRODUCT.PRODUCT_ID, orderProduct.getProductId())
                .set(_ORDER_PRODUCT.QUANTITY, orderProduct.getQuantity()).execute();
    }

    @Override
    public List<OrderProduct> findOrderProductByOrderId(int orderId) {
        List<OrderProduct> orderProducts = DSL.using(getConnection()).selectFrom(_ORDER_PRODUCT)
                .where(_ORDER_PRODUCT.ORDER_ID.eq(orderId)).fetch().into(OrderProduct.class);
        return orderProducts;
    }




    /*@PersistenceContext
    EntityManager entityManager;

    @Override
    public OrderProduct findOrderProductById(long orderProductId) {
        TypedQuery<OrderProduct> q = entityManager.createQuery(
                "select op from OrderProduct where op.id = :id", OrderProduct.class
        );
        q.setParameter("id", orderProductId);
        return q.getSingleResult();
    }

    @Override
    public List<OrderProduct> getAllOrdersProducts() {
        return entityManager.createQuery(
                "select op from OrderProduct op", OrderProduct.class
        ).getResultList();
    }

    @Override
    public void addOrderProduct(OrderProduct orderProduct) {
        entityManager.persist(orderProduct);
    }

    @Override
    public List<OrderProduct> findOrderProductByOrderId(int id) {
        return entityManager.createQuery(
                "select op from OrderProduct where op.order_id = :id", OrderProduct.class)
                .setParameter("id", id).getResultList();
    }*/
}
