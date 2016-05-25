
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Implmentation of a Graph data structure. This implementation uses a 
 * linked list to keep track of edges. 
 * @author JohnMezzanotte
 */
public class Graph<E> implements Cloneable{
    
    private final String NO_EDGES = "Vertex has no edges.";
    
    private Object[] vertexlist; 
    LinkedList<EdgeNode>[] edges;
    
    public Graph(int n){
        vertexlist = new Object[n]; 
        edges = new LinkedList[n];
    }
    
    /**
     * Specified edge is added to the graph, unless already present.
     * @param source The vertex number
     * @param target The vertex number of the target edge
     */
    public void addEdge(int source, int target){
        // We have to make sure the LinkedList at the source is 
        // initialized before adding into it
        if(edges[source] == null){
            edges[source] = new LinkedList();
        }
        
        edges[source].add(new EdgeNode(target));
    }
    
    /**
     * The specified edge is removed from the graph.
     * @param source The vertex number of the source of the edge
     * @param target The vertex number of the target of the edge
     */
    public void removeEdge(int source, int target){
        
        if(edges[source] == null){
            throw new NullPointerException(NO_EDGES);
        }
        
        for(int i = 0; i < edges[source].size(); i++){
            if(edges[source].get(i).getvnum() == target ){
                edges[source].remove(i);
            }
        }
        
    }
    
    /**
     * Determines whether or not there is an edge present between a source 
     * or starting vertex and a target vertex.
     * @param source The vertex number of the source of the edge
     * @param target The vertex number of the target of the edge
     * @return Returns true if there is a valid edge present, false otherwise.
     */
    public boolean isEdge(int source, int target){
        
        if(edges[source] == null){
            throw new NullPointerException(NO_EDGES);
        }
        
        boolean answer = false;
        
        for(int i=0; i < edges[source].size(); i++){
            if(edges[source].get(i).getvnum() == target){
                answer = true;
            }
        }
        
        return answer;
        
    }
    /**
     * 
     * @param vertex
     * @return 
     */
    public int[] neighbors(int vertex){
        
        int count; 
        int[] answer; 
        
        // first we get a count of the number of edges at the source vertex
        count = 0;
        for( int i = 0; i < edges[vertex].size(); i++){
            count++;
        }
        
        // now we can allocated the new array 
        answer = new int[count]; 
        
        // fill our answer array
        count = 0; 
        for( int i = 0; i < edges[vertex].size(); i++){
            answer[count++] = edges[vertex].get(i).getvnum();
        }
        
        return answer; 
    }
    
    @SuppressWarnings("unchecked")
    public E getLabel(int vertex){
        return (E) vertexlist[vertex];
    }
    
    public void setLabel(int vertex, E label){
        vertexlist[vertex] = label;
    }

    public int size(){
        return vertexlist.length;
    }
    
    public Graph<E> clone(){
        
        Graph<E> answer; 
        
        try{
            answer = (Graph<E>) super.clone();
        }catch (CloneNotSupportedException e){
            throw new RuntimeException("This class does not implment cloneable");
        }
       
        
        answer.vertexlist = vertexlist.clone();
        answer.edges = edges.clone();
        
        return answer;
        
    }
   
    /**
     * Determines if there is a path that proceeds from the vertex(start 
     * position) to the target(end position). This method will also print 
     * the vertex numbers of the path leading from the source vertex to the 
     * target.
     * @param vertex The number of the start vertex
     * @param target The number of the end vertex
     * @return Returns true if there is a path, false otherwise. 
     */
    public boolean isPath(int vertex, int target){

        ArrayQueue q = new ArrayQueue();
        ArrayList<Integer> processed = new ArrayList<>();
       
        q.enqueue(vertex);
       
        while(!q.isEmpty()){
            
            int current = (Integer)q.dequeue();
            
            // Process -- termine if there is a path here
            if(!processed.contains(current)){
                
                if(current == target){
                    System.out.println(target);
                    return true;
                }
            }
            
            // Add all the nieghbor nodes to the queue
            if(edges[current] != null ){
                
                for(int i = 0; i < edges[current].size(); i++){
                    if(!processed.contains(edges[current].get(i).getvnum())){
                        q.enqueue(edges[current].get(i).getvnum());
                        
                    }
                }
                
                System.out.println(current);
            }
            
            // Mark as processed
            processed.add(current);
        }
       
        return false;
        
    } // end is path
    
    
}
