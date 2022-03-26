package pl.estrix.subiekt.domain;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.estrix.subiekt.commons.Result;
import pl.estrix.subiekt.model.Product;

import java.util.List;

/**
 * Created by Kamil on 30-07-2020.
 */
@RequestMapping(value = "/api/v1/product")
public interface ProductGateway {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    Result<List<Product>> getProducts();

//    @RequestMapping(value = "/details", method = RequestMethod.POST)
//    Result<Product> getUserDetails(@RequestBody GetUserDetailsRequest request);
}
