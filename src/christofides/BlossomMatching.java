//import java.util.*;
//
//
//
//class MatchingAlgorithm {
//    BlossomGraph g;
//    ArrayList<String> gnodes = new ArrayList<>();
//    // Find the maximum edge weight.
//    int maxweight = 0;
//
//    // If v is a matched vertex, mate[v] is its partner vertex.
//    // If v is a single vertex, v does not occur as a key in mate.
//    // Initially all vertices are single; updated during augmentation.
//    Map<String, String> mate = new HashMap<>();
//
//    // If b is a top-level blossom,
//    // label.get(b) is None if b is unlabeled (free),
//    //            1 if b is an S-blossom,
//    //            2 if b is a T-blossom.
//    // The label of a vertex is found by looking at the label of its top-level
//    // containing blossom.
//    // If v is a vertex inside a T-blossom, label[v] is 2 iff v is reachable
//    // from an S-vertex outside the blossom.
//    // Labels are assigned during a stage and reset after each augmentation.
//    Map<String, Integer> label = new HashMap<>();
//
//    // If b is a labeled top-level blossom,
//    // labeledge[b] = (v, w) is the edge through which b obtained its label
//    // such that w is a vertex in b, or None if b's base vertex is single.
//    // If w is a vertex inside a T-blossom and label[w] == 2,
//    // labeledge[w] = (v, w) is an edge through which w is reachable from
//    // outside the blossom.
//    Map<String, BlossomEdge> labeledge = new HashMap<>();
//
//    // If v is a vertex, inblossom[v] is the top-level blossom to which v
//    // belongs.
//    // If v is a top-level vertex, inblossom[v] == v since v is itself
//    // a (trivial) top-level blossom.
//    // Initially all vertices are top-level trivial blossoms.
//    Map<String, String> inblossom = new HashMap<>();
//
//    // If b is a sub-blossom,
//    // blossomparent[b] is its immediate parent (sub-)blossom.
//    // If b is a top-level blossom, blossomparent[b] is None.
//    Map<String, String> blossomparent = new HashMap<>();
//
//    // If b is a (sub-)blossom,
//    // blossombase[b] is its base VERTEX (i.e. recursive sub-blossom).
//    Map<String, String> blossombase = new HashMap<>();
//
//    // If w is a free vertex (or an unreached vertex inside a T-blossom),
//    // bestedge[w] = (v, w) is the least-slack edge from an S-vertex,
//    // or None if there is no such edge.
//    // If b is a (possibly trivial) top-level S-blossom,
//    // bestedge[b] = (v, w) is the least-slack edge to a different S-blossom
//    // (v inside b), or None if there is no such edge.
//    // This is used for efficient computation of delta2 and delta3.
//    Map<String, Edge> bestedge = new HashMap<>();
//
//    // If v is a vertex,
//    // dualvar[v] = 2 * u(v) where u(v) is the v's variable in the dual
//    // optimization problem (if all edge weights are integers, multiplication
//    // by two ensures that all values remain integers throughout the algorithm).
//    // Initially, u(v) = maxweight / 2.
//    Map<String, Integer> dualvar = new HashMap<>();
//
//    // If b is a non-trivial blossom,
//    // blossomdual[b] = z(b) where z(b) is b's variable in the dual
//    // optimization problem.
//    Map<String, Integer> blossomdual = new HashMap<>();
//
//    // If (v, w) in allowedge or (w, v) in allowedge, then the edge
//    // (v, w) is known to have zero slack in the optimization problem;
//    // otherwise the edge may or may not have zero slack.
//    Map<Edge, Boolean> allowedge = new HashMap<>();
//
//    // Queue of newly discovered S-vertices.
//    Queue<String> queue = new LinkedList<>();
//
//    public MatchingAlgorithm(BlossomGraph g) {
//        this.g = g;
//        // Get a list of vertices.
//        gnodes = g.getNodes();
//        // don't bother with empty graphs
//        if(gnodes.size() == 0) return;
//
//        initialize();
//        match();
//    }
//
//    private void initialize() {
//        // Find the maximum edge weight.
//        maxweight = g.getMaxWeight();
//        for(String node: gnodes) inblossom.put(node, node);
//        for(String node: gnodes) blossomparent.put(node, null);
//        for(String node: gnodes) blossombase.put(node, node);
//        for(String node: gnodes) dualvar.put(node, maxweight);
//    }
//
//    public void match() {
//
//
//
//
//
//    }
//
//    // Return 2 * slack of edge (v, w) (does not work inside blossoms).
//    private int slack(String v, String w) {
//        int weight = g.getWeight(v, w);
//        return dualvar.getOrDefault(v, 0) + dualvar.getOrDefault(w, 0) - 2 * weight;
//    }
//
//    // Assign label t to the top-level blossom containing vertex w,
//    // coming through an edge from vertex v.
//    private void assignLabel(String w, int t, String v) throws Exception {
//        String b = inblossom.get(w);
//        assert (label.get(w) == null && label.get(b) == null) : "Unexpected node values in assignlabel";
//        label.put(w, t); label.put(b, t);
//        if(v != null) {
//            labeledge.put(w, new BlossomEdge(v, w));
//            labeledge.put(b, new BlossomEdge(v, w));
//        } else {
//            labeledge.put(w, null); labeledge.put(b, null);
//            bestedge.put(w, null); bestedge.put(b, null);
//        }
//        if(t == 1) {
//            // b became an S-vertex/blossom; add it(s vertices) to the queue.
//            if(b.getClass().isAssignableFrom(Blossom.class)) {
//        }
//
//
//
//
//
//        if isinstance(b, Blossom):
//        queue.extend(b.leaves())
//            else:
//        queue.append(b)
//        elif t == 2:
//            # b became a T-vertex/blossom; assign label S to its mate.
//            # (If b is a non-trivial blossom, its base is the only vertex
//            # with an external mate.)
//        base = blossombase[b]
//        assignLabel(mate[base], 1, base)
//
//    }
//
//
//}
//
//class Blossom {
//    ArrayList<Object> childs = new ArrayList<>();
//    ArrayList<Edge> edges = new ArrayList<Edge>();
//    ArrayList<Edge> myBestEdges = new ArrayList<>();
//
//    public Blossom() {}
//
//
//    // Generate the blossom's leaf vertices.
//    private ArrayList<Node> leaves() {
//        ArrayList<Node> nodes = new ArrayList<>();
//        checkAndObtainLeaves(nodes);
//        return nodes;
//    }
//
//    private void checkAndObtainLeaves(ArrayList<Node> nodes) {
//        for(Object t: childs) {
//            if(t.getClass().isAssignableFrom(Blossom.class)) {
//                ((Blossom)t).leaves();
//            } else {
//                nodes.add((Node)t);
//            }
//        }
//    }
//
//}