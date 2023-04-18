package testCases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import christofides.Node;
import christofides.Edge;
import christofides.Graph;
import christofides.MST;

class MstTest {

	@Test
	void testMST1() {
		ArrayList<Node> list = new ArrayList<>();
        list.add(new Node("1", 0.0, 0.0));
        list.add(new Node("2", 1.0, 0.0));
        list.add(new Node("3", 2.0, 0.0));
        list.add(new Node("4", 3.0, 0.0));
        list.add(new Node("5", 4.0, 0.0));
        double mstWeight = new MST(list.size(), list).computeDistance();
        assertEquals(mstWeight, 4.0);
	}
	
	@Test
	void testMST2() {
		ArrayList<Node> list = new ArrayList<>();
		list = new ArrayList<>();
        list.add(new Node("1", 0.0, 0.0));
        list.add(new Node("2", 1.0, 1.0));
        list.add(new Node("3", 0.0, 2.0));
        list.add(new Node("4", -1.0, 1.0));
        list.add(new Node("5", 0.0, 1.0));
        double mstWeight = new MST(list.size(), list).computeDistance();
        assertEquals(mstWeight, 4.0);
	}
	
	@Test
	void testMST3() {
		ArrayList<Node> list = new ArrayList<>();
		list = new ArrayList<>();
		list.add(new Node("1", 0.0, 0.0));
        list.add(new Node("2", 1.0, 1.0));
        list.add(new Node("3", 2.0, 0.0));
        list.add(new Node("4", 3.0, 1.0));
        list.add(new Node("5", 4.0, 0.0));
        list.add(new Node("6", 5.0, 1.0));
        double mstWeight = new MST(list.size(), list).computeDistance();
        assertEquals(mstWeight, 10.0);
	}
	
	@Test
	void testMST4() {
		ArrayList<Node> list = new ArrayList<>();
		list = new ArrayList<>();
		list.add(new Node("1", 2.0, 7.0));
        list.add(new Node("2", 9.0, 9.0));
        list.add(new Node("3", 3.0, 3.0));
        list.add(new Node("4", 7.0, 8.0));
        list.add(new Node("5", 4.0, 5.0));
        list.add(new Node("6", 5.0, 4.0));
        list.add(new Node("7", 6.0, 4.0));
        double mstWeight = new MST(list.size(), list).computeDistance();
        assertEquals(mstWeight, 18.0);
        HashSet<String> edgeSet = new HashSet<>(Arrays.asList("1#5", "5#6", "6#7", "6#3", "7#4", "4#2"));
        Graph mst = new MST(list.size(), list).buildGraph();
        for(Edge edge: mst.allEdges()) edgeSet.remove(edge.getUEdge().getID()+"#"+edge.getVEdge().getID());
        assertTrue(edgeSet.size() == 0);
	}

}
