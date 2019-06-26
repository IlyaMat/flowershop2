package com.accenture.flowershop.be.business.rest;

import com.accenture.flowershop.be.business.product.ProductBusinessService;
import com.accenture.flowershop.be.business.webservice.ProductWebService;
import com.accenture.flowershop.be.entity.product.Product;
import com.accenture.flowershop.fe.dto.product.ProductDTO;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Controller
@EnableWebMvc //без этого будет ошибка 406
@RequestMapping("/flowers")
public class ProductController {

    @Autowired
    private ProductWebService productWebService;

    private MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    private MapperFacade mapper = mapperFactory.getMapperFacade();


    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productDTOS = new ArrayList<>();
        List<Product> products = productWebService.getAllProducts();
        mapperFactory.classMap(Product.class, ProductDTO.class);
        for (Product product : products) {
            ProductDTO productDTO = mapper.map(product, ProductDTO.class);
            productDTOS.add(productDTO);
        }
        return ResponseEntity.ok().body(productDTOS);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON)
    @ResponseBody
    public ResponseEntity<ProductDTO> findProductById(@PathVariable("id") int id) {
        mapperFactory.classMap(Product.class, ProductDTO.class);
        Product product = productWebService.findProductById(id);
        ProductDTO productDTO = mapper.map(product, ProductDTO.class);
        return ResponseEntity.ok().body(productDTO);
    }

    @RequestMapping(method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON)
    public ResponseEntity<ProductDTO> putProduct(@RequestBody Product product) {
        productWebService.addProduct(product);
        mapperFactory.classMap(Product.class, ProductDTO.class);
        return ResponseEntity.ok().body(mapper.map(product, ProductDTO.class));
    }


}
