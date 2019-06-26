package com.accenture.flowershop.be.business.webservice;

import com.accenture.flowershop.be.business.product.ProductBusinessService;
import com.accenture.flowershop.be.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductWebServiceImpl implements ProductWebService {

    @Autowired
    private ProductBusinessService productBusinessService;

    @Override
    public Product findProductById(int productId) {
       return productBusinessService.findProductById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productBusinessService.getAllProducts();
        return products;
    }

    @Override
    public void addProduct(Product product) {
        productBusinessService.addProduct(product);
    }
}
