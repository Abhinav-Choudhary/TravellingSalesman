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
//public class ThreeOpt {
//	Graph tour;
//	ArrayList<Node> nodes;
//	int numVertices = 0;
//	
//	public ThreeOpt(Graph tour) {
//		this.tour = tour;
//		this.nodes = this.tour.getNodes();
//		this.numVertices = this.tour.getVertexCount();
//	}
//	
//	public Graph buildTour(EulerTour eulerTour) {
//		String[] originalTour = Helper.getStringTourFromVertexTour(eulerTour);
//		HashMap<String, Double> allDistances = Helper.calculateAllDistances(numVertices, nodes);
//		HashMap<String, Node> mappedNodes = Helper.mapNodes(originalTour, numVertices, nodes);
////		String[] modifiedTour = threeOptSolver(originalTour, allDistances, mappedNodes);
//		String[] modifiedTour = threeOptAlternateSolver(originalTour, allDistances, mappedNodes);
//		ArrayList<Edge> newTour = Helper.generateArrayListOfEdgesFromTour(modifiedTour, mappedNodes);
//		return new Graph(newTour);
//	}
//	
//	// perform 3-opt optimization on the given tour
//    public String[] threeOptSolver(String[] tour, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//        int n = tour.length;
//        boolean improved = true;
//        String[] currentTour = tour.clone();
//        while (improved) {
//            improved = false;
//            for (int i = 0; i < n - 2; i++) {
//                for (int j = i + 2; j < n; j++) {
//                    for (int k = j + 2; k < n + (i == 0 ? 1 : 0); k++) {
//                        if (k == n && i == 0) {
//                            continue;
//                        }
//                        Double gain = calculateGain(currentTour, allDistances, nodesMap, n, i, j, k);
//                        if (gain < 0) {
//                        	currentTour = applyExchange(currentTour, n, i, j, k);
//                            improved = true;
//                        }
//                    }
//                }
//            }
//        }
//        return currentTour;
//    }
//    
// // calculate the gain of the 3-opt exchange
//    private Double calculateGain(String[] tour, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap, int n, int i, int j, int k) {
//        Node a = nodesMap.get(tour[i]);
//        Node b = nodesMap.get(tour[j]);
//        Node c = nodesMap.get(tour[k%n]);
//        Node d = nodesMap.get(tour[(k + 1) % n]);
//        Node e = nodesMap.get(tour[(i + 1) % n]);
//        Node f = nodesMap.get(tour[(j + 1) % n]);
//        Double gain = -Helper.getDistanceBetweenNodes(a, e, allDistances) - Helper.getDistanceBetweenNodes(b, f, allDistances) - Helper.getDistanceBetweenNodes(c, d, allDistances)
//        				+ Helper.getDistanceBetweenNodes(a, b, allDistances) + Helper.getDistanceBetweenNodes(b, c, allDistances) + Helper.getDistanceBetweenNodes(c, e, allDistances)
//        				+ Helper.getDistanceBetweenNodes(d, f, allDistances) - Helper.getDistanceBetweenNodes(e, f, allDistances);
//        return gain;
//    }
//    
// // apply the 3-opt exchange to the tour
//    private static String[] applyExchange(String[] tour, int n, int i, int j, int k) {
//        String[] newTour = new String[n];
//        for (int p = 0; p <= i; p++) {
//            newTour[p] = tour[p];
//        }
//        for (int p = j; p > i; p--) {
//        	newTour[p] = tour[p];
//        }
//        for (int p = k; p > j; p--) {
//            newTour[p] = tour[p % n];
//        }
//        for (int p = k + 1; p < n; p++) {
//        	newTour[p] = tour[p];
//        }
//        return newTour;
//    }
//    
////--------------------- Different Approach to 3 opt -----------------------------------------------
//    public String[] threeOptAlternateSolver(String[] tour, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//        int improve = 0;
//        int size = tour.length;
//        String[] currentTour = tour.clone();
//
//        while (improve < 5) {
//            Double best_distance = calculateTourDistance(currentTour, size, allDistances, nodesMap);
//            for (int i = 0; i < size - 2; i++) {
//                for (int j = i + 1; j < size - 1; j++) {
//                    for (int k = j + 1; k < size; k++) {
//                        String[] new_tour = improveTour(currentTour, size, i, j, k);
//                        Double new_distance = calculateTourDistance(new_tour, size, allDistances, nodesMap);
//                        if (new_distance < best_distance) {
//                        	currentTour = new_tour;
//                            best_distance = new_distance;
//                            improve = 0;
//                        }
//                    }
//                }
//            }
//            improve++;
//        }
//        
//        return currentTour;
//    }
//    
//    private static String[] improveTour(String[] tour, int size, int i, int j, int k) {
//        String[] new_tour = new String[size];
//        for (int x = 0; x <= i - 1; x++) {
//            new_tour[x] = tour[x];
//        }
//        int dec = 0;
//        for (int x = i; x <= j; x++) {
//            new_tour[x] = tour[j - dec];
//            dec++;
//        }
//        dec = 0;
//        for (int x = j + 1; x <= k; x++) {
//            new_tour[x] = tour[k - dec];
//            dec++;
//        }
//        for (int x = k + 1; x < size; x++) {
//            new_tour[x] = tour[x];
//        }
//        return new_tour;
//    }
//
//    private Double calculateTourDistance(String[] tour, int size, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//        Double distance = 0.0;
//        for (int i = 0; i < size - 1; i++) {
//        	Node node1 = nodesMap.get(tour[i]);
//        	Node node2 = nodesMap.get(tour[i+1]);
//            distance += Helper.getDistanceBetweenNodes(node1, node2, allDistances);
//        }
//        Node node1 = nodesMap.get(tour[size - 1]);
//    	Node node2 = nodesMap.get(tour[0]);
//        distance += Helper.getDistanceBetweenNodes(node1, node2, allDistances);
//        return distance;
//    }
//}
