package pl.estrix.warehouse.controller.list;

import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ListTile extends BorderPane {
    private final ObjectProperty<Node> a = new SimpleObjectProperty();
    private final ObservableList<String> b = FXCollections.observableArrayList();
    private final ObjectProperty<Node> c = new SimpleObjectProperty();
    private final BooleanProperty d = new SimpleBooleanProperty();
    private final Label e = new Label();
    private final VBox f = new VBox();
    private String g = null;

    public ListTile() {
        this.getStyleClass().setAll(new String[]{"list-tile"});
        this.f.getStyleClass().setAll(new String[]{"text-box"});
        this.e.getStyleClass().setAll(new String[]{"primary-text"});
        this.e.setMaxHeight(1.7976931348623157E308D);
        this.a.addListener((var1, var2, var3) -> {
            this.a(var3, true);
        });
        this.c.addListener((var1, var2, var3) -> {
            this.a(var3, false);
        });
        this.b.addListener((ListChangeListener<? super String>) (var1) -> {
            ListTile var4 = this;
            int var2;
            if (this.b.size() == this.f.getChildren().size()) {
                for(var2 = 0; var2 < var4.b.size(); ++var2) {
                    ((Label)var4.f.getChildren().get(var2)).setText((String)var4.b.get(var2));
                }
            } else {
                this.f.getChildren().clear();
                this.e.setText(this.b.isEmpty() ? "" : (String)this.b.get(0));
                this.f.getChildren().add(this.e);
                VBox.setVgrow(this.e, Priority.ALWAYS);

                for(var2 = 1; var2 <= 2; ++var2) {
                    if (var4.b.size() > var2) {
                        Label var3;
                        (var3 = new Label((String)var4.b.get(var2))).setMaxHeight(1.7976931348623157E308D);
                        var3.setAlignment(Pos.TOP_LEFT);
                        var3.getStyleClass().add(var2 == 1 ? "secondary-text" : "tertiary-text");
                        var4.f.getChildren().add(var3);
                        VBox.setVgrow(var3, Priority.ALWAYS);
                    }
                }

                String var10000;
                switch(var4.b.size()) {
                    case 0:
                    case 1:
                        var10000 = "single-line-tile";
                        break;
                    case 2:
                        var10000 = "dual-line-tile";
                        break;
                    default:
                        var10000 = "triple-line-tile";
                }

                String var5 = var10000;
                if (var4.g != null && !var5.equals(var4.g)) {
                    var4.getStyleClass().remove(var4.g);
                    var4.getStyleClass().add(var5);
                    var4.g = var5;
                }
            }

            var4.a();
        });
        this.d.addListener((var1, var2, var3) -> {
            this.a();
        });
        this.setCenter(this.f);
        BorderPane.setAlignment(this.f, Pos.CENTER_LEFT);
//        if (com.gluonhq.impl.charm.a.d.b.a()) {
//            this.setCache(true);
//        }

    }

    public final ObjectProperty<Node> primaryGraphicProperty() {
        return this.a;
    }

    public final Node getPrimaryGraphic() {
        return (Node)this.a.get();
    }

    public final void setPrimaryGraphic(Node graphic) {
        this.a.set(graphic);
    }

    public final ObservableList<String> textProperty() {
        return this.b;
    }

    public void setTextLine(int i, String v) {
        while(this.b.size() < i) {
            this.b.add("");
        }

        if (this.b.size() == i) {
            this.b.add(v);
        } else {
            this.b.set(i, v);
        }
    }

    public final ObjectProperty<Node> secondaryGraphicProperty() {
        return this.c;
    }

    public final Node getSecondaryGraphic() {
        return (Node)this.c.get();
    }

    public final void setSecondaryGraphic(Node graphic) {
        this.c.set(graphic);
    }

    public final BooleanProperty wrapTextProperty() {
        return this.d;
    }

    public final boolean isWrapText() {
        return this.d.get();
    }

    public final void setWrapText(boolean value) {
        this.d.set(value);
    }

    private void a(Node var1, boolean var2) {
        Optional.ofNullable(var2 ? this.getLeft() : this.getRight()).ifPresent((var1x) -> {
            this.getChildren().remove(var1x);
        });
        Optional.ofNullable(var1).ifPresent((var2x) -> {
            var2x.getStyleClass().add(var2 ? "primary-graphic" : "secondary-graphic");
            BorderPane.setAlignment(var2x, Pos.CENTER);
            if (var2) {
                this.setLeft(var2x);
            } else {
                this.setRight(var2x);
            }
        });
    }

    private void a() {
        //TODO: poprawic listę IndexOutOfBoundsException
        try {
            for (int var1 = 0; var1 < this.b.size(); ++var1) {
                ((Label) this.f.getChildren().get(var1)).setWrapText(this.isWrapText());
            }
        }catch (IndexOutOfBoundsException e){
            System.out.println("Blad");
        }

    }
}
