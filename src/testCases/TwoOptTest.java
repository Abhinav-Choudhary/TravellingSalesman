package testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import christofidesAlgo.EulerTour;
import christofidesAlgo.Graph;
import christofidesAlgo.Node;
import christofidesAlgo.Edge;
import christofidesAlgo.optimizations.TwoOpt;

class TwoOptTest {
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		TestConfig.shared.shouldComputeManhattan = true;
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		TestConfig.shared.shouldComputeManhattan = false;
	}

	@Test
	void testTwoOpt1() {
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Node("1", 2.0, 7.0), new Node("2", 9.0, 9.0)));
		edges.add(new Edge(new Node("3", 3.0, 3.0), new Node("4", 7.0, 8.0)));
		edges.add(new Edge(new Node("5", 4.0, 5.0), new Node("6", 5.0, 4.0)));
		edges.add(new Edge(new Node("7", 6.0, 4.0), new Node("8", 8.0, 2.0)));
		
		Graph graph = new Graph(edges);
		EulerTour eulerTour = new EulerTour(graph);
		Graph twoOptGraph = new TwoOpt(graph).buildTour(eulerTour);
		assertEquals(twoOptGraph.totalWeight(), 18.0);
	}
	
	@Test
	void testTwoOpt2() {
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Node("1", 1.0, 1.0), new Node("2", 2.0, 3.0)));
		edges.add(new Edge(new Node("3", 4.0, 3.0), new Node("4", 5.0, 2.0)));
		edges.add(new Edge(new Node("5", 6.0, 1.0), new Node("6", 5.0, 0.0)));
		edges.add(new Edge(new Node("7", 3.0, 0.0), new Node("8", 2.0, 1.5)));
		
		Graph graph = new Graph(edges);
		EulerTour eulerTour = new EulerTour(graph);
		Graph twoOptGraph = new TwoOpt(graph).buildTour(eulerTour);
		assertEquals(twoOptGraph.totalWeight(), 6.0);
	}
	
	@Test
	void testTwoOpt3() {
		ArrayList<Edge> edges = new ArrayList<>();
		edges.add(new Edge(new Node("1", 0.0, 0.0), new Node("2", 1.0, 2.0)));
		edges.add(new Edge(new Node("3", 3.0, 1.0), new Node("4", 5.0, 2.0)));
		edges.add(new Edge(new Node("5", 6.0, 4.0), new Node("6", 4.0, 6.0)));
		edges.add(new Edge(new Node("7", 2.0, 5.0), new Node("8", 1.5, 3.5)));
		edges.add(new Edge(new Node("9", 2.5, 2.5), new Node("10", 4.5, 3.5)));
		
		Graph graph = new Graph(edges);
		EulerTour eulerTour = new EulerTour(graph);
		Graph twoOptGraph = new TwoOpt(graph).buildTour(eulerTour);
		assertEquals(twoOptGraph.totalWeight(), 6.0);
	}

}
