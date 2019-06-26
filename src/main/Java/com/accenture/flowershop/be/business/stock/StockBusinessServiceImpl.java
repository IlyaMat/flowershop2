package com.accenture.flowershop.be.business.stock;

import com.accenture.flowershop.be.access.stock.StockDAO;
import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.be.entity.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StockBusinessServiceImpl implements StockBusinessService {

    @Autowired
    private StockDAO stockDAO;

    @Override
    @Transactional
    public void updateQuantityProduct(int productId, int quantity) {
        Stock stock = stockDAO.findStockByProductId(productId);
        stock.setQuantity(stock.getQuantity() - quantity);
        stockDAO.updateQuantityProduct(stock);

    }

    @Override
    public Stock findStockByProductId(Product product) {
        return stockDAO.findStockByProductId(product.getId());
    }
}
