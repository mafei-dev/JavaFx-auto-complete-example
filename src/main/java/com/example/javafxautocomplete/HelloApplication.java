package com.example.javafxautocomplete;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
       /* FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();*/
        HBox box = new HBox();
//        Panel panel = new Panel("This is the title");
        box.getStyleClass().add("panel-primary");                            //(2)
        BorderPane content = new BorderPane();
        content.setPadding(new Insets(20));
        Button button = new Button("Hello BootstrapFX");
        button.getStyleClass().setAll("btn", "btn-danger");                     //(2)
        content.setCenter(button);
        TextField nextTextField = new TextField();
        content.setBottom(nextTextField);
        AutoComplete<MyTextNode> myAutoAutoComplete = new AutoComplete<>(
                new ItemChangeListener<MyTextNode>() {
                    @Override
                    public void onChange(MyTextNode selectedItem) {
                        System.out.println("SelectedItem : " + selectedItem);
                    }

                    @Override
                    public List<MyTextNode> loadItems(String text) {

                        /*try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }*/
                        List<MyTextNode> myList = new ArrayList<>();
                        myList.add(new MyTextNode("abc" + LocalDateTime.now().toLocalTime()));
                        myList.add(new MyTextNode("abca"));
                        myList.add(new MyTextNode("abcd"));
                        myList.add(new MyTextNode("abcdef"));
                        return myList;
                    }
                }, nextTextField);
//        myAutoAutoComplete.getStyleClass().setAll("text-primary");                     //(2)
        myAutoAutoComplete.setMinWidth(600);
        content.setTop(myAutoAutoComplete);
        box.getChildren().add(content);


        Scene scene = new Scene(box);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        stage.setTitle("BootstrapFX");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}