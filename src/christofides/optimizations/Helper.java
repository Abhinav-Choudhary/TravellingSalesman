//package christofides.optimizations;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import christofides.Edge;
//import christofides.EulerTour;
//import christofides.Node;
//
//public class Helper {
//
//	public static HashMap<String, Double> calculateAllDistances(int numVertices, ArrayList<Node> nodes) {
//		HashMap<String, Double> distancesMap = new HashMap<>();
//		for (int i=0; i < numVertices; i++) {
//			Node initialNode = nodes.get(i);
//			for (int j=i+1; j < numVertices; j++) {
//				Node currentNode = nodes.get(i);
//				String combinedID = initialNode.getID() + "--to--" + currentNode.getID();
//				if (!distancesMap.containsKey(combinedID)) {
//					Edge newEdge = new Edge(initialNode, currentNode);
//					distancesMap.put(combinedID, newEdge.getEdgeWeight());
//				}
//			}
//		}
//		return distancesMap;
//	}
//	
//	public static HashMap<String, Double> calculateDistances(String[] initialRoute, HashMap<String, Double> allDistances, HashMap<String, Node> nodesMap) {
//    	int n = initialRoute.length;
//    	HashMap<String, Double> newDistances = new HashMap<>();
//        for (int i = 0; i < n; i++) {
//            for (int j = i + 1; j < n; j++) {
//            	Node node1 = nodesMap.get(initialRoute[i]);
//            	Node node2 = nodesMap.get(initialRoute[j]);
//            	String combinedID = node1.getID() + "--to--" + node2.getID();
//                if (!allDistances.containsKey(combinedID)) {
//					Edge newEdge = new Edge(node1, node2);
//					newDistances.put(combinedID, newEdge.getEdgeWeight());
//				}
//            }
//        }
//        return newDistances;
//    }
//	
//	public static Double getDistanceBetweenNodes(Node node1, Node node2, HashMap<String, Double> allDistances) {
//		Double distance = 0.0;
//		
//		String combinedID = node1.getID() + "--to--" + node2.getID();
//		String reverseCombinedID = node2.getID() + "--to--" + node1.getID();
//		
//		if (allDistances.containsKey(combinedID)) {
//			distance = allDistances.get(combinedID);
//		} else if (allDistances.containsKey(reverseCombinedID)) {
//			distance = allDistances.get(reverseCombinedID);
//		}
//		
//		return distance;
//	}
//	
//	public static HashMap<String, Node> mapNodes(String[] tour, int numVertices, ArrayList<Node> nodes) {
//		HashMap<String, Node> map = new HashMap<>();
//		for (int i=0; i < numVertices; i++) {
//			Node currentNode = nodes.get(i);
//			map.put(currentNode.getID(), currentNode);
//		}
//		return map;
//	}
//	
//	public static String[] getStringTourFromVertexTour(EulerTour eulerTour) {
//    	ArrayList<Node> arrayNodes = eulerTour.buildVertexTour();
//    	String[] stringTour = new String[arrayNodes.size()];
//    	
//    	for (int i=0; i< arrayNodes.size(); i++ ) {
//    		stringTour[i] = arrayNodes.get(i).getID();
//    	}
//    	
//    	return stringTour;
//    }
//	
//	public static ArrayList<Edge> generateArrayListOfEdgesFromTour(String[] tour, HashMap<String, Node> nodesMap) {
//		ArrayList<Edge> newTour = new ArrayList<>();
//		int n = tour.length;
//		for (int i=0; i < n; i++) {
//			int j = (i + 1) % n;
//			Node node1 = nodesMap.get(tour[i]);
//			Node node2 = nodesMap.get(tour[j]);
//	        Edge newEdge = new Edge(node1, node2);
//	        newTour.add(newEdge);
//		}
//		
//		return newTour;
// }
//	
//}
