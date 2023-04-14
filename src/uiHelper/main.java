package uiHelper;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import uiHelper.DataNormalizer;
import uiHelper.dataProvider;

import java.util.ArrayList;

public class main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Provide the path of the CSV file
        String csvFile = "C:\\Users\\kcsar\\TravellingSalesman\\src\\uiHelper\\data.txt";

        // Call the dataProvider class to parse the data
        dataProvider dataProvider = new dataProvider(csvFile);
        ArrayList<dataProvider.Node> nodes = dataProvider.parseData();

        // Call the DataNormalizer class to normalize the data
        DataNormalizer dataNormalizer = new DataNormalizer(nodes);
        ArrayList<dataProvider.Node> normalizedNodes = dataNormalizer.normalizeData();

        // Print normalized latitude and longitude values
        System.out.println("Normalized Latitude and Longitude Values:");
        int k = 1;
        for (dataProvider.Node node : normalizedNodes) {
            System.out.println(k + " Latitude: " + node.getLatitude() + ", Longitude: " + node.getLongitude());
            k++;
        }

        // Find the min and max values for latitude and longitude
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;
        for (dataProvider.Node node : normalizedNodes) {
            double latitude = node.getLatitude();
            double longitude = node.getLongitude();
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
        for (dataProvider.Node node : normalizedNodes) {
            double latitude = node.getLatitude();
            double longitude = node.getLongitude();
            // Scale the coordinates based on the center point and scaling factors
            double scaledX = (latitude - centerX) * scaleX + 400;
            double scaledY = (longitude - centerY) * scaleY + 300;
            double radius = 5; // Radius of the circle representing the data point
            Circle circle = new Circle(scaledX, scaledY, radius);
            circle.setFill(Color.BLUE); // Set fill color of the circle
            root.getChildren().add(circle); // Add circle to the group
        }

        // Create a scene and set it to the stage
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
