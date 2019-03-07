import java.util.ArrayList;

public class Vertex {
	public String words;
	public int marked;
	public ArrayList<Double> vector=new ArrayList<Double>();
	public ArrayList<Edge> neighBours=new ArrayList<Edge>();
	public Vertex(String wor,ArrayList<Double> vec,ArrayList<Edge> neigh) {
		this.words=wor;
		this.vector=vec;
		this.neighBours=neigh;
		this.marked=0;
	}
}
