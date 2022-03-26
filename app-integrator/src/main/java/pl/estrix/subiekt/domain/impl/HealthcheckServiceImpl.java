package pl.estrix.subiekt.domain.impl;

import org.springframework.web.bind.annotation.RestController;
import pl.estrix.subiekt.commons.Result;
import pl.estrix.subiekt.commons.SuccessResult;
import pl.estrix.subiekt.domain.HealthcheckService;

@RestController
public class HealthcheckServiceImpl implements HealthcheckService {

    @Override
    public Result<String> getHealthcheck() {
        return new SuccessResult("ok");
    }
}
