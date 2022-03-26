package pl.estrix.warehouse.controller.list;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;

import java.util.function.Consumer;

public class CompanyListCell extends ListCell<CompanyModel> {

    private final SlidingListTile slidingTile;
    private final ListTile tile;
    private final Service service;

    private CompanyModel currentItem;
    private final ChangeListener<String> commentChangeListener;

    public CompanyListCell(Service service, Consumer<CompanyModel> consumerLeft, Consumer<CompanyModel> consumerRight) {
        this.service = service;

        tile = new ListTile();
//        avatar = new Avatar();
//        Services.get(DisplayService.class)
//                .ifPresent(d -> {
//                    if (d.isTablet()) {
//                        avatar.getStyleClass().add("tablet");
//                    }
//                });
//        tile.setPrimaryGraphic(avatar);
        slidingTile = new SlidingListTile(tile, true, "Delete", "Edit");
//        slidingTile = new SlidingListTile(tile, true, MaterialDesignIcon.DELETE.text, MaterialDesignIcon.EDIT.text);

        slidingTile.swipedLeftProperty().addListener((obs, ov, nv) -> {
            if (nv && consumerRight != null) {
                consumerRight.accept(currentItem);
            }
            slidingTile.resetTilePosition();
        });

        // delete mode, swipping from left to right
        slidingTile.swipedRightProperty().addListener((obs, ov, nv) -> {
            if (nv && consumerLeft != null) {
                consumerLeft.accept(currentItem);
            }
            slidingTile.resetTilePosition();
        });

        commentChangeListener = (obs, ov, nv) -> {
            // make sure text updates are run on the JavaFX thread, because the change can come from
            // an sse event from gluon cloud when another application updates a currentItem

            Platform.runLater(() -> {
                if (currentItem != null) {
//                    tile.textProperty().setAll(currentItem.getAuthor());
                    tile.textProperty().addAll(getContent(currentItem));
                } else {
                    tile.textProperty().clear();
                }
            });
        };
    }

    /**
     * Add a ListTile control to not empty cells
     * @param item the comment on the cell
     * @param empty
     */
    @Override
    protected void updateItem(CompanyModel item, boolean empty) {
        super.updateItem(item, empty);
        updateCurrentItem(item);
        if (!empty && item != null) {
//            avatar.setImage(Service.getUserImage(item.getImageUrl()));
//            if (service.getUser() != null) {
//                slidingTile.allowedProperty().set(service.getUser().getNetworkId().equals(item.getNetworkId()));
//            }
//            tile.textProperty().setAll(item.getAuthor());
            String[] content = getContent(item);
            if (content != null) {
                tile.textProperty().addAll(content);
            }
            setGraphic(slidingTile);
            setPadding(Insets.EMPTY);
        } else {
            setGraphic(null);
        }
    }

    private void updateCurrentItem(CompanyModel comment) {
        if (currentItem == null || !currentItem.equals(comment)) {
            if (currentItem != null) {
//                currentItem.authorProperty().removeListener(commentChangeListener);
                currentItem.contentProperty().removeListener(commentChangeListener);
            }

            currentItem = comment;

            if (currentItem != null) {
//                currentItem.authorProperty().addListener(commentChangeListener);
                currentItem.contentProperty().addListener(commentChangeListener);
            }
        }
    }

    private String[] getContent(CompanyModel comment) {
        if (comment == null || comment.getContent() == null || comment.getContent().isEmpty()) {
            return new String[] {""};
        }

        final String[] lines = comment.getContent().split("\\n");
        if (lines.length > 2) {
            lines[1]=lines[1].concat(" ...");
        }
        return lines;
    }

    /**
     * Boolean property with sliding status of cell
     * @return
     */
    public BooleanProperty slidingProperty() {
        return slidingTile.slidingProperty();
    }

}
