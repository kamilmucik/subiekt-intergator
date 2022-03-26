package pl.estrix.warehouse.controller.list;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CompanyModel {

    // url to user image
    private final StringProperty name = new SimpleStringProperty();
    // user network id, to identify author of comment
    private final StringProperty networkId = new SimpleStringProperty();
    // content of comment
    private final StringProperty content = new SimpleStringProperty();

    public CompanyModel() {
    }

    public CompanyModel(String content, String name, String networkId) {
        this.content.set(content);
        this.name.set(name);
        this.networkId.set(networkId);
    }

    public StringProperty contentProperty() {
        return content;
    }

    public String getContent() {
        return content.get();
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String author) {
        this.name.set(author);
    }

    public StringProperty networkIdProperty() {
        return networkId;
    }

    public String getNetworkId() {
        return networkId.get();
    }

    public void setNetworkId(String networkId) {
        this.networkId.set(networkId);
    }

    @Override
    public String toString() {
        return networkId.get()+": "+name.get();
    }
}
