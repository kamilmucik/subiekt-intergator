package pl.estrix.warehouse;

import com.gluonhq.charm.down.Services;
import com.gluonhq.charm.down.common.PlatformFactory;
import com.gluonhq.charm.down.plugins.EstrixService;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.estrix.warehouse.controller.RootController;
import pl.estrix.warehouse.controller.multipier.ScanMultipierDialogController;
import pl.estrix.warehouse.dto.ReturnDialogDto;
import pl.estrix.warehouse.service.task.CheckConnectionTask;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

public class MainApp extends Application{

    private Stage primaryStage;

    private Scene scene;

    private Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    private Double sceneWidth = visualBounds.getWidth();
    private Double sceneHeight = visualBounds.getHeight();

    private static Timer timer = new Timer();
    private static TimerTask checkConnectionTask;

    public void initialize() {
    }

    @Override
    public void init() {
//        System.setOut(new PrintStream(new DevNull()));
    }

    private class DevNull extends OutputStream {
        @Override
        public void write(int b) throws IOException {
        }
    }


    @Override
    public void start(Stage stage) {
        initialize();
        this.primaryStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/RootLayout.fxml"));
            Parent rootPane = loader.load();

            Services.get(EstrixService.class).ifPresent(esService -> {
                if("desktop".equals(esService.checkSystem())){
                    sceneWidth= 380.0;
                    sceneHeight= 480.0;
                }
            });

            scene = new Scene(rootPane, sceneWidth, sceneHeight);

            scene.getStylesheets().add("/style.css");
            primaryStage.setScene(scene);


            RootController controller = loader.getController();
            controller.setAppMain(this);

            primaryStage.show();

            primaryStage.setOnCloseRequest(we -> exit());

            checkConnectionTask = new CheckConnectionTask();
            timer.schedule(checkConnectionTask, 1000, 10_000); // co 10 sekund

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void exit() {
            timer.cancel();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ReturnDialogDto<Integer> showScanMultiplerDialog() {
        try {
            // Load the fxml file and create a new stage for the popup
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/ScanMultiplierDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);

            dialogStage.resizableProperty().setValue(Boolean.FALSE);
            dialogStage.setResizable(false);
//            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
//            dialogStage.setY(primaryStage.getY() + 60);
//            dialogStage.setX( primaryStage.getX() + ((primaryStage.getWidth() /2)-(page.getPrefWidth()/2)) );
//            dialogStage.setX( primaryStage.getX()  );

            Scene scene = null;



//            scene = new Scene(page, page.getPrefWidth(),page.getPrefHeight());
//            if(PlatformFactory.getPlatform().getName().equals(PlatformFactory.ANDROID)){
//                Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
//                scene = new Scene(page, visualBounds.getWidth(), page.getPrefHeight());
//            } else {
                scene = new Scene(page, sceneWidth,page.getPrefHeight());
//            }

            dialogStage.setScene(scene);

            // Set the person into the controller
            ScanMultipierDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        } catch (IOException e) {
            ReturnDialogDto<Integer> result = new ReturnDialogDto<>();
            result.set(-1);
            result.setStatus(-1);
            result.setMessage(e.getMessage());
            return result;
        }
    }

    public Double getSceneWidth() {
        return sceneWidth;
    }

    public Double getSceneHeight() {
        return sceneHeight;
    }
}
