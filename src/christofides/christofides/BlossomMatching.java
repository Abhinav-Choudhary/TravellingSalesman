package christofides;

import java.lang.reflect.Array;
import java.util.*;

public class BlossomMatching {
    //--------------------------------------Declarations-----------------------------------------------------------//
    private final int EVEN = 2;
    private final int ODD = 1;
    private final int UNLABELED = 0;

    private BlossomGraph g;

    // List of free blossom indices
    private ArrayList<Integer> free = new ArrayList<>();

    // outer[v] gives the index of the outermost blossom that contains v, outer[v] = v if v is not contained in any blossom
    private ArrayList<Integer> outer = new ArrayList<Integer>();

    //deep[v] is a list of all the original vertices contained inside v, deep[v] = v if v is an original vertex
    private ArrayList<ArrayList<Integer>> deep = new ArrayList<>();

    //shallow[v] is a list of the vertices immediately contained inside v, shallow[v] is empty is the default
    private ArrayList<ArrayList<Integer>> shallow = new ArrayList<>();

    //tip[v] is the tip of blossom v
    private ArrayList<Integer> tip = new ArrayList<>();

    //true if a blossom is being used
    private ArrayList<Boolean> active = new ArrayList<>();

    //Even, odd, neither (2, 1, 0)
    private ArrayList<Integer> type = new ArrayList<>();

    //forest[v] gives the father of v in the alternating forest
    private ArrayList<Integer> forest = new ArrayList<>();

    //root[v] gives the root of v in the alternating forest
    private ArrayList<Integer> root = new ArrayList<>();

    //A blossom can be blocked due to dual costs, this means that it behaves as if it were an original vertex and cannot be expanded
    private ArrayList<Boolean> blocked = new ArrayList<>();

    //dual multipliers associated to the blossoms, if dual[v] > 0, the blossom is blocked and full
    private ArrayList<Double> dual = new ArrayList<>();

    //slack associated to each edge, if slack[e] > 0, the edge cannot be used
    private ArrayList<Double> slack = new ArrayList<>();

    //mate[v] gives the mate of v
    private ArrayList<Integer> mate = new ArrayList<>();

    private int m, n;

    private Boolean perfect;

    private LinkedList<Integer> forestList = new LinkedList<>();
    private ArrayList<Boolean> visited = new ArrayList<>();

    //--------------------------------------Public Methods-----------------------------------------------------------//
    public BlossomMatching(BlossomGraph g) {
        this.g = g;
        // Number of vertices
        n = g.getNodeCount();
        // Number of edges
        m = g.getEdgeCount();
        initialize();
    }

    private void initialize() {
        outer = new ArrayList<Integer>(Collections.nCopies(2*n, 0));
        tip = new ArrayList<Integer>(Collections.nCopies(2*n, 0));
        active= new ArrayList<Boolean>(Collections.nCopies(2*n, false));
        type = new ArrayList<Integer>(Collections.nCopies(2*n, 0));
        forest = new ArrayList<Integer>(Collections.nCopies(2*n, 0));
        root = new ArrayList<Integer>(Collections.nCopies(2*n, 0));
        blocked = new ArrayList<Boolean>(Collections.nCopies(2*n, false));
        dual = new ArrayList<Double>(Collections.nCopies(2*n, 0.0));
        slack = new ArrayList<Double>(Collections.nCopies(m, 0.0));
        mate = new ArrayList<Integer>(Collections.nCopies(2*n, 0));
        visited = new ArrayList<Boolean>(Collections.nCopies(2*n, false));
        
        for (int i=0; i<2*n; i++) {
        	deep.add(new ArrayList<Integer>());
        	shallow.add(new ArrayList<Integer>());
        }
    }

