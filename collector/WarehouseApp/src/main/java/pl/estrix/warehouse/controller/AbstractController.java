package pl.estrix.warehouse.controller;


import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

abstract public class AbstractController {

    @FXML
    protected AnchorPane rootAnchorePane;


    public AbstractController() {
    }

    public void setSize(Double width, Double height) {
        rootAnchorePane.setPrefWidth(width);
        rootAnchorePane.setPrefHeight(height);
    }


}