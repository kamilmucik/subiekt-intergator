package pl.estrix.subiekt.service;

import pl.estrix.subiekt.model.Company;
import pl.estrix.subiekt.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    List<Company> getCompanies();


}