    //--------------------------------------Private Methods-----------------------------------------------------------//
    private void grow() {
        reset();
        // All unmatched vertices will be roots in a forest that will be grown
        // The forest is grown by extending a unmatched vertex w through a matched edge u-v in a BFS fashion
        while(!forestList.isEmpty()) {
            Integer w = outer.get(forestList.poll());

            // w might be a blossom
            // we have to explore all the connections from vertices inside the blossom to other vertices
            ArrayList<Integer> deepArray = deep.get(w);
            for(Integer u: deepArray) {
                Boolean cont = false;
                ArrayList<Integer> adjacentList = g.adj(u);
                for(Integer v: adjacentList) {
                    if(isEdgeBlocked(u, v) || type.get(outer.get(v)) == ODD) continue;
                    // if v is unlabeled
                    if(type.get(outer.get(v)) != EVEN) {
                        // We grow the alternating forest
                        Integer vm = mate.get(outer.get(v));

                        forest.set(outer.get(v), u);
                        type.set(outer.get(v), ODD);
                        root.set(outer.get(v), root.get(outer.get(u)));
                        forest.set(outer.get(vm), v);
                        type.set(outer.get(vm), EVEN);
                        root.set(outer.get(vm), root.get(outer.get(u)));
                        if(!visited.get(outer.get(vm))) {
                            forestList.add(vm);
                            visited.set(outer.get(vm), true);
                        }
                    }
                    // If v is even and u and v are on different trees
                    // we found an augmenting path
                    else if(!root.get(outer.get(v)).equals(root.get(outer.get(u)))) {
                        augment(u,v);
                        reset();
                        cont = true;
                        break;
                    }
                    // If u and v are even and on the same tree
                    // we found a blossom
                    else if(!outer.get(u).equals(outer.get(v))) {
                        Integer b = blossom(u,v);
                        forestList.addFirst(b);
                        visited.set(b, true);
                        cont = true;
                        break;
                    }
                }
                if(cont) break;
                }
            }

            // Check whether the matching is perfect
            perfect = true;
            for (int i=0; i < n; i++) {
            	if (mate.get(outer.get(i)) == -1) {
            		perfect = false;
            	}
            }
    }

    private Boolean isAdjacent(int u, int v) {
        // Always adj as every vertex is fully connected
        return (g.isAdjacent(u, v) && !isEdgeBlocked(u, v));
    }

    private Boolean isEdgeBlocked(int e) {
        return slack.get(e) > 0;
    }

    private Boolean isEdgeBlocked(int u, int v) {
        return slack.get(g.getEdgeIndex(u, v)) > 0;
    }

    // Vertices will be selected in non-decreasing order of their degree
    // Each time an unmatched vertex is selected, it is matched to its adjacent unmatched vertex of minimum degree
    private void heuristic() throws Exception {
        ArrayList<Integer> degree = new ArrayList<Integer>(Collections.nCopies(n, 0));
        BinaryHeap b = new BinaryHeap();

        for(int i = 0; i < m; i++) {
            if(isEdgeBlocked(i)) continue;
            BlossomEdge p = g.getEdge(i);
            int u = p.u;
            int v = p.v;

            degree.set(u, degree.get(u)+1);
            degree.set(v, degree.get(v)+1);
        }

        for(int i = 0; i < n; i++)
            b.insert(degree.get(i), i);

        while(b.size() > 0) {
            int u = b.deleteMin();
            if(mate.get(outer.get(u)) == -1) {
                int min = -1;
                for(int v: g.adj(u)) {
                    if(isEdgeBlocked(u, v) || (outer.get(u).equals(outer.get(v))) || (mate.get(outer.get(v)) != -1)) continue;
                    if(min == -1 || (degree.get(v) < degree.get(min))) min = v;
                }
                if(min != -1) {
                    mate.set(outer.get(u), min);
                    mate.set(outer.get(min), u);
                }
            }
        }
    }

    //Destroys a blossom recursively
    private void destroyBlossom(int t) {
        if((t < n) || (blocked.get(t) && dual.get(t) > 0)) return;
        for(int s: shallow.get(t)) {
            outer.set(s, s);
            for(int jt: deep.get(s)) outer.set(jt, s);
            destroyBlossom(s);
        }
        active.set(t, false);
        blocked.set(t, false);
        addFreeBlossomIndex(t);
        mate.set(t, -1);
    }


