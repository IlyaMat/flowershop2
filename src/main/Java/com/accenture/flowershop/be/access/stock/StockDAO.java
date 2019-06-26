package com.accenture.flowershop.be.access.stock;

import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.be.entity.stock.Stock;

import java.util.List;

public interface StockDAO {
    //возврат списка всех объектов на складе
    List<Stock> getAllInStock();

    //
    void updateQuantityProduct(Stock stock);

    Stock findStockByProductId(int productId);


}
