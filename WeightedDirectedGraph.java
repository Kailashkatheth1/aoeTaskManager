/**
 * @author Angus Hung
 * WeightedDirectedGraph represents the AOE network, the directed graph
 * consisting of activities (the edges) and completion of those activities
 * (the vertexes).
 */

import java.util.ArrayList;
import java.util.List;

public class WeightedDirectedGraph {
  /**
   * An array of Lists of ints representing the weighted directed
   * graph. The index of the array corresponds to the vertex number, and the
   * List associated with the vertex number contains ints that represent the
   * vertices that succeed the numbered vertex.
   */
  private List<Integer>[] adjacencyList;
  /**
   * An array of Lists of ints representing the reverse of the weight directed * graph. Used to find the predecessors of a numbered vertex.
   */
  private List<Integer>[] reverseAdjacencyList;
  /**
   * An array of arrays of ints used to make constant time operations
   * possible with getWeight() and isEdge(). The index of the array
   * corresponds to the source vertex number, and the index of the array
   * associated with each vertex number is the target vertex number. Each int
   * inside the array associated with each source vertex represents the
   * weight of edge {source,target}. If the weight is 0, then no such edge
   * exists in the graph.
   */
  private int[][] adjacencyMatrix;

  /**
   * Constructor for WeightedDirectedGraph.
   * @param  n Number of vertices in the graph; hence adjacencyList does not
   * need to be resized.
   */
  public WeightedDirectedGraph(int n) {
    adjacencyList = new ArrayList[n];
    reverseAdjacencyList = new ArrayList[n];
    adjacencyMatrix = new int[n][n];
    for (int i = 0; i < n; i++) {
      adjacencyList[i] = new ArrayList<Integer>();
      reverseAdjacencyList[i] = new ArrayList<Integer>();
    }
  }

  /**
   * Adds edge to the weighted directed graph. Takes O(1) time.
   * @param source vertex number of starting node.
   * @param target vertex number of ending node.
   * @param weight duration of the edge.
   */
  public void addEdge(int source , int target, int weight) {
    adjacencyList[source].add((Integer) target);
    reverseAdjacencyList[target].add((Integer) source);
    adjacencyMatrix[source][target] = weight;
  }

  /**
   * returns the weight of edge {source,target}. Takes O(1) time with
   * adjacency matrix.
   * @param  source vertex number of starting node.
   * @param  target vertex number of ending node.
   * @return        weight of the edge
   */
  public int getWeight (int source, int target) {
    return adjacencyMatrix[source][target];
  }

  /**
   * returns true iff {source,target} is a valid edge. Takes O(1).
   * @param  source number of source vertex.
   * @param  target number of target vertex.
   * @return        true iff {source,target} is valid.
   */
  public boolean isEdge (int source, int target) {
    return (adjacencyMatrix[source][target] > 0);
  }

  /**
   * returns a List of ints that are the succeeding vertices. Takes O(1).
   * @param  vertex specified vertex number to get successors for.
   * @return        List of ints that are successors to vertex.
   */
  public List<Integer> getSuccessors(int vertex) {
    return adjacencyList[vertex];
  }

  /**
   * returns a List of ints that are the preceding vertices. Takes O(1).
   * @param  vertex specified vertex number to get predecessors for.
   * @return        List of ints that are predecessors to vertex.
   */
  public List<Integer> getPredecessors(int vertex) {
    return reverseAdjacencyList[vertex];
  }

}
