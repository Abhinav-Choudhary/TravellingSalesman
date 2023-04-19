package UI;

import java.util.ArrayList;
import java.util.Random;

import christofidesAlgo.*;

public class randomGraphGenerator {
    private static final int NUM_NODES = 15; // Number of nodes in the graph
    private static final int MAX_EDGE_WEIGHT = 10; // Maximum edge weight

    public static Graph generateRandomGraph() {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();
        Random random = new Random();

        // Generate nodes
        for (int i = 1; i <= NUM_NODES; i++) {
            String nodeId = "Node" + i; // Generate node ID
            Double x = random.nextDouble() * 100; // Generate random x-coordinate
            Double y = random.nextDouble() * 100; // Generate random y-coordinate
            Node node = new Node(nodeId, x, y); // Create node with ID and coordinates
            nodes.add(node); // Add node to nodes list
        }

        // Generate edges
        for (int i = 0; i < NUM_NODES; i++) {
            Node u = nodes.get(i); // Pick a random starting node
            for (int j = i + 1; j < NUM_NODES; j++) {
                Node v = nodes.get(j); // Pick a random ending node
                Edge edge = new Edge(u, v); // Create edge with random weight
                edges.add(edge); // Add edge to edges list
            }
        }

        Graph graph = new Graph(edges); // Create graph with generated edges
        return graph;
    }
    
    public static Graph generateRandomGraphWithLoops() {
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Node> nodes = new ArrayList<>();
        Random random = new Random();

        // Generate nodes
        for (int i = 1; i <= NUM_NODES; i++) {
            String nodeId = "Node" + i; // Generate node ID
            Double x = random.nextDouble() * 100; // Generate random x-coordinate
            Double y = random.nextDouble() * 100; // Generate random y-coordinate
            Node node = new Node(nodeId, x, y); // Create node with ID and coordinates
            nodes.add(node); // Add node to nodes list
        }

        // Generate edges
        for (int i = 0; i < NUM_NODES; i++) {
            Node u = nodes.get(i); // Pick a random starting node
            Node v1 = nodes.get((i + 1) % NUM_NODES); // Pick the next node in the list as the first neighbor
            Node v2 = nodes.get((i + NUM_NODES - 1) % NUM_NODES); // Pick the previous node in the list as the second neighbor
            Edge edge1 = new Edge(u, v1); // Create first edge with v1 as the endpoint
            Edge edge2 = new Edge(u, v2); // Create second edge with v2 as the endpoint
            edges.add(edge1); // Add first edge to edges list
            edges.add(edge2); // Add second edge to edges list
        }

        Graph graph = new Graph(edges); // Create graph with generated edges
        return graph;
    }
    
    
}