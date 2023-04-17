package uiHelper;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import uiHelper.DataNormalizer;
import uiHelper.dataProvider;
import helper.*;
import helper.genetic.GASolver;
import helper.model.CityManager;
import helper.model.Tour;
import helper.pso.Swarm;
import java.util.ArrayList;
import christofides.*;

public class uiCreator extends Application {
    
    private Graph graph; // Graph object to be displayed in UI

    public uiCreator(Graph graph) {
        this.graph = graph;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Find the min and max values for latitude and longitude
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        
        for (christofides.Node  node : graph.getNodes()) {
            double latitude = node.x;
            double longitude = node.y;
            minLatitude = Math.min(minLatitude, latitude);
            maxLatitude = Math.max(maxLatitude, latitude);
            minLongitude = Math.min(minLongitude, longitude);
            maxLongitude = Math.max(maxLongitude, longitude);
        }

        // Calculate the center point and scaling factors
        double centerX = (minLatitude + maxLatitude) / 2;
        double centerY = (minLongitude + maxLongitude) / 2;
        double scaleX = 800 / (maxLatitude - minLatitude);
        double scaleY = 600 / (maxLongitude - minLongitude);

        // Create a group to hold the circles representing the data points
        Group root = new Group();

        // Add circles for each normalized latitude and longitude values
        for (christofides.Node node : graph.getNodes()) {
            double latitude = node.x;
            double longitude = node.y;
            // Scale the coordinates based on the center point and scaling factors
            double scaledX = (latitude - centerX) * scaleX + 400;
            double scaledY = (longitude - centerY) * scaleY + 300;
            double radius = 2; // Radius of the circle representing the data point
            Circle circle = new Circle(scaledX, scaledY, radius);
            circle.setFill(Color.BLUE); // Set fill color of the circle
            root.getChildren().add(circle); // Add circle to the group
        }
        
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
