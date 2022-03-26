package pl.estrix.warehouse.controller.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import pl.estrix.warehouse.ViewParam;
import pl.estrix.warehouse.controller.AbstractController;
import pl.estrix.warehouse.controller.RootController;
//import pl.estrix.core.PopupSensor;
//import pl.estrix.core.PopupSensorListener;
//import pl.estrix.core.ScreenResolutionSensor;
//import pl.estrix.dao.model.PagingCriteria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import pl.estrix.warehouse.Configurable;
import pl.estrix.warehouse.controller.AbstractController;

public class MainController extends AbstractController implements Initializable, Configurable {

    private static MainController instance = null;

    @FXML
    private Text example;

    @FXML
    private AnchorPane listAnchorPane;


//    private PopupSensorListener popupSensorListener;
//    private PopupSensor popupSensor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
//        SessionManager.returnToMainTail.setValue(true);
    }

    public void onClearFindField(Integer id, Boolean isShort) {
    }

    public static synchronized MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public MainController() {
    }

    private static void setInstance(MainController instance) {
        MainController.instance = instance;
    }

    @Override
    public void setParams(ViewParam params) {
//        onClearFindField(params.getId(),params.getShort() );
    }

	public void onSettingsAction(ActionEvent actionEvent) {
        RootController.getInstance().openAbout();
	}

    public void onDeliveryAction(ActionEvent event) {
        RootController.getInstance().openCompanyList(new ViewParam("Wydania"));
    }

    public void onReceiptAction(ActionEvent event) {
        RootController.getInstance().openCompanyList(new ViewParam("Przyjęcia"));
    }
}
