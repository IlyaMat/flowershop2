package com.accenture.flowershop.be.access.product;

import com.accenture.flowershop.be.entity.product.Product;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static jooq.flyway.db.h2.public_.Tables.*;


@Repository
public class ProductDAOImpl implements ProductDAO {


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
    public Product findProductByName(String productName) {
        Product product = DSL.using(getConnection()).selectFrom(PRODUCT)
                .where(PRODUCT.NAME.contains(productName)).fetchAny().into(Product.class);
        return product;
    }

    @Override
    public Product findProductById(int productId) {
        Product product = DSL.using(getConnection()).selectFrom(PRODUCT)
                .where(PRODUCT.ID.eq(productId)).fetchAny().into(Product.class);
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = DSL.using(getConnection()).selectFrom(PRODUCT).fetch().into(Product.class);
        return products;
    }

    @Override
    public void add(Product product) {
        DSL.using(getConnection()).insertInto(PRODUCT)
                .set(PRODUCT.NAME, product.getName())
                .set(PRODUCT.PRICE, product.getPrice()).execute();

    }











   /* @PersistenceContext
    EntityManager entityManager;

    @Override
    public Product findProductByName(String productName) {
        TypedQuery<Product> q = entityManager.createQuery(
                "select p from Product p where p.name = :name", Product.class
        );
        q.setParameter("name", productName);
        return q.getSingleResult();
    }

    @Override
    public Product findProductById(int productId) {
        TypedQuery<Product> q = entityManager.createQuery(
                "select p from Product p where p.id = :id", Product.class
        );
        q.setParameter("id", productId);
        return q.getSingleResult();
    }

    @Override
    @Transactional
    public List<Product> getAllProducts() {
        return entityManager.createQuery(
                "select p from Product p", Product.class
        ).getResultList();
    }

    @Override
    @Transactional
    public void add(Product product) {
        entityManager.persist(product);
    }*/

}