    private void expand(int u, boolean expandBlocked) {
        int v = outer.get(mate.get(u));
        int index = m;
        int p = -1, q = -1;
        // Find the regular edge {p,q} of minimum index connecting u and its mate
        // We use the minimum index to grant that the two possible blossoms u and v will use the same edge for a mate
        for(int di: deep.get(u)) {
            for(int dj: deep.get(v)) {
                if(isAdjacent(di, dj) && (g.getEdgeIndex(di, dj) < index)) {
                    index = g.getEdgeIndex(di, dj);
                    p = di;
                    q = dj;
                }
            }
        }

        mate.set(u, q);
        mate.set(v, p);
        // If u is a regular vertex, we are done
        if((u < n) || (blocked.get(u) && !expandBlocked)) return;

        boolean found = false;
        int is = 0;
        // Find the position t of the new tip of the blossom
        ArrayList<Integer> shallowInitialArray = (ArrayList<Integer>) shallow.get(u).clone();
        while (is < shallowInitialArray.size()) {
        	if(found) break;
        	int si = shallowInitialArray.get(is);
            for(int jt: deep.get(si)) {
                if(found) break;
                if(jt == p) found = true;
            }
            is++;
            if(!found) {
                shallow.get(u).add(si);
                shallow.get(u).remove(0);
            }
        }

        ArrayList<Integer> it = shallow.get(u);
        int pos = 0;
        // Adjust the mate of the tip
        mate.set(it.get(pos), mate.get(u));
        pos++;
        //
        // Now we go through the odd circuit adjusting the new mates
        while(pos < shallow.get(u).size()) {
            int posNext = pos;
            posNext++;
            mate.set(it.get(pos), it.get(posNext));
            mate.set(it.get(posNext), it.get(pos));
            posNext++;
            pos = posNext;
        }

        // We update the sets blossom, shallow, and outer since this blossom is being deactivated
        for(int s: shallow.get(u)) {
            outer.set(s, s);
            for(int jt: deep.get(s)) outer.set(jt, s);
        }
        active.set(u, false);
        addFreeBlossomIndex(u);

        // Expand the vertices in the blossom
        for(int iti: shallow.get(u)) expand(iti, expandBlocked);
    }

    // Augment the path root[u], ..., u, v, ..., root[v]
    private void augment(int u, int v) {
        // We go from u and v to its respective roots, alternating the matching
        int p = outer.get(u);
        int q = outer.get(v);
        int outv = q;
        int fp = forest.get(p);
        mate.set(p, q);
        mate.set(q, p);
        expand(p, false); expand(q, false);

        while(fp != -1) {
            q = outer.get(forest.get(p));
            p = outer.get(forest.get(q));
            fp = forest.get(p);

            mate.set(p, q);
            mate.set(q, p);
            expand(p, false); expand(q, false);
        }

        p = outv;
        fp = forest.get(p);
        while(fp != -1) {
            q = outer.get(forest.get(p));
            p = outer.get(forest.get(q));
            fp = forest.get(p);
            mate.set(p, q);
            mate.set(q, p);
            expand(p, false); expand(q, false);
        }
    }

    private void reset() {
        for(int i = 0; i < 2*n; i++) {
            forest.set(i, -1);
            root.set(i, i);
            if(i >= n && active.get(i) && outer.get(i) == i) destroyBlossom(i);
        }
        visited = new ArrayList<Boolean>(Collections.nCopies(2*n, false));
        forestList.clear();
        for(int i = 0; i < n; i++) {
            if(mate.get(outer.get(i)) == -1) {
                type.set(outer.get(i), 2);
                if(!visited.get(outer.get(i))) forestList.add(i);
                visited.set(outer.get(i), true);
            } else {
                type.set(outer.get(i), 0);
            }
        }
    }

    private int getFreeBlossomIndex() {
        int i = free.get(free.size() - 1);
        free.remove(free.size() - 1);
        return i;
    }

    private void addFreeBlossomIndex(int i) {
        free.add(i);
    }

    private void clearBlossomIndices() {
        free.clear();
        for(int i = n; i < 2*n; i++) addFreeBlossomIndex(i);
    }

