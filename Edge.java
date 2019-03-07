import java.util.ArrayList;

public class Edge {
	public String word1;
	public String word2;
	public Double weight;
	public int marked;
	public Edge(String w1,String w2) {
		this.word1=w1;
		this.word2=w2;
		this.weight=0.0;
		this.marked=0;
		
	}
	public static Double findingWeight(ArrayList<Double> w1,ArrayList<Double> w2) {
		int len=w1.size();
		int i=0;
		Double result= 0.0;
		Double  divide=0.0;
		Double  len1= 0.0;
		Double  len2= 0.0;
		Double  divider=0.0;
		for(i=0;i<len;i++) {
			Double mult=w1.get(i)*w2.get(i);
		
			len1+=w1.get(i)*w1.get(i);
			len2+=w2.get(i)*w2.get(i);
			divide+=mult;
		}
		len1= Math.sqrt(len1);
		len2= Math.sqrt(len2);
		divider=len1*len2;
		result=divide/divider;
		//System.out.println(result);
		return  result;
		
	}
	
}
