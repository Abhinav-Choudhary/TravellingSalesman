package testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import christofidesAlgo.Edge;
import christofidesAlgo.EulerTour;
import christofidesAlgo.Graph;
import christofidesAlgo.Node;
import christofidesAlgo.optimizations.ThreeOpt;

class ThreeOptTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TestConfig.shared.shouldComputeManhattan = true;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		TestConfig.shared.shouldComputeManhattan = false;
	}

	@Test
	void testThreeOpt1() {
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Node("1", 1.0, 1.0), new Node("2", 2.0, 3.0)));
		edges.add(new Edge(new Node("3", 4.0, 3.0), new Node("4", 5.0, 2.0)));
		edges.add(new Edge(new Node("5", 6.0, 1.0), new Node("6", 5.0, 0.0)));
		edges.add(new Edge(new Node("7", 3.0, 0.0), new Node("8", 2.0, 1.5)));
		
		Graph graph = new Graph(edges);
		EulerTour eulerTour = new EulerTour(graph);
        Graph threeOptGraph = new ThreeOpt(graph).buildTour(eulerTour);
		assertEquals(threeOptGraph.totalWeight(), 6.0);
	}
	
	@Test
	void testThreeOpt2() {
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Node("1", 0.0, 0.0), new Node("2", 1.0, 2.0)));
		edges.add(new Edge(new Node("3", 3.0, 1.0), new Node("4", 5.0, 2.0)));
		edges.add(new Edge(new Node("5", 6.0, 4.0), new Node("6", 4.0, 6.0)));
		edges.add(new Edge(new Node("7", 2.0, 5.0), new Node("8", 1.5, 3.5)));
		edges.add(new Edge(new Node("9", 2.5, 2.5), new Node("10", 4.5, 3.5)));
		
		Graph graph = new Graph(edges);
		EulerTour eulerTour = new EulerTour(graph);
        Graph threeOptGraph = new ThreeOpt(graph).buildTour(eulerTour);
		assertEquals(threeOptGraph.totalWeight(), 6.0);
	}
	
	@Test
	void testThreeOpt3() {
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Node("1", 1.0, 1.0), new Node("2", 2.0, 3.0)));
		edges.add(new Edge(new Node("3", 4.0, 3.0), new Node("4", 5.0, 2.0)));
		edges.add(new Edge(new Node("5", 6.0, 1.0), new Node("6", 5.0, 0.0)));
		edges.add(new Edge(new Node("7", 3.0, 0.0), new Node("8", 2.0, 1.5)));
		
		Graph graph = new Graph(edges);
		EulerTour eulerTour = new EulerTour(graph);
        Graph threeOptGraph = new ThreeOpt(graph).buildTour(eulerTour);
		assertEquals(threeOptGraph.totalWeight(), 6.0);
	}

}
