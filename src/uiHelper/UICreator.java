package uiHelper;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uiHelper.DataNormalizer;
import uiHelper.dataProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import christofides.*;
import christofides.optimizations.*;

public class UICreator extends Application {

    //private Graph graph; // Graph object to be displayed in UI

    public UICreator() {
        // Default constructor with no arguments
    }
//
//    public UICreator(Graph graph) {
//        this.graph = graph;
//    }

//    public static void main(String[] args) {
//        launch(args);
//    }
    
    public void initialSetup() { 
    	try {
    		Scanner reader = new Scanner(new File("src\\uiHelper\\info6205.spring2023.teamproject.csv"));
            TSPRoute.shared.build(reader);
            reader.close();
    	} catch(FileNotFoundException e) {
    		System.out.println("File not found. "+e.getLocalizedMessage());
    	}
    }

    @Override
    public void start(Stage stage) {
        initialSetup();
//        Graph graph = TSPRoute.shared.buildChristofides();
        Graph mst = TSPRoute.shared.computeMST();
        Text mstText = new Text("Minimum Spanning Tree: " + String.format("%,.2f", mst.totalWeight()*1000) + " m");
    	Graph graph = TSPRoute.shared.computeEulerTour(mst);
        Text algoText = new Text("Christofides Algorithm: " + String.format("%,.2f", graph.totalWeight()*1000) + " m");
        Text optText = new Text();
        
//        DataNormalizer dm = new DataNormalizer();      
//        Graph normalizedGraph = dm.normalizeData(graph);
        
      for(int i = 0; i < graph.getVertexCount(); i++) {
    	System.out.print("Lat = " + graph.getNodes().get(i).getX()+ " ");
    	System.out.println("Lon = " + graph.getNodes().get(i).getY());
    }
        
        // Find the min and max values for latitude and longitude
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = Double.MIN_VALUE;
        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = Double.MIN_VALUE;

        for (christofides.Node node : graph.getNodes()) {
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
            double radius = 3; // Radius of the circle representing the data point
            Circle circle = new Circle(scaledX, scaledY, radius);
            circle.setFill(Color.BLUE); // Set fill color of the circle

            // Extract last six characters of ID
            String id = node.getID();
            String label = id.substring(Math.max(0, id.length() - 6));

            // Create label for the circle
            javafx.scene.control.Label labelNode = new javafx.scene.control.Label(label);
            labelNode.setLayoutX(scaledX - 10); // Adjust x-coordinate for label placement
            labelNode.setLayoutY(scaledY - 10); // Adjust y-coordinate for label placement
            root.getChildren().addAll(circle, labelNode); // Add circle and label to the group
        }

        for (Edge edge : graph.allEdges()) {
            christofides.Node node1 = edge.u; // Assuming 'u' represents the start node of the edge
            christofides.Node node2 = (edge.v); // Assuming 'v' represents the end node of the edge
            // Scale the coordinates of the start and end nodes based on the center point and scaling factors
            double startX = (node1.x - centerX) * scaleX + 400;
            double startY = (node1.y - centerY) * scaleY + 300;
            double endX = (node2.x - centerX) * scaleX + 400;
            double endY = (node2.y - centerY) * scaleY + 300;

            // Create a line to represent the edge
            Line line = new Line(startX, startY, endX, endY);
            root.getChildren().add(line); // Add line to the group
        }

        // Create a Pane to hold the Group
        Pane pane = new Pane();
        pane.getChildren().add(root); // Add group to the pane

        // Create buttons for different optimization algorithms
        Button btnGA = new Button("Genetic Algorithm");
        Button btnSA = new Button("Simulated Annealing");
        Button btn2Opt = new Button("Two-Opt Optimization");
        Button btn3Opt = new Button("Three-Opt Optimization");

        // Set the layout of the buttons
        btnGA.setLayoutX(20);
        btnGA.setLayoutY(20);
        btnSA.setLayoutX(20);
        btnSA.setLayoutY(60);
        btn2Opt.setLayoutX(20);
        btn2Opt.setLayoutY(100);
        btn3Opt.setLayoutX(20);
        btn3Opt.setLayoutY(140);

        
        
        // Set the event handlers for the buttons
        btnGA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Call the Genetic Algorithm
            	Graph gaTour = TSPRoute.shared.buildGeneticAlgoTour();
            	optText.setText("Genetic Optimization: " + String.format("%,.2f", gaTour.totalWeight()*1000) + " m");
            	System.out.print("Done");
            	
            	root.getChildren().removeIf(node -> node instanceof Line);
                
                // Plot lines for each edge in the graph
            	for (Edge edge : gaTour.allEdges()) {
                    christofides.Node node1 = edge.u; // Assuming 'u' represents the start node of the edge
                    christofides.Node node2 = (edge.v); // Assuming 'v' represents the end node of the edge
                    // Scale the coordinates of the start and end nodes based on the center point and scaling factors
                    double startX = (node1.x - centerX) * scaleX + 400;
                    double startY = (node1.y - centerY) * scaleY + 300;
                    double endX = (node2.x - centerX) * scaleX + 400;
                    double endY = (node2.y - centerY) * scaleY + 300;
                    // Create a line to represent the edge
                    Line line = new Line(startX, startY, endX, endY);
                    root.getChildren().add(line); // Add line to the group
                }
            }

        });

        btnSA.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Call the SimulatedAnnealing Algorithm
            	 Graph saTour = TSPRoute.shared.runSimulatedAnnealing();
             	optText.setText("Simulated Annealing Optimization: " + String.format("%,.2f", saTour.totalWeight()*1000) + " m");
            	 
            	 root.getChildren().removeIf(node -> node instanceof Line);
            	 
                 // Plot lines for each edge in the graph
             	for (Edge edge : saTour.allEdges()) {
                     christofides.Node node1 = edge.u; // Assuming 'u' represents the start node of the edge
                     christofides.Node node2 = (edge.v); // Assuming 'v' represents the end node of the edge
                     // Scale the coordinates of the start and end nodes based on the center point and scaling factors
                     double startX = (node1.x - centerX) * scaleX + 400;
                     double startY = (node1.y - centerY) * scaleY + 300;
                     double endX = (node2.x - centerX) * scaleX + 400;
                     double endY = (node2.y - centerY) * scaleY + 300;
                     // Create a line to represent the edge
                     Line line = new Line(startX, startY, endX, endY);
                     root.getChildren().add(line); // Add line to the group
                 }

            }
        });

        btn2Opt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Call 2Opt Optimization 
                System.out.println("Ant Colony Optimization: ");
                Graph twoOptTour = TSPRoute.shared.run2OPT();
                optText.setText("2-Opt Optimization: " + String.format("%,.2f", twoOptTour.totalWeight()*1000) + " m");
                
                root.getChildren().removeIf(node -> node instanceof Line);
                
             	for (Edge edge : twoOptTour.allEdges()) {
                    christofides.Node node1 = edge.u; // Assuming 'u' represents the start node of the edge
                    christofides.Node node2 = (edge.v); // Assuming 'v' represents the end node of the edge
                    // Scale the coordinates of the start and end nodes based on the center point and scaling factors
                    double startX = (node1.x - centerX) * scaleX + 400;
                    double startY = (node1.y - centerY) * scaleY + 300;
                    double endX = (node2.x - centerX) * scaleX + 400;
                    double endY = (node2.y - centerY) * scaleY + 300;
                    // Create a line to represent the edge
                    Line line = new Line(startX, startY, endX, endY);
                    root.getChildren().add(line); // Add line to the group
                }
                
            }
        });

        btn3Opt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // Call the 3Opt optimization Algorithm
            	Graph threeOptTour = TSPRoute.shared.run3OPT();
            	optText.setText("3-Opt Optimization: " + String.format("%,.2f", threeOptTour.totalWeight()*1000) + " m");
            	
            	root.getChildren().removeIf(node -> node instanceof Line);
             	for (Edge edge : threeOptTour.allEdges()) {
                    christofides.Node node1 = edge.u; // Assuming 'u' represents the start node of the edge
                    christofides.Node node2 = (edge.v); // Assuming 'v' represents the end node of the edge
                    // Scale the coordinates of the start and end nodes based on the center point and scaling factors
                    double startX = (node1.x - centerX) * scaleX + 400;
                    double startY = (node1.y - centerY) * scaleY + 300;
                    double endX = (node2.x - centerX) * scaleX + 400;
                    double endY = (node2.y - centerY) * scaleY + 300;
                    // Create a line to represent the edge
                    Line line = new Line(startX, startY, endX, endY);
                    root.getChildren().add(line); // Add line to the group
                }
            }
        });
        
        //set layout of text
        mstText.setLayoutX(20);
        mstText.setLayoutY(700);
        algoText.setLayoutX(20);
        algoText.setLayoutY(720);
        optText.setLayoutX(20);
        optText.setLayoutY(740);

        // Add buttons and text to the pane
        pane.getChildren().addAll(btnGA, btnSA, btn2Opt, btn3Opt, mstText, algoText, optText);

        // Create a scene and set it on the stage
        Scene scene = new Scene(pane, 1000, 800);
        stage.setScene(scene);
        stage.setTitle("TSP Solver");
        stage.show();
    }
}