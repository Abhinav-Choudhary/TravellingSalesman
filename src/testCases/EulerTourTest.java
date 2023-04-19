package testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import christofidesAlgo.Node;
import christofidesAlgo.Edge;
import christofidesAlgo.Graph;
import christofidesAlgo.EulerTour;

class EulerTourTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TestConfig.shared.shouldComputeManhattan = true;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		TestConfig.shared.shouldComputeManhattan = false;
	}

	@Test
	void testEulerTour() {
        Map<String, Node> nodeMap = new HashMap<>();
        nodeMap.put("1", new Node("1", 2.0, 7.0));
        nodeMap.put("2", new Node("2", 9.0, 9.0));
        nodeMap.put("3", new Node("3", 3.0, 3.0));
        nodeMap.put("4", new Node("4", 7.0, 8.0));
        nodeMap.put("5", new Node("5", 4.0, 5.0));
        nodeMap.put("6", new Node("6", 5.0, 4.0));
        nodeMap.put("7", new Node("7", 6.0, 4.0));

        ArrayList<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(nodeMap.get("1"), nodeMap.get("2")));
        edgeList.add(new Edge(nodeMap.get("1"), nodeMap.get("5")));
        edgeList.add(new Edge(nodeMap.get("4"), nodeMap.get("2")));
        edgeList.add(new Edge(nodeMap.get("6"), nodeMap.get("3")));
        edgeList.add(new Edge(nodeMap.get("3"), nodeMap.get("6")));
        edgeList.add(new Edge(nodeMap.get("7"), nodeMap.get("4")));
        edgeList.add(new Edge(nodeMap.get("5"), nodeMap.get("6")));
        edgeList.add(new Edge(nodeMap.get("6"), nodeMap.get("7")));

        Graph g = new Graph(edgeList);
        EulerTour eulerTour = new EulerTour(g);
        ArrayList<Node> vertexTour = eulerTour.buildVertexTour();
        ArrayList<String> expectedTour = new ArrayList<>(Arrays.asList("1","5","6","3","7","4","2"));
        Boolean tourEqual = true;
        for(int i=0; i<expectedTour.size(); i++) {
            if(!vertexTour.get(i).getID().equals(expectedTour.get(i))) {
                tourEqual = false;
                break;
            }
        }
        assertTrue(tourEqual);
    }
	
	@Test
	void testEulerTour2() {
        Map<String, Node> nodeMap = new HashMap<>();
        nodeMap.put("1", new Node("1", 0.0, 0.0));
        nodeMap.put("2", new Node("2", 1.0, 1.0));
        nodeMap.put("3", new Node("3", 2.0, 0.0));
        nodeMap.put("4", new Node("4", 3.0, 1.0));
        nodeMap.put("5", new Node("5", 4.0, 0.0));
        nodeMap.put("6", new Node("6", 5.0, 1.0));

        ArrayList<Edge> edgeList = new ArrayList<>();
        edgeList.add(new Edge(nodeMap.get("1"), nodeMap.get("2")));
        edgeList.add(new Edge(nodeMap.get("1"), nodeMap.get("3")));
        edgeList.add(new Edge(nodeMap.get("2"), nodeMap.get("3")));
        edgeList.add(new Edge(nodeMap.get("3"), nodeMap.get("4")));
        edgeList.add(new Edge(nodeMap.get("3"), nodeMap.get("5")));
        edgeList.add(new Edge(nodeMap.get("4"), nodeMap.get("6")));
        edgeList.add(new Edge(nodeMap.get("5"), nodeMap.get("6")));

        Graph g = new Graph(edgeList);
        EulerTour eulerTour = new EulerTour(g);
        ArrayList<Node> vertexTour = eulerTour.buildVertexTour();
        ArrayList<String> expectedTour = new ArrayList<>(Arrays.asList("1","3","5","6","4","2"));
        Boolean tourEqual = true;
        for(int i=0; i<expectedTour.size(); i++) {
            if(!vertexTour.get(i).getID().equals(expectedTour.get(i))) {
                tourEqual = false;
                break;
            }
        }
        assertTrue(tourEqual);
    }

}
