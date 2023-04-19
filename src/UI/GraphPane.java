package UI;
//import christofides.Edge;
//import javafx.scene.Group;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Circle;
//import javafx.scene.shape.Line;
//
////package uiHelper;
////
////import christofides.Edge;
////import christofides.Graph;
////import javafx.scene.layout.Pane;
////import javafx.scene.shape.Line;
////
////public class GraphPane extends Pane {
////
////    // ...
////
////    // Function to update the graph with new edges
////    public void updateGraph(Graph graph) {
////        // Clear the pane by removing all previously plotted edges
////        getChildren().clear();
////
////        // Plot the new edges provided in the Graph object
////        for (Edge edge : graph.allEdges()) {
////            double startX = edge.u.x;
////            doube startY = edge.v.y;
////            int endX = edge.getEndX();
////            int endY = edge.getEndY();
////
////            // Create a JavaFX Line object to represent the edge
////            Line line = new Line(startX, startY, endX, endY);
////
////            // Add the line to the pane
////            getChildren().add(line);
////        }
////    }
////}
////
//
//
//
//
//
//        // Find the min and max values for latitude and longitude
//        double minLatitude = Double.MAX_VALUE;
//        double maxLatitude = Double.MIN_VALUE;
//        double minLongitude = Double.MAX_VALUE;
//        double maxLongitude = Double.MIN_VALUE;
//
//        for (christofides.Node node : graph.getNodes()) {
//            double latitude = node.x;
//            double longitude = node.y;
//            minLatitude = Math.min(minLatitude, latitude);
//            maxLatitude = Math.max(maxLatitude, latitude);
//            minLongitude = Math.min(minLongitude, longitude);
//            maxLongitude = Math.max(maxLongitude, longitude);
//        }
//
//        // Calculate the center point and scaling factors
//        double centerX = (minLatitude + maxLatitude) / 2;
//        double centerY = (minLongitude + maxLongitude) / 2;
//        double scaleX = 800 / (maxLatitude - minLatitude);
//        double scaleY = 600 / (maxLongitude - minLongitude);
//
//        // Create a group to hold the circles representing the data points
//        Group root = new Group();
//
//        // Add circles for each normalized latitude and longitude values
//        for (christofides.Node node : graph.getNodes()) {
//            double latitude = node.x;
//            double longitude = node.y;
//            // Scale the coordinates based on the center point and scaling factors
//            double scaledX = (latitude - centerX) * scaleX + 400;
//            double scaledY = (longitude - centerY) * scaleY + 300;
//            double radius = 5; // Radius of the circle representing the data point
//            Circle circle = new Circle(scaledX, scaledY, radius);
//            circle.setFill(Color.BLUE); // Set fill color of the circle
//
//            // Extract last six characters of ID
//            String id = node.getID();
//            String label = id.substring(Math.max(0, id.length() - 6));
//
//            // Create label for the circle
//            javafx.scene.control.Label labelNode = new javafx.scene.control.Label(label);
//            labelNode.setLayoutX(scaledX - 10); // Adjust x-coordinate for label placement
//            labelNode.setLayoutY(scaledY - 10); // Adjust y-coordinate for label placement
//            root.getChildren().addAll(circle, labelNode); // Add circle and label to the group
//        }
//
//        for (Edge edge : graph.allEdges()) {
//            christofides.Node node1 = edge.u; // Assuming 'u' represents the start node of the edge
//            christofides.Node node2 = (edge.v); // Assuming 'v' represents the end node of the edge
//            // Scale the coordinates of the start and end nodes based on the center point and scaling factors
//            double startX = (node1.x - centerX) * scaleX + 400;
//            double startY = (node1.y - centerY) * scaleY + 300;
//            double endX = (node2.x - centerX) * scaleX + 400;
//            double endY = (node2.y - centerY) * scaleY + 300;
//
//            // Create a line to represent the edge
//            Line line = new Line(startX, startY, endX, endY);
//            root.getChildren().add(line); // Add line to the group
//        }
