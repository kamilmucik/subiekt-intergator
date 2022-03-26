package pl.estrix.subiekt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class Product {

    private Long id;
    private String name;


    private BigDecimal price;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price) {
        this.id    = id;
        this.name  = name;
        this.price = price;
    }

    public Product(String name, BigDecimal price) {
        this.name  = name;
        this.price = price;
    }

    // getters & setters

}