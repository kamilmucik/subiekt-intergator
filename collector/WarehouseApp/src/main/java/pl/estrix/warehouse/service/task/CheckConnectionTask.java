package pl.estrix.warehouse.service.task;


import pl.estrix.warehouse.service.RestService;
import pl.estrix.warehouse.util.SessionManager;

import java.util.Date;
import java.util.TimerTask;

public class CheckConnectionTask extends TimerTask {

    @Override
    public void run() {
        SessionManager.errorStatus.setValue(!RestService.getInstance().ping());
        SessionManager.errorMessage.setValue("Brak połączenia z serwerem! " + new Date());
    }
}
