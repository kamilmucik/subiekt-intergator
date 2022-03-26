package pl.estrix.subiekt.domain.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.estrix.subiekt.commons.Result;
import pl.estrix.subiekt.commons.SuccessResult;
import pl.estrix.subiekt.domain.ProductGateway;
import pl.estrix.subiekt.model.Product;
import pl.estrix.subiekt.service.ProductService;

import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
@RestController
public class ProductGatewayService implements ProductGateway {

    @Autowired
    private ProductService productService;

    @Override
    public Result<List<Product>> getProducts() {
        return new SuccessResult(productService.getAllProducts());
    }
}
