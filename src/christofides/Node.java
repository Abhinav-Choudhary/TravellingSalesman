package christofides;

public class Node {
	String id;
    public Double x;
    public Double y;
    public Node(String id, Double x, Double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }
    
    
    public Double getX() {
		return x;
	}


	public void setX(Double x) {
		this.x = x;
	}


	public Double getY() {
		return y;
	}


	public void setY(Double y) {
		this.y = y;
	}


	public String getID() {
    	return this.id;
    }

    public boolean equals(Node that) {
        return this.id.equals(that.id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
