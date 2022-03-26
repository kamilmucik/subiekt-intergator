package pl.estrix.subiekt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Company {

    private Long id;

    private String name;

}
