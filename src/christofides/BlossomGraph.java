package christofides;

import java.util.*;

class Pair {
    Integer p, q;

    public Pair(Integer p, Integer q) {
        this.p = p;
        this.q = q;
    }
}

class BlossomEdge {
    Integer u, v;
    public BlossomEdge(Integer u, Integer v) {
        this.u = u;
        this.v = v;
    }
}

class BlossomGraph {
    private Map<String, Integer> weights = new HashMap<>();
    private ArrayList<String> nodeList = new ArrayList<>();
    private HashMap<String, Node> nodeMapping = new HashMap<>();
    private Map<Integer, ArrayList<Integer>> adj = new HashMap<>();
    private int[][] edgeIndex;
    private ArrayList<BlossomEdge> blossomEdges = new ArrayList<>();
    private int edgeCount = 0;


    public BlossomGraph(ArrayList<Edge> edges) {
        HashSet<String> nodeSet = new HashSet<>();
        for(Edge edge: edges) {
        	if(!nodeSet.contains(edge.u.id))nodeList.add(edge.u.id);
        	if(!nodeSet.contains(edge.v.id))nodeList.add(edge.v.id);
            nodeSet.add(edge.u.id);
            nodeSet.add(edge.v.id);
            nodeMapping.put(edge.u.id, edge.u);
            nodeMapping.put(edge.v.id, edge.v);
        }
        edgeIndex = new int[nodeSet.size()][nodeSet.size()];
        for(int[] arr: edgeIndex) Arrays.fill(arr, -1);
        for(int i = 0; i< nodeList.size(); i++) adj.put(i, new ArrayList<Integer>());

        System.out.println("Node count"+nodeSet.size());
        System.out.println("Edge count"+edges.size());


        for(Edge edge: edges) {
            int uid = getNodeIndexWithId(edge.u.id);
            int vid = getNodeIndexWithId(edge.v.id);
            weights.put(uid+"#"+vid, (int)edge.weight);
            weights.put(vid+"#"+uid, (int)edge.weight);
            adj.get(uid).add(vid);
            adj.get(vid).add(uid);
            edgeIndex[uid][vid] = edgeCount;
            edgeIndex[vid][uid] = edgeCount;
            edgeCount++;
            blossomEdges.add(new BlossomEdge(uid, vid));

            System.out.println(uid+" "+vid+" "+(int)edge.weight);
        }
    }

    
    public Boolean isAdjacent(int u, int v) {
    	return edgeIndex[u][v] != -1;
    }

    private int getEdgeIndex(String u, String v) {
        int uid = getNodeIndexWithId(u);
        int vid = getNodeIndexWithId(v);
        return edgeIndex[uid][vid];
    }

    private int getNodeIndexWithId(String id) {
        for(int i = 0; i< nodeList.size(); i++) if(nodeList.get(i).equals(id)) return i;
        return -1;
    }

    public String getNodeIdWithIndex(int index) {
        return nodeList.get(index);
    }

    public ArrayList<Integer> adj(Integer u) {
        return adj.get(u);
    }

    public int getNodeCount() {
        return nodeList.size();
    }

    public int getEdgeCount() {
        return blossomEdges.size();
    }

    public BlossomEdge getEdge(int index) {
        return blossomEdges.get(index);
    }

    public int getWeight(String u, String v) {
        return weights.getOrDefault(u+"#"+v, 0);
    }

    public ArrayList<Double> costList() {
        ArrayList<Double> list = new ArrayList<>();
        for(BlossomEdge e: blossomEdges) {
            list.add((double)weights.get(e.u+"#"+e.v));
        }
        return list;
    }

    public int getMaxWeight() {
        int maxWeight = 0;
        for(int weight: weights.values()) maxWeight = Math.max(weight, maxWeight);
        return maxWeight;
    }

    public int getEdgeIndex(int u, int v) {
        return edgeIndex[u][v];
    }

    public ArrayList<Integer> getVertexDegree() {
        ArrayList<Integer> degrees = new ArrayList<>();
        for(int u=0; u<getNodeCount(); u++) {
            degrees.set(u, adj(u).size());
        }
        return degrees;
    }

    public Node getNodeWithIndex(int index) {
        String id = getNodeIdWithIndex(index);
        return nodeMapping.get(id);
    }
}