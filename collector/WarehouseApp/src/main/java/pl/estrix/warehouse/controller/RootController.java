package pl.estrix.warehouse.controller;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import pl.estrix.warehouse.Configurable;
import pl.estrix.warehouse.controller.about.AboutController;
import pl.estrix.warehouse.MainApp;
import pl.estrix.warehouse.ViewParam;
import pl.estrix.warehouse.controller.main.MainController;
import pl.estrix.warehouse.controller.scan.ScanController;
import pl.estrix.warehouse.core.NotificationSensor;
import pl.estrix.warehouse.dto.ReturnDialogDto;
import pl.estrix.warehouse.util.SessionManager;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class RootController implements Initializable {

    private static final Logger LOG = Logger.getLogger(RootController.class.getName());

    private static RootController instance = null;
    @FXML
    public AnchorPane toastformContentPane;
    @FXML
    public AnchorPane toastformPane;
    @FXML
    public ScrollPane toastformScroll;
    @FXML
    private Text noteInfo;
    @FXML
    private BorderPane bp;
    //    @FXML
//    private HBox hbox;
    private Double hboxPrefHeight = 0.0d;

    @FXML
    private AnchorPane rootPaneAchore;
    @FXML
    private AnchorPane rootLayout;
    @FXML
    private AnchorPane topMenuPane;
    @FXML
    private AnchorPane errorPane;
    @FXML
    private TextArea errorMessage;
    @FXML
    private AnchorPane blurPane;
    @FXML
    private AnchorPane blurPaneBk;
    public  Boolean blurPaneOpened;
    private Double blurPaneHeight;

    private MainApp mainApp;


    @FXML
    private Label appTitle;

    @FXML
    private ScrollPane rootLayoutPane;

    @FXML
    private RadioButton checkboxList;
    @FXML
    private RadioButton checkboxSearch;
    @FXML
    private Hyperlink returnMainTile;

    private AnchorPane ads;

    private NotificationSensor notificationSensor;

    private BooleanProperty blurOnProperty = new SimpleBooleanProperty(false);
    private BooleanProperty blurBkOnProperty = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
        SessionManager.getInstance().restoreSession();
        SessionManager.getInstance().loadProperties("/filter.properties");

        blurPaneBk.visibleProperty().bindBidirectional(blurBkOnProperty);
        blurPane.visibleProperty().bindBidirectional(blurOnProperty);

        errorPane.visibleProperty().bindBidirectional(SessionManager.errorStatus);
        errorMessage.visibleProperty().bindBidirectional(SessionManager.errorStatus);
        errorMessage.textProperty().bindBidirectional(SessionManager.errorMessage);

        openMainTiles();
    }

    public RootController() {
        blurPaneOpened = false;

    }

    public static synchronized RootController getInstance() {
        if (instance == null) {
            instance = new RootController();
        }
        return instance;
    }

    private static void setInstance(RootController instance) {
        RootController.instance = instance;
    }

    void setCenter(String fxmlPath, ViewParam param) {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass == MainController.class) {
                return MainController.getInstance() ;
            } else if (controllerClass == AboutController.class) {
                return AboutController.getInstance() ;
            } else if (controllerClass == ScanController.class) {
                return ScanController.getInstance() ;
            } else {
                try {
                    return controllerClass.newInstance();
                } catch (Exception exc) {
                    LOG.severe(exc.getMessage());
                    return null;
                }
            }
        });
        try {
            AnchorPane node = (AnchorPane)loader.load();
            if (loader.getController() instanceof Configurable){
                ((Configurable) loader.getController()).setParams(param);
            }
            if (loader.getController() instanceof AbstractController){
//                ((AbstractController) loader.getController()).rootAnchorePane.setStyle("-fx-background-color: red");
//                double w = ((AbstractController) loader.getController()).rootAnchorePane.getWidth();
//                double w = rootLayoutPane.getWidth();
//                ((AbstractController) loader.getController()).setSize(w,node.getHeight());
//                ((AbstractController) loader.getController()).setSize(mainApp.getSceneWidth() ,mainApp.getSceneHeight());
            }
            node.setStyle("/style.css");

            rootLayoutPane.setContent(node);
        } catch (Exception e) {
//            LOG.severe(e.getMessage());
        }
    }

    public void setAppMain(MainApp appMain) {
        this.mainApp = appMain;
    }

    @FXML
    public void openAbout() {setCenter("/fxml/About.fxml", null);}

    @FXML
    public void openScan(ViewParam viewParam) {setCenter("/fxml/Scan.fxml", viewParam);}

    @FXML
    public void openMain() {setCenter("/fxml/Main.fxml", new ViewParam(0)); }
    @FXML
    public void openMain(Integer id) {setCenter("/fxml/Main.fxml", new ViewParam(id)); }

    @FXML
    public void openMainTiles() {setCenter("/fxml/Main.fxml", new ViewParam()); }

    @FXML
    public void openCompanyList() {setCenter("/fxml/CompanyList.fxml", new ViewParam()); }

    @FXML
    public void openCompanyList(ViewParam viewParam) {setCenter("/fxml/CompanyList.fxml", viewParam); }

    @FXML
    public void onReturnMainTile(ActionEvent actionEvent) {
        this.openMainTiles();
    }

    public void notificationOn(final String msg) {
        blurPaneOn(msg);
//        notificationSensor.getMessages().add(msg);
    }

    public void notificationOff(final String msg) {
        notificationSensor.getMessages().remove(msg);
    }

    private static Object requestObject;
    public void blurPaneOn(Object object, Object clazz) {
        blurPaneOn(object);
        requestObject = clazz;

    }
    public void blurPaneOn(Object object) {
        blurOnProperty.setValue(true);
        blurBkOnProperty.setValue(true);

        if (object instanceof String) {
            toastformContentPane.getChildren().clear();
            Label textField = new Label();

            textField.setText(object.toString());
            textField.setWrapText(true);
            toastformScroll.setContent(textField);
        } else if (object instanceof Node){
            toastformScroll.setContent((Node)object);
        }
//        bp.setBottom(null);
    }

    @FXML
    public void onBlurOff() {
        blurPaneOpened = false;
        blurOnProperty.setValue(false);
        blurBkOnProperty.setValue(false);
    }

    @FXML
    public void onBlurCancelOff() {
//        if (requestObject != null){
//            popupSensor.fireChangeEvent(requestObject, false);
//        }
//        popupSensor.fireChangeEvent(false);
        onBlurOff();
        requestObject = null;
    }

    @FXML
    public void onBlurAgreeOff() {
        onBlurOff();
        requestObject = null;
    }

    public ReturnDialogDto<Integer> openScanMultiplierDialog() {
//        apToastBackground.setVisible(true);
        blurBkOnProperty.setValue(true);

        ReturnDialogDto<Integer> tmp = mainApp.showScanMultiplerDialog();
//        apToastBackground.setVisible(false);
        blurBkOnProperty.setValue(false);

        return tmp;
    }

    public void onCloseErrorMessage(ActionEvent event) {
        SessionManager.errorStatus.setValue(false);
    }
}
