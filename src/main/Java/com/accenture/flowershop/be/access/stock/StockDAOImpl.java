package com.accenture.flowershop.be.access.stock;

import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.be.entity.stock.Stock;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static jooq.flyway.db.h2.public_.Tables.STOCK;

@Repository
public class StockDAOImpl implements StockDAO {


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
    public List<Stock> getAllInStock() {
        List<Stock> stocks = DSL.using(getConnection()).selectFrom(STOCK).fetch().into(Stock.class);
        return stocks;
    }

    @Override
    public void updateQuantityProduct(Stock stock) {
        DSL.using(getConnection()).update(STOCK)
                .set(STOCK.QUANTITY, stock.getQuantity())
                .where(STOCK.ID.eq(stock.getId())).execute();
    }


    @Override
    public Stock findStockByProductId(int productId) {
        Stock stock = DSL.using(getConnection()).selectFrom(STOCK)
                .where(STOCK.PRODUCT_ID.eq(productId)).fetchAny().into(Stock.class);
        return stock;
    }








   /* @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Stock> getAllInStock() {
        return entityManager.createQuery(
                "select s from Stock s", Stock.class
        ).getResultList();
    }

    @Override
    @Transactional
    public void addStock(Stock stock) {
        entityManager.persist(stock);
    }

    @Override
    public Stock findStockByProduct(Product product) {
        TypedQuery<Stock> q = entityManager.createQuery(
                "select s from Stock s where s.product = :product", Stock.class
        ).setParameter("product", product);
        return q.getSingleResult();
    }*/
}
