package pl.estrix.warehouse.controller.about;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.estrix.warehouse.controller.AbstractController;
import pl.estrix.warehouse.controller.RootController;
import pl.estrix.warehouse.dto.ReturnDialogDto;
import pl.estrix.warehouse.util.SessionManager;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class AboutController extends AbstractController implements Initializable {

    private static AboutController instance = null;

    @FXML
    private Label build;
    @FXML
    private Label version;
    @FXML
    private TextField serverIp;
    @FXML
    private TextField serverPort;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
//        SessionManager.returnToMainTail.setValue(true);
        build.setText(SessionManager.properties.get("estrix.application.biuld-time"));
        version.setText(SessionManager.properties.get("estrix.application.version"));

        if (SessionManager.customsProperties.containsKey("serverAddress")){
            serverIp.setText(SessionManager.customsProperties.get("serverAddress"));
        }
        if (SessionManager.customsProperties.containsKey("serverPort")){
            serverPort.setText(SessionManager.customsProperties.get("serverPort"));
        }

    }

    public static synchronized AboutController getInstance() {
        if (instance == null) {
            instance = new AboutController();
        }
        return instance;
    }

    private static void setInstance(AboutController instance) {
        AboutController.instance = instance;
    }

    private AboutController(){
    }

    @FXML
    public void onUpdateDatabase() {
//        System.out.println("onUpdateDatabase");
//        RootController.getInstance().notificationOn("onUpdateDatabase");
//        ReturnDialogDto<Integer> okClicked = RootController.getInstance().openScanMultiplierDialog();
//        if (okClicked.getStatus() == 1) {
//            RootController.getInstance().notificationOn("onUpdateDatabase: " + okClicked.get());
////            multipler = okClicked.get();
////            multiplerButton.setText("x"+okClicked.get());
//        }

    }

    public void onSaveAction(ActionEvent event) {
        System.out.println("onSaveAction");
        SessionManager.customsProperties.put("serverAddress",serverIp.getText());
        SessionManager.customsProperties.put("serverPort",serverPort.getText());
        SessionManager.getInstance().saveSession();
    }
}