package com.accenture.flowershop.be.entity.orderproduct;

import com.accenture.flowershop.be.entity.order.Order;
import com.accenture.flowershop.be.entity.product.Product;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "_order_product")
@Transactional
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id")
    private int productId;

    /*@ManyToOne
    @JoinColumn(name = "product_id")*/
    //private Product product;

    @Column(name = "quantity")
    private int quantity;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

   /* public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }*/

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "id=" + id +
                ", order=" + order +
                ", productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
