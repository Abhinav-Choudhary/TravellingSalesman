//package christofides.optimizations;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import christofides.Edge;
//import christofides.EulerTour;
//import christofides.Graph;
//import christofides.Node;
//
//public class TwoOpt {
//	Graph tour;
//	ArrayList<Node> nodes;
//	int numVertices = 0;
//	
//	public TwoOpt(Graph tour) {
//		this.tour = tour;
//		this.nodes = this.tour.getNodes();
//		this.numVertices = this.tour.getVertexCount();
//	}
//	
//	public Graph buildTour(EulerTour eulerTour) {
//		String[] originalTour = Helper.getStringTourFromVertexTour(eulerTour);
//		HashMap<String, Double> allDistances = Helper.calculateAllDistances(numVertices, nodes);
//		HashMap<String, Node> mappedNodes = Helper.mapNodes(originalTour, numVertices, nodes);
//		String[] modifiedTour = twoOptSolver(originalTour, allDistances, mappedNodes);
//		ArrayList<Edge> newTour = Helper.generateArrayListOfEdgesFromTour(modifiedTour, mappedNodes);
//		return new Graph(newTour);
//	}
//	//Implementation of 2-opt optimization
//	public String[] twoOptSolver(String[] tour, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//        int numCities = tour.length;
//        String[] currentTour = tour.clone();
//        int swaps = 1;
//        double bestDistance = this.tour.totalWeight();
//        while (swaps != 0) {
//        	swaps = 0;
//        	for (int i=1; i < numCities - 2; i++) {
//        		for (int j=i+1; j < numCities - 1; j++) {
//        			String nodeI = currentTour[i];
//        			String nodeIMinusOne = currentTour[i-1];
//        			String nodeJ = currentTour[j];
//        			String nodeJPlusOne = currentTour[j+1];
//        			Double dist1 = getDistances(nodeI, nodeIMinusOne, allDistances, nodesMap) + getDistances(nodeJPlusOne, nodeJ, allDistances, nodesMap);
//        			Double dist2 = getDistances(nodeI, nodeJPlusOne, allDistances, nodesMap) + getDistances(nodeIMinusOne, nodeJ, allDistances, nodesMap);
//        			if (dist1 >= dist2) {
//        				String[] newTour = swap(currentTour, numCities, i, j);
//        				ArrayList<Edge> newTourEdges = Helper.generateArrayListOfEdgesFromTour(newTour, nodesMap);
//        				Graph newGraph = new Graph(newTourEdges);
//        				double newTotalDistance = newGraph.totalWeight();
//        				if (newTotalDistance < bestDistance) {
//        					currentTour = newTour;
//        					bestDistance = newTotalDistance;
//        					swaps++;
//        				}
//        			}
//        		}
//        	}
//        }
//        return currentTour;
//    }
//	//swap two nodes
//	private String[] swap(String[] currentTour, int length, int i, int j) {
//		String[] newTour = new String[length];
//		for (int c = 0; c < i; c++) {
//			newTour[c] = currentTour[c];
//		}
//		int dec = 0;
//		for (int c = i; c <= j; c++) {
//			newTour[c] = currentTour[j - dec];
//			dec++;
//		}
//		for (int c=j+1; c < length; c++) {
//			newTour[c] = currentTour[c];
//		}
//		return newTour;
//	}
//	//get distance between two nodes with their respective String IDs
//	private Double getDistances(String n1, String n2, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//		Node node1 = nodesMap.get(n1);
//		Node node2 = nodesMap.get(n2);
//		return Helper.getDistanceBetweenNodes(node1, node2, allDistances);
//	}
//}
