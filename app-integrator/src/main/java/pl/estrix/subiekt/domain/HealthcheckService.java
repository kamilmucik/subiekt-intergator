package pl.estrix.subiekt.domain;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.estrix.subiekt.commons.Result;

@RequestMapping(value = "/api")
public interface HealthcheckService {

    @RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
    Result<String> getHealthcheck();

}
