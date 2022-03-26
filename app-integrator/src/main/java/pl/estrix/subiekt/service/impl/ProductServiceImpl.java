package pl.estrix.subiekt.service.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import pl.estrix.commons.mock.ReplaceableComponent;
import pl.estrix.subiekt.model.Company;
import pl.estrix.subiekt.model.Product;
import pl.estrix.subiekt.service.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Primary
@ReplaceableComponent
public class ProductServiceImpl implements ProductService {

    static List<Product> products = new ArrayList();

    static {
        Product product1 = new Product(1L, "p1", new BigDecimal(200));
        Product product2 = new Product(2L, "p2", new BigDecimal(300));
        Product product3 = new Product(3L, "p3", new BigDecimal(400));
        Product product4 = new Product(4L, "p4", new BigDecimal(500));
        Product product5 = new Product(5L, "p5", new BigDecimal(600));

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);

    }

    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public List<Company> getCompanies() {
        return new ArrayList<>();
    }
}
