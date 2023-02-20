package com.example.mathfunctions;

import com.FXChemiEngine.draw.BufferImageDrawingObject;
import com.FXChemiEngine.engine.Scene;
import com.FXChemiEngine.engine.ShapeObject;
import com.FXChemiEngine.util.Color;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.CycleMethod;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public ImageView image;
    @FXML
    public TextField textField;
    public Group mainScene;
    private Scene scene;
    private Page page;
    private SubScene subScene;
    private double newMouseX;
    private double newMouseY;
    private Function function;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group rubicGroup = new Group();
        subScene = new SubScene(rubicGroup, 800, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
        mainScene.getChildren().add(subScene);
        image = new ImageView();
        rubicGroup.getChildren().add(image);

        BufferImageDrawingObject drawingObject = new BufferImageDrawingObject();
        scene = new Scene(800, 600, Color.TRANSPARENT, drawingObject);
        scene.enableDevelopMode();

        ShapeObject ob = new ShapeObject("Function", 1);
        function = new Function(Color.BLUE);
        //function.setExpression("y=x");
        //function.setExpression("y^2=-x^2+5000");
        //function.setExpression("y=x^2");
        function.setDx(1);
        function.setMinX(scene.MinX);
        function.setMaxX(scene.MaxX);
        //function.parseExpression();
        //function.gen();

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            function.setExpression(newValue);
        });


        ob.add(function);
        scene.add(ob);

        subScene.setOnScroll(scrollEvent -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = scrollEvent.getDeltaY()/20;
            //System.out.println(f.getZoomFactor());
            function.setZoomFactor(function.getZoomFactor()+zoomFactor);
            function.setDx(1/function.getZoomFactor());
            /*if(function.getZoomFactor() % 10 >= 0)
                page.setZoomFactor(function.getZoomFactor());*/
        });

        subScene.setOnMousePressed(event -> {
                newMouseX = event.getSceneX();
                newMouseY = event.getSceneY();
        });
        subScene.setOnMouseDragged(event -> {
            float dx = (float) (newMouseX - event.getSceneX());
            float dy = (float) (newMouseY - event.getSceneY());

            image.setTranslateX(-dx);
            image.setTranslateY(-dy);

        });


        page = new Page();
        scene.add(page);
        Timeline t = new Timeline(new KeyFrame(Duration.millis(100), event -> game()));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void game(){
        if(function.parseExpression())
            function.gen();
        page.update(scene.MaxX, scene.MaxY);
        scene.repaint();
        Image img = scene.getImage();
        this.image.setFitWidth(img.getWidth());
        this.image.setFitHeight(img.getHeight());
        this.image.setImage(img);
    }
}