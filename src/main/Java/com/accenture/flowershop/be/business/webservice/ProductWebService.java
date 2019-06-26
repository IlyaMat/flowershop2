package com.accenture.flowershop.be.business.webservice;

import com.accenture.flowershop.be.entity.product.Product;

import java.util.List;

public interface ProductWebService {

    //поиск по id
    Product findProductById(int productId);

    //возврат всех цветов
    List<Product> getAllProducts();

    void addProduct(Product product);
}