    // Contracts the blossom w, ..., u, v, ..., w, where w is the first vertex that appears in the paths from u and v to their respective roots
    private int blossom(int u, int v) {
        int t = getFreeBlossomIndex();
        ArrayList<Boolean> isInPath = new ArrayList<Boolean>(Collections.nCopies(2*n, false));

        // Find the tip of the blossom
        int u_ = u;
        while(u_ != -1) {
            isInPath.set(outer.get(u_), true);
            u_ = forest.get(outer.get(u_));
        }

        int v_ = outer.get(v);
        while(!isInPath.get(v_)) v_ = outer.get(forest.get(v_));
        tip.set(t, v_);

        // Find the odd circuit, update shallow, outer, blossom and deep
        // First we construct the set shallow (the odd circuit)
        ArrayList<Integer> circuit = new ArrayList<>();
        u_ = outer.get(u);
        circuit.add(0, u_);
        while(u_ != tip.get(t)) {
            u_ = outer.get(forest.get(u_));
            circuit.add(0, u_);
        }

        shallow.get(t).clear();
        deep.get(t).clear();
        for(int ele: circuit) {
            shallow.get(t).add(ele);
        }

        v_ = outer.get(v);
        while(v_ != tip.get(t)) {
            shallow.get(t).add(v_);
            v_ = outer.get(forest.get(v_));
        }

        // Now we construct deep and update outer
        int it = 0;
        while(it < shallow.get(t).size()) {
            u_ = shallow.get(t).get(it);
            outer.set(u_, t);
            for(int jt = 0; jt < deep.get(u_).size(); jt++) {
                deep.get(t).add(deep.get(u_).get(jt));
                outer.set(deep.get(u_).get(jt), t);
            }
            it++;
        }

        forest.set(t, forest.get(tip.get(t)));
        type.set(t, EVEN);
        root.set(t, root.get(tip.get(t)));
        active.set(t, true);
        outer.set(t, t);
        mate.set(t, mate.get(tip.get(t)));
        return t;
    }

    private void updateDualCosts() {
        double e1 = 0, e2 = 0, e3 = 0;
        boolean inite1 = false, inite2 = false, inite3 = false;
        for(int i = 0; i < m; i++) {
            int u = g.getEdge(i).u;
            int v = g.getEdge(i).v;

            if((type.get(outer.get(u)) == EVEN && type.get(outer.get(v)) == UNLABELED) ||
                    (type.get(outer.get(v)) == EVEN && type.get(outer.get(u)) == UNLABELED) ) {
                if(!inite1 || (e1 > slack.get(i))) {
                    e1 = slack.get(i);
                    inite1 = true;
                }
            } else if((!outer.get(u).equals(outer.get(v))) && type.get(outer.get(u)) == EVEN && type.get(outer.get(v)) == EVEN ) {
                if(!inite2 || (e2 > slack.get(i))) {
                    e2 = slack.get(i);
                    inite2 = true;
                }
            }
        }
        for(int i = n; i < 2*n; i++) {
            if(active.get(i) && i == outer.get(i) && type.get(outer.get(i)) == ODD && (!inite3 || e3 > dual.get(i))) {
                e3 = dual.get(i);
                inite3 = true;
            }
        }
        double e = 0;
        if(inite1) e = e1;
        else if(inite2) e = e2;
        else if(inite3) e = e3;

        if(e > e2/2.0 && inite2) e = e2/2.0;
        if(e > e3 && inite3)  e = e3;

        for(int i = 0; i < 2*n; i++) {
            if(i != outer.get(i)) continue;
            if(active.get(i) && type.get(outer.get(i)) == EVEN) {
                dual.set(i, dual.get(i) + e);
            } else if(active.get(i) && type.get(outer.get(i)) == ODD) {
                dual.set(i, dual.get(i) - e);
            }
        }

        for(int i = 0; i < m; i++) {
            int u = g.getEdge(i).u;
            int v = g.getEdge(i).v;
            if(!outer.get(u).equals(outer.get(v))) {
                if(type.get(outer.get(u)) == EVEN && type.get(outer.get(v)) == EVEN)
                    slack.set(i, slack.get(i)-2.0*e);
			    else if(type.get(outer.get(u)) == ODD && type.get(outer.get(v)) == ODD)
                    slack.set(i, slack.get(i)+2.0*e);
			    else if((type.get(outer.get(v)) == UNLABELED && type.get(outer.get(u)) == EVEN) ||
                        (type.get(outer.get(u)) == UNLABELED && type.get(outer.get(v)) == EVEN))
                    slack.set(i, slack.get(i)-e);
			    else if((type.get(outer.get(v)) == UNLABELED && type.get(outer.get(u)) == ODD) ||
                        (type.get(outer.get(u)) == UNLABELED && type.get(outer.get(v)) == ODD))
                    slack.set(i, slack.get(i)+e);
            }
        }

        for(int i = n; i < 2*n; i++) {
            if(dual.get(i) > 0) blocked.set(i, true);
            else if(active.get(i) && blocked.get(i)) {
                // The blossom is becoming unblocked
                if(mate.get(i) == -1) {
                    destroyBlossom(i);
                } else {
                    blocked.set(i, false);
                    expand(i, false);
                }
            }
        }
    }

