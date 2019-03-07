import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Assignment3 {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		 Scanner scanner = new Scanner(new File(args[0]));
		 Scanner scanner2=new Scanner(new File(args[1]));
		 PrintWriter writer = new PrintWriter(args[2], "UTF-8");
		 ArrayList<Vertex> verList=new ArrayList<Vertex>();
		 ArrayList<Edge> edgeList=new ArrayList<Edge>();
		 ArrayList<Vertex> adjList=new ArrayList<Vertex>();
		 ArrayList<Edge> mstGraph=new ArrayList<Edge>();
		int clusters=Integer.parseInt(args[3]);
		 while(scanner.hasNextLine()) {
			 String line = scanner.nextLine();
			 String[] ary = line.split(" ");
			 int size=ary.length;
			 int i=0;
			 String words="";
			 
			 ArrayList<Double> vecField=new ArrayList<Double>();
			 for(i=0;i<size;i++) {
				 if(i==0) {
					 for(char s:ary[i].toCharArray()) {
						 if(s=='"') {
							 continue;
						 }
						 else {
							 words+=s;
						 }
						 
					 }
				 }
				 else {
					 Double num=Double.parseDouble(ary[i]);
					 vecField.add(num);
				 }
			 }
			 ArrayList<Edge> neighBours=new ArrayList<Edge>();
			 Vertex et=new Vertex(words, vecField,neighBours);
			 verList.add(et);
			
			 
		 }
		 while(scanner2.hasNextLine()) {
			 String line=scanner2.nextLine();
			 String[] ary=line.split("-");
			 if(ary[0]==null) {
				 continue;
			 }
			 else {
				 int control=0;
			 for(int i=0;i<2;i++) {
				 for(Vertex adjEle:adjList) {
					 if(adjEle.words.compareTo(ary[i])==0) {
						 control=1;
					 }
					 else {
						 continue;
					 }
				 }
				 for(Vertex v:verList) {
					 if(v.words.compareTo(ary[i])==0 && control!=1) {
						 adjList.add(v);
					 }
				 }
				
			 }
			 }
		 }
		 FillingAdj(adjList,edgeList);
		 //QuickSortWeight(adjList);
		 int sizen=adjList.size()/clusters;
		 sizen=sizen-3/clusters;
		 int high=edgeList.size();
		 int sizeVertex=verList.size();
		 sort(edgeList, 0, high-1);
		 edgeList.get(0).marked=1;
		 
		 mstGraph.add(edgeList.get(0));
		 edgeList.remove(0);
		 MST(verList, edgeList, mstGraph,0,sizeVertex);
		 Cluster(mstGraph, verList, clusters,writer);
		 writer.close();
		
		 

	}
	public static void Cluster(ArrayList<Edge> mstGraph,ArrayList<Vertex> v,int clusterNum,PrintWriter writer) {
		int size=mstGraph.size();
		sort(mstGraph, 0, size-1);
		ArrayList<String> num=new ArrayList<String>();
		for(int i=0;i<clusterNum-1;i++) {
			//ArrayList<String> words=new ArrayList<String>();
			if(num.isEmpty()) {
				num.add(mstGraph.get(size-(i+1)).word1);
				num.add(mstGraph.get(size-(i+1)).word2);
				mstGraph.remove(size-(i+1));
			}
			else {
				int control=0;
				for(String s:num) {
					if(s.compareTo(mstGraph.get(size-(i+1)).word1)==0 && s.compareTo(mstGraph.get(size-(i+1)).word2)!=0) {
						control=1;
						break;
					}
					else if(s.compareTo(mstGraph.get(size-(i+1)).word2)==0 && s.compareTo(mstGraph.get(size-(i+1)).word1)!=0) {
						control=2;
						break;
					}
					else {
						continue;
					}
				}
				if(control==1) {
					num.add(mstGraph.get(size-(i+1)).word2);
				}
				if(control==2) {
					num.add(mstGraph.get(size-(i+1)).word1);
				}
				if(control==0) {
					num.add(mstGraph.get(size-(i+1)).word1);
					num.add(mstGraph.get(size-(i+1)).word2);
				}
				mstGraph.remove(size-(i+1));
			}
			
			
		}
		int numSize=num.size();
		ArrayList<String> controller=new ArrayList<String>();
		for(int i=0;i<numSize;i++) {
			if(controller.isEmpty()) {
				FindingPathForCluster(mstGraph, num.get(i),0,controller,writer);
				writer.println();
				//System.out.println();
			}
			else {
				int control=0;
				for(String et:controller) {
					if(et.compareTo(num.get(i))==0) {
						control=1;
						break;
					}
					else {
						continue;
					}
					
				}
				if(control!=1) {
					FindingPathForCluster(mstGraph, num.get(i),0,controller,writer);
					//System.out.println();
					writer.println();
				}
				
			}
			//System.out.println(num.get(i));
			
		}
	
		
		
	}
	public static void FindingPathForCluster(ArrayList<Edge> e,String nextWord,int control,ArrayList<String> controlNode,PrintWriter writer) {
		if(control==1) {
			return;
		}
		int num=0;
		//System.out.print(nextWord+" ");
		writer.print(nextWord+" ");
		controlNode.add(nextWord);
		for(Edge findingNextNode:e) {
			if(findingNextNode.word1.compareTo(nextWord)==0) {
				e.remove(findingNextNode);
				FindingPathForCluster(e, findingNextNode.word2,0,controlNode,writer);
				
				return;
			}
			else if(findingNextNode.word2.compareTo(nextWord)==0) {
				e.remove(findingNextNode);
				FindingPathForCluster(e, findingNextNode.word1,0,controlNode,writer);
				//e.remove(findingNextNode);
				return;
			}
			else {
				continue;
			}
		}
		FindingPathForCluster(e, nextWord, 1,controlNode,writer);
	} 
	public static void MST(ArrayList<Vertex> v,ArrayList<Edge> e,ArrayList<Edge> mstGraph,int count,int vertexSize) {
		if(vertexSize-1==count) {
			return;
		}
		else {
			ArrayList<Vertex> MarkedVert=new ArrayList<Vertex>();
			for(Edge et:mstGraph) {
				for(Vertex vt:v) {
					if(vt.words.compareTo(et.word1)==0 || vt.words.compareTo(et.word2)==0) {
						vt.marked=1;
						MarkedVert.add(vt);
					}
				}
			}
			int size=e.size();
			for(int i=0;i<size;i++) {
				int control=0;
				for(Vertex marked:MarkedVert) {
					if(marked.words.compareTo(e.get(i).word1)==0||marked.words.compareTo(e.get(i).word2)==0) {
						control++;
					}
					else {
						continue;
					}
				}
			
				if(control==1) {
					e.get(i).marked=1;
					mstGraph.add(e.get(i));
					e.remove(i);
					break;
				}
				
			}
			count++;
			MST(v, e, mstGraph, count, vertexSize);
			return;
		}
	}
	public static void FillingAdj(ArrayList<Vertex> v,ArrayList<Edge> ed) {
		int adjSize=v.size();
		int count=0;
		for(Vertex vertex:v) {
			for(int a=0;a<adjSize;a++) {
				if(a==count) {
					continue;
				}
				else {
					//for adjaceny list
					Edge e=new Edge(vertex.words, v.get(a).words);
					Double weight=Edge.findingWeight(vertex.vector, v.get(a).vector);
					e.weight=weight;
					vertex.neighBours.add(e);
					int controlSize=ed.size();
					int control=0;
					if(controlSize==0) {
						//ed.add(e);
					}
					else {
						for(int i=0;i<controlSize;i++) {
							if(((ed.get(i).word1.compareTo(e.word2)==0) &&(ed.get(i).word2.compareTo(e.word1)==0))||(ed.get(i).word1.compareTo(e.word1)==0) &&(ed.get(i).word2.compareTo(e.word2)==0)) {
								control=1;
								break;
							}
							else {
								continue;
							}
							}
					}
					
					
					//for edges
					if(control!=1) {
						ed.add(e);
					}
					
					}
					
				}
			count++;
			}
			
		}

	
	public static void QuickSortWeight(ArrayList<Vertex> v) {
		for(Vertex vertex:v) {
			int size=vertex.neighBours.size();
			sort(vertex.neighBours, 0, size-1);
			
		}
	}
	


	 public static void sort(ArrayList<Edge> arr, int low, int high)
	    {
	        if (low < high)
	        {
	            /* pi is partitioning index, arr[pi] is 
	              now at right place */
	            int pi = partition(arr, low, high);
	 
	            // Recursively sort elements before
	            // partition and after partition
	            sort(arr, low, pi-1);
	            sort(arr, pi+1, high);
	        }
	    }
	 private static int partition(ArrayList<Edge> a, int lo, int hi)
	 {
	  int i = lo, j = hi+1;
	  while (true)
	  {
		  while (less( a.get(lo).weight,a.get(++i).weight))
			  if (i == hi) break;
		  while (less( a.get(--j).weight,a.get(lo).weight))
			  	if (j == lo) break;

		  if (i >= j) break;
		  exch(a, i, j);
	  }
	  	exch(a, lo, j);
	  return j;
	 }
	 public static void exch(ArrayList<Edge> v,int lo,int j) {
		 Edge e=v.get(lo);
		 v.set(lo, v.get(j));
		 v.set(j, e);
	 }
	 public static boolean less(Double a,Double b) {
		 if(a<b) {
			 return true;
		 }
		 else {
			 return false;
		 }
	 }
}
