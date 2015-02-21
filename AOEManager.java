/**
 * Manager for AOE network.
 */

import java.util.* ;
import java.awt.* ;

public class AOEManager {
  private Vector vVertex;   // Set of Event
  private Vector vEdge;     // Set of Edge

  /**
   * The AOE network.
   */
  private WeightedDirectedGraph graph ;
  private VectorStack stack = new VectorStack();

  /**
   * true iff in testing mode.
   */
  private boolean isTesting = true;
  /**
   * Array of vertexes; indices map to corresponding vertex.
   */
  private Vertex[] vertices;
  /**
   * Start vertex. Used in forwardStage().
   */
  private Vertex start;
  /**
   * End vertex. Used in backwardStage().
   */
  private Vertex end;

  /**
   * Constructor for AOE manager.
   */
  public AOEManager() {
    vVertex = new Vector();
    vEdge = new Vector();
  }

  public void draw(Graphics g) {
    for (int i =  0; i < vVertex.size(); i++)
      ((Vertex)vVertex.elementAt(i)).draw(g);
    for (int i =  0; i < vEdge.size(); i++)
      ((Edge)vEdge.elementAt(i)).draw(g);
   }

  public void addVertex(int x, int y, int id) {
    vVertex.add(new Vertex(x, y, id));
  }

  public void addEdge(Vertex v1, Vertex v2, int timeActivity, int iDedge) {
    vEdge.add(new Edge(v1,v2, timeActivity,iDedge));
  }

   // Return a specific Vertex if x and y are within the circle
  public Vertex findVertex (int x , int y) {
    for (int i = 0; i < vVertex.size(); i++) {
      if (((Vertex)vVertex.elementAt(i)).isInclude( x , y )) {

        return (Vertex)vVertex.elementAt(i);
      }
    }
    return null;
  }
  // Return a specific Vertex if the point are along the Edge
  public Edge findEdge(Point pt) {
    for (int i = 0; i < vEdge.size(); i++) {
      if (((Edge)vEdge.elementAt(i)).isInclude( pt))
      {

        return (Edge)vEdge.elementAt(i);
      }
    }
    return null;
  }

  /**
   * Total run time: O(|V|+|E|), with constants left out.
   */
  public void criticalPath() {
    if (vVertex.isEmpty())
      return;


    // initialize array of vertices VERTICES so that ID of vertices
    // map to corresponding vertex (otherwise we'll have to search thorugh
    // vVector everytime we look for the next vertex, because nothing says
    // that the indices of vVector correspond to the numbered vertex);
    vertices = new Vertex[vVertex.size()];
    for (int i = 0; i < vVertex.size(); i++) {
      Vertex v = (Vertex) vVertex.elementAt(i);
      vertices[v.getId()] = v;
    }
    // also initializes WeightedDirectedGraph
    graph = new WeightedDirectedGraph(vVertex.size());
    for (int i = 0; i < vEdge.size(); i++) {
      Edge e = (Edge) vEdge.elementAt(i);
      if (e == null) {
        System.out.println("Unexpected error occured in initialization of Weighted Graph.");
        return;
      }
      vertices[e.getSource()].incSuccessorsCount();
      vertices[e.getDestination()].incPredecessorsCount();
      graph.addEdge(e.getSource(), e.getDestination(), e.getTime());
    }
    // initializes start and end vertex
    for (int i = 0; i < vVertex.size(); i++) {
      Vertex v = (Vertex) vVertex.elementAt(i);
      if (v.getPredecessorsCount() == 0) { // has indegree 0
        start = v;
      } else if (v.getSuccessorsCount() == 0) { // has outdegree 0
        end = v;
      }
    }

    forwardStage ();
    backwardStage ();
  }

  /**
   * Total run time: O(|V|+|E|).
   */
  public void forwardStage() {
    // push all vertices with indegree 0 onto stack
    stack.push(start);

    for (int loop = 0; loop < vertices.length; loop++) {
      Vertex v = (Vertex) stack.pop();
      if (v == null) {
        System.out.println("Cycle exists in network!");
        return;
      } else {
        java.util.List<Integer> l = graph.getSuccessors(v.getId());
        for (Integer i : l) {
          vertices[i].decPredecessorsCount();
          System.out.println("forwardStage(): i is " + i);
          System.out.println("forwardStage(): getSuccessorsCount " + vertices[i].getSuccessorsCount());
          System.out.println("forwardStage(): getPredecessorsCount " + vertices[i].getPredecessorsCount());

          // compute ee
          if (vertices[i].getEarliestEvent() < (v.getEarliestEvent() + graph.getWeight(v.getId(), i))) {
            vertices[i].setEarliestEvent(v.getEarliestEvent() + graph.getWeight(v.getId(), i));
          }
          if (vertices[i].getPredecessorsCount() == 0) {
            stack.push(vertices[i]);
          } else if (vertices[i].getPredecessorsCount() < 0) {
            System.out.println("Unexpected error: PredecessorCount is < 0");
          }
        }
      }
    }

    testing (isTesting);
  }
  /**
   * Total run time: O(|V|+|E|). Of course same as forwardStage() because this
   * is just a reverse of it.
   */
  public void backwardStage() {
    if (end == null) {
      System.out.println("Error: end vertex is not set.");
      return;
    }
    end.setLatestEvent(end.getEarliestEvent());
    for (int i = 0; i < vVertex.size(); i++) {
      Vertex v = (Vertex) vVertex.elementAt(i);
      // initializes latest event times of all vertices to project duration
      v.setLatestEvent(end.getLatestEvent());
    }
    stack.push(end); // pushes end vertex onto stack
    for (int loop = 0; loop < vertices.length; loop++) {
      Vertex v = (Vertex) stack.pop();
      if (v == null) {
        System.out.println("Cycle exists in network! backwardStage()");
        return;
      } else {
        java.util.List<Integer> l = graph.getPredecessors(v.getId());
        for (Integer i : l) {
          vertices[i].decSuccessorsCount();
          // compute ee
          if (vertices[i].getLatestEvent() > (v.getLatestEvent() - graph.getWeight(i, v.getId()))) {
            vertices[i].setLatestEvent(v.getLatestEvent() - graph.getWeight(i, v.getId()));
          }
          if (vertices[i].getSuccessorsCount() == 0) {
            stack.push(vertices[i]);
          } else if (vertices[i].getSuccessorsCount() < 0) {
            System.out.println("Unexpected error: PredecessorCount is < 0. BackWard()");
          }
        }
      }
    }
      testing ( isTesting );

  }

  public void resetEarlyLateTime ()
  {
    for ( int i = 0 ; i < vVertex.size() ; i ++ )
    {
      Vertex current = (Vertex)vVertex.elementAt(i);
      current.reset();


    }

  }


  public void testing ( boolean testing)
  {
    if (  testing )
    {

      System.out.println("Testing Forward Stage");

       for ( int i = 0 ; i < vVertex.size() ; i ++ )
      {
        Vertex vtry =      ((Vertex)vVertex.elementAt(i))  ;
        System.out.println(vtry.getEarliestEvent());
      }

      System.out.println("Testing BackWard Stage");

      for ( int i = 0 ; i < vVertex.size() ; i ++ )
      {
        Vertex vtry =      ((Vertex)vVertex.elementAt(i))  ;
        System.out.println(vtry.getLatestEvent());
      }
    }
  }
}
