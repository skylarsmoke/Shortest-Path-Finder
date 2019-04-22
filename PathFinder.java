package Lab3;

/**
 * 
 * Skylar Smoker and Austin Hull
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PathFinder {
	int[][] map;
	int v=0, e =0;

	public PathFinder(String fileName)  {
		
		File file = new File(fileName);
		Scanner sc;
		try {
			sc = new Scanner(file);
			while(sc.hasNext()) {
	    			int z[] = new int[2];
				String line = sc.nextLine();
				if (line.startsWith("p sp")) {
					line= line.substring(4);
					String[] strs = line.trim().split("\\s+");
				    for (int i = 0; i < strs.length; i++) 
				    		z[i] = Integer.parseInt(strs[i]);
					v = z[0]+1;
					e = z[1];
					map=new int[v][v];
					break;
				}
			}
			
//			for (int i =0; i<v; i++)
//				for (int j = 0; j<v; j++)
//					map.add(i, j, Integer.MAX_VALUE);
			
			while (sc.hasNext()) {
				int z[] = new int[3];
				String line = sc.nextLine();
				if (line.startsWith("a")) {
					line = line.substring(1);
					String[] strs = line.trim().split("\\s+");
				    for (int i = 0; i < strs.length; i++) 
				    		z[i] = Integer.parseInt(strs[i]);
				    map[z[0]][z[1]]=z[2];
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/**
		for(int i=0; i<v; i++) {
			for(int j=0; j<v; j++)
				System.out.print(map[i][j] + " ");
			System.out.print("\n");
		}
		*/
		
		printMenu(v - 1);
	}
	
	public void dijkstra(int src, int end){
		int nVertices = map[0].length; 

	    
	    int[] shortestDistances = new int[nVertices]; 

	    boolean[] added = new boolean[nVertices]; 

	    for (int vertexIndex = 0; vertexIndex < nVertices;  
	                                        vertexIndex++) 
	    { 
	        shortestDistances[vertexIndex] = Integer.MAX_VALUE; 
	        added[vertexIndex] = false; 
	    } 
	      
	    shortestDistances[src] = 0; 

	    int[] parents = new int[nVertices]; 

	    parents[src] = -1; 

	    for (int i = 1; i < nVertices; i++) 
	    { 


	        int nearestVertex = -1; 
	        int shortestDistance = Integer.MAX_VALUE; 
	        for (int vertexIndex = 0; 
	                 vertexIndex < nVertices;  
	                 vertexIndex++) 
	        { 
	            if (!added[vertexIndex] && 
	                shortestDistances[vertexIndex] <  
	                shortestDistance)  
	            { 
	                nearestVertex = vertexIndex; 
	                shortestDistance = shortestDistances[vertexIndex]; 
	            } 
	        } 

         added[nearestVertex] = true; 

         for (int vertexIndex = 0; 
                  vertexIndex < nVertices;  
                  vertexIndex++)  
         { 
             int edgeDistance = map[nearestVertex][vertexIndex]; 
               
             if (edgeDistance > 0
                 && ((shortestDistance + edgeDistance) <  
                     shortestDistances[vertexIndex]))  
             { 
                 parents[vertexIndex] = nearestVertex; 
                 shortestDistances[vertexIndex] = shortestDistance +  
                                                    edgeDistance; 
             } 
         } 
     } 
     pathTo(end, shortestDistances, parents);
 } 
	
	public int minDistance(int dist[], Boolean sptSet[]) {
		int min= Integer.MAX_VALUE, min_index= -1;
		
		for(int iter= 0; iter <v; iter++) {
			if(sptSet[iter]==false && dist[iter]<=min) {
				min= dist[iter];
				min_index=iter;
			}
		}
		return min_index;
	}
	
	private static void printPath(int currentVertex, int[] parents, int fin) { 
		if (currentVertex == -1) {
			return; 
		} 
		printPath(parents[currentVertex], parents, fin); 
		System.out.print(currentVertex);
		if (currentVertex != fin) {
			System.out.print(" -> ");
		}
	} 
	
	public void pathTo(int n, int[] dist, int[] parents) {
		printPath(n, parents, n);
		System.out.println("\nTotal distance: " + dist[n]);
			
	}
	
	
	
	
public void printMenu(int v) {
		
		int answer = 0;
		Scanner user = new Scanner(System.in);
		while(answer != 2) {
			System.out.println();
			System.out.println("The current graph has vertices from 1 to " + v);
			System.out.println("Would you like to:");
			System.out.println("   1. Find a new route");
			System.out.println("   2. Exit");
			
			
			while(!user.hasNextInt()) {
				System.out.println("Please enter an integer!");
				user.next();
			}
			
			answer = user.nextInt();
			int source;
			int dest;
			if (answer == 1) {
				
				System.out.println("Source? ");
				source = user.nextInt();
				System.out.println("Destination? ");
				dest = user.nextInt();
				
				System.out.println("\nShortest path from " + source + " to " + dest + ":");
				double startTime = System.currentTimeMillis();
				try {
					dijkstra(source, dest);
				} catch (ArrayIndexOutOfBoundsException e1) {
					System.out.println("Path does not exist!");
				}
				double endTime = System.currentTimeMillis();
				System.out.println("Time to find: " + (endTime - startTime)/1000 + " sec");
				
				
			}
		}
		user.close();
	}
	
	public static void main(String args[]) {
		PathFinder t = new PathFinder(args[0]);
	}
}