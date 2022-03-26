package pl.estrix.warehouse.controller.list;

import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import pl.estrix.warehouse.Configurable;
import pl.estrix.warehouse.ViewParam;
import pl.estrix.warehouse.controller.AbstractController;
import pl.estrix.warehouse.controller.RootController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * https://github.com/gluonhq/gluon-samples/blob/master/comments2.0/src/main/java/com/gluonhq/comments20/views/CommentsPresenter.java
 */
public class CompanyListController extends AbstractController implements Initializable,Configurable {

    private static CompanyListController instance = null;

    private Service service = new Service();

    private ViewParam params;

//    @FXML
//    private Text example;

    @FXML
    private ListView<CompanyModel> companiesList;

    private final BooleanProperty sliding = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setInstance(this);
//        SessionManager.returnToMainTail.setValue(true);

//        companiesList.setCellFactory(cell -> {
//            final CompanyListCell commentListCell = new CompanyListCell(
//                    service,
//                    // left button: delete comment, only author's comment can delete it
//                    c -> {
////                        if (service.getUser().getNetworkId().equals(c.getNetworkId())) {
//////                            showDialog(c);
////                            example.setText("showDialog: " + c.getNetworkId());
////                        }
//                    },
//                    // right button: edit comment, everybody can view it, only author can edit it
//                    c -> {
//                        service.activeCommentProperty().set(c);
////                        example.setText("EDITION_VIEW: " + c.getNetworkId());
//                    });
//
//            // notify view that cell is sliding
//            commentListCell.slidingProperty().addListener((obs, ov, nv) -> sliding.set(nv));
//
//            return commentListCell;
//        });

        companiesList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                params.setName(newSelection.getNetworkId());
                RootController.getInstance().openScan(params);
            }
            if (oldSelection != null) {
//                oldSelection.switchedOnProperty().setValue(false);
            }
        });

        final Label label = new Label("SIGN_IN_MESSAGE");
        companiesList.setPlaceholder(label);
//        companiesList.disableProperty().bind(service.userProperty().isNull());
        companiesList.setItems(service.commentsProperty());

        service.retrieveComments();
    }

    public void onClearFindField(Integer id, Boolean isShort) {
    }

    public static synchronized CompanyListController getInstance() {
        if (instance == null) {
            instance = new CompanyListController();
        }
        return instance;
    }

    public CompanyListController() {
    }

    private static void setInstance(CompanyListController instance) {
        CompanyListController.instance = instance;
    }

    @Override
    public void setParams(ViewParam params) {
        this.params = params;
//        example.setText("code: " + params.getCode());
    }

}