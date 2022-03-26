package pl.estrix.subiekt.mock;

import org.springframework.context.annotation.Bean;
import pl.estrix.commons.mock.BaseMockService;
import pl.estrix.commons.mock.ReplacingMockComponent;
import pl.estrix.subiekt.model.Company;
import pl.estrix.subiekt.model.Product;
import pl.estrix.subiekt.service.ProductService;

import java.util.Arrays;
import java.util.List;

@ReplacingMockComponent( clazz = pl.estrix.subiekt.service.impl.ProductServiceImpl.class)
public class MockProductService extends BaseMockService {

    @Bean(name = "mockProductSerivce")
    ProductService productSerivce() {
        return new ProductService() {

            @Override
            public List<Product> getAllProducts() {
                return Arrays.asList(
                        Product.builder().id(1L).name("Produkt 1").build(),
                        Product.builder().id(2L).name("Produkt 2").build(),
                        Product.builder().id(3L).name("Produkt 3").build()
                );
            }

            @Override
            public List<Company> getCompanies() {
                return Arrays.asList(
                        Company.builder().id(1L).name("Rossmann").build(),
                        Company.builder().id(2L).name("Cztery Konie").build(),
                        Company.builder().id(3L).name("Regimnton").build()
                );
            }
        };
    }
}