    public ArrayList<Edge> solveMinimumCostPerfectMatching() throws Exception {
        solveMaximumMatching();
        if(!perfect) {
            throw new Exception("Error: The graph does not have a perfect matching");
        }
        clear();
        ArrayList<Double> cost = g.costList();
        //Initialize slacks (reduced costs for the edges)
        slack = g.costList();

        positiveCosts();

        // If the matching on the compressed graph is perfect, we are done
        perfect = false;
        while(!perfect) {
            // Run an heuristic maximum matching algorithm
            heuristic();
            // Grow a hungarian forest
            grow();
            updateDualCosts();
            //Set up the algorithm for a new grow step
            reset();
        }

        ArrayList<Integer> matching = retrieveMatching();

        double obj = 0;
        for(int it: matching)
            obj += cost.get(it);

        double dualObj = 0;
        for(int i = 0; i < 2*n; i++) {
            if(i < n) dualObj += dual.get(i);
            else if(blocked.get(i)) dualObj += dual.get(i);
        }
        ArrayList<Pair> maximumMatchingPairs = new ArrayList<>();
        for(int edgeIndex: matching) {
            int uIndex = g.getEdge(edgeIndex).u;
            int vIndex = g.getEdge(edgeIndex).v;
            Pair pair = new Pair(uIndex, vIndex);
            maximumMatchingPairs.add(pair);
        }
        return getMatchingEdges(maximumMatchingPairs);
    }

    private ArrayList<Edge> getMatchingEdges(ArrayList<Pair> pairs) {
        ArrayList<Edge> edges = new ArrayList<>();
        for(Pair pair: pairs) {
            Node u = g.getNodeWithIndex(pair.p);
            Node v = g.getNodeWithIndex(pair.q);
            edges.add(new Edge(u, v));
        }
        return edges;
    }

    private void positiveCosts() {
        double minEdge = 0;
        for(int i = 0; i < m; i++)
            if((minEdge - slack.get(i)) > 0) minEdge = slack.get(i);
        for(int i = 0; i < m; i++)
            slack.set(i, slack.get(i)-minEdge);
    }

    private ArrayList<Integer> solveMaximumMatching() {
        // TODO: double check initialize
//    	initialize();
        clear();
        grow();
        return retrieveMatching();
    }

    // Sets up the algorithm for a new run
    private void clear() {
        clearBlossomIndices();
        for(int i = 0; i < 2*n; i++) {
            outer.set(i, i);
            deep.get(i).clear();
            if(i<n) deep.get(i).add(i);

            shallow.get(i).clear();
            if(i < n) active.set(i, true);
            else active.set(i, false);

            type.set(i, 0);
            forest.set(i, -1);
            root.set(i, i);

            blocked.set(i, false);
            dual.set(i, 0.0);
            mate.set(i, -1);
            tip.set(i, i);
        }
        slack = new ArrayList<Double>(Collections.nCopies(m, 0.0));
    }

    private ArrayList<Integer> retrieveMatching() {
        ArrayList<Integer> matching = new ArrayList<>();
        for(int i = 0; i < 2*n; i++) {
            if(active.get(i) && mate.get(i) != -1 && outer.get(i) == i) expand(i, true);
        }

        for(int i = 0; i < m; i++) {
            int u = g.getEdge(i).u;
            int v = g.getEdge(i).v;

            if(mate.get(u) == v) matching.add(i);
        }
        return matching;
    }
}
