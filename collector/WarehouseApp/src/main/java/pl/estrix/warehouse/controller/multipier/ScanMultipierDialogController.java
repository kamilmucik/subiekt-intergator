package pl.estrix.warehouse.controller.multipier;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.estrix.warehouse.dto.ReturnDialogDto;

public class ScanMultipierDialogController {

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button0;
    @FXML
    private Button buttonOk;
    @FXML
    private Label resultLabel;
    @FXML
    private Button buttonClean;
    private Stage dialogStage;
    private boolean okClicked = false;

    @FXML
    private Text header;
    @FXML
    private AnchorPane headPane;

    StringBuilder concanetValue = new StringBuilder("1");

    private StringProperty value = new SimpleStringProperty(concanetValue.toString());

    @FXML
    private void initialize() {
        resultLabel.textProperty().bind(value);

        buttonClean.setOnMouseClicked(event -> {
            concanetValue.delete(0,concanetValue.length());
            value.setValue(concanetValue.toString());
        });
        button1.setOnMouseClicked(event -> {
            concanetValue.append("1");
            value.setValue(concanetValue.toString());
        });
        button2.setOnMouseClicked(event -> {
            concanetValue.append("2");
            value.setValue(concanetValue.toString());
        });
        button3.setOnMouseClicked(event -> {
            concanetValue.append("3");
            value.setValue(concanetValue.toString());
        });
        button4.setOnMouseClicked(event -> {
            concanetValue.append("4");
            value.setValue(concanetValue.toString());
        });
        button5.setOnMouseClicked(event -> {
            concanetValue.append("5");
            value.setValue(concanetValue.toString());
        });
        button6.setOnMouseClicked(event -> {
            concanetValue.append("6");
            value.setValue(concanetValue.toString());
        });
        button7.setOnMouseClicked(event -> {
            concanetValue.append("7");
            value.setValue(concanetValue.toString());
        });
        button8.setOnMouseClicked(event -> {
            concanetValue.append("8");
            value.setValue(concanetValue.toString());
        });
        button9.setOnMouseClicked(event -> {
            concanetValue.append("9");
            value.setValue(concanetValue.toString());
        });
        button0.setOnMouseClicked(event -> {
            concanetValue.append("0");
            value.setValue(concanetValue.toString());
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public ReturnDialogDto<Integer> isOkClicked() {
        ReturnDialogDto<Integer> result = new ReturnDialogDto<>();
        result.set(Integer.parseInt(concanetValue.toString().isEmpty()?"1":concanetValue.toString()));
        result.setStatus(1);
        return result;
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOk() {
        okClicked = true;
        dialogStage.close();
    }
}
