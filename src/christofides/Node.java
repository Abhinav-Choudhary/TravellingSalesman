package christofides;

public class Node {
	String id;
    double x;
    double y;
    public Node(String id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public boolean equals(Node that) {
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
    
    public String getID() {
    	return this.id;
    }
}
