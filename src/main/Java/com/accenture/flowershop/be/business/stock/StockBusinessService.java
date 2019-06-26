package com.accenture.flowershop.be.business.stock;

import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.be.entity.stock.Stock;

public interface StockBusinessService {

    void updateQuantityProduct(int productId, int quantity);

    Stock findStockByProductId(Product product);
}
