package pl.estrix.warehouse.controller.scan;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Ellipse;
import pl.estrix.warehouse.Configurable;
import pl.estrix.warehouse.ViewParam;
import pl.estrix.warehouse.controller.AbstractController;
import pl.estrix.warehouse.controller.RootController;
import pl.estrix.warehouse.controller.about.AboutController;
import pl.estrix.warehouse.dto.ReturnDialogDto;

import java.net.URL;
import java.util.ResourceBundle;

public class ScanController extends AbstractController implements Initializable, Configurable {

    private static ScanController instance = null;

    @FXML
    private TextField eanField;
    @FXML
    private Label name;

    @FXML
    private Label code;
    @FXML
    private Label company;
    @FXML
    private Label counter;
    @FXML
    private Label form;
    @FXML
    private Button counterButton;

    @FXML
    private Ellipse eanFieldSematphor;

    @FXML
    private Hyperlink eanFieldSematphorLink;

    private StringBuilder eanCode = new StringBuilder();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);

        counterButton.focusedProperty().addListener((obs, oldVal, newVal) -> Platform.runLater(() -> eanField.requestFocus()));
        eanField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                eanFieldSematphorLink.setVisible(false);
            } else {
                eanFieldSematphorLink.setVisible(true);
            }
        });

        eanField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode().equals(KeyCode.ESCAPE)
            ) {
                eanCode.delete(0, eanCode.length());
                Platform.runLater(() -> {
                    name.setText("");
                    code.setText("");
                    counter.setText("");
                    eanField.clear();
                });
                return;
            }
            if (event.getCode().equals(KeyCode.ENTER)
                    || event.getCode().equals(KeyCode.UNDEFINED)
            ) {
                eanCode.delete(0, eanCode.length());
                eanCode.append(eanField.getText());
                Platform.runLater( ()->{
                    code.setText(leftPadZeros(eanCode.toString(), 13));
                });

                eanField.clear();
                return;
            }
        });

    }

    public static String leftPadZeros(String str, int num) {
        return String.format("%1$" + num + "s", str).replace(' ', '0');
    }

    public static synchronized ScanController getInstance() {
        if (instance == null) {
            instance = new ScanController();
        }
        return instance;
    }

    private static void setInstance(ScanController instance) {
        ScanController.instance = instance;
    }

    private ScanController(){
    }

    @FXML
    public void onUpdateDatabase() {
        ReturnDialogDto<Integer> okClicked = RootController.getInstance().openScanMultiplierDialog();
        if (okClicked.getStatus() == 1) {
            Platform.runLater( ()->{
                counter.setText(okClicked.get().toString());
            });
        }

        Platform.runLater(() -> eanField.requestFocus());
    }

    @Override
    public void setParams(ViewParam params) {
        Platform.runLater( ()->{
            form.setText(params.getCode());
            company.setText(params.getName());
        });
    }

    public void onFieldFocusAction(ActionEvent event) {
    }
}
