package pl.estrix.subiekt.controller.ui;

import lombok.Getter;
import lombok.Setter;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.estrix.subiekt.model.Company;
import pl.estrix.subiekt.model.Product;
import pl.estrix.subiekt.service.ProductService;

import java.util.List;

@Getter
@Setter
@Scope( value          = "session")
@Component( value          = "productList")
@ELBeanName( value          = "productList")
@Join(       path = "/", to = "/product-list.jsf")
public class ProductListController {

    private List<Product> products;
    private List<Company> companies;
    private Company company;

    @Autowired
    ProductService productService;

    @Deferred
    @RequestAction
    @IgnorePostback
    public void loadData() {
        products = productService.getAllProducts();
        companies = productService.getCompanies();
    }

}