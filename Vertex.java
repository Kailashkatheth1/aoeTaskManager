/**
 */

import java.awt.*;

public class Vertex {
  private int vertexX;
  private int vertexY;
  private final int Diameter = 40;
  private final int iD;

  private int successors ;
  private int predecessors  ;

  private int eeTime = 0  ;
  private int leTime = Integer.MAX_VALUE  ;

  public Vertex ( int x , int y , int iD )
  {
    vertexX = x;
    vertexY = y;
    this.iD = iD;
  }

  // draw an event and print its name
  public void draw ( Graphics g )
  {
    if  ( !getCritical() )
      g.setColor(Color.black);
    else
      g.setColor(Color.red);
    g.drawOval( vertexX - Diameter/2 ,vertexY - Diameter/2  , Diameter , Diameter);

    g.drawString("E"+iD,vertexX - 4 ,vertexY - 1  );

   }

  // This method checks if the click is inside the vertice
  public boolean isInclude ( int x , int y )
  {
    return ( (Diameter/2 > Math.abs((vertexX - x))) && (Diameter/2 >  Math.abs((vertexY - y))));

  }

  // returns the X coordinate of the center of the vertice
  public int getX()
  {
    return vertexX;
  }
  // returns the Y coordinate of the center of the vertice
  public int getY()
  {
    return vertexY;
  }

  // returns the Id of the vertice
  public int getId()
  {
    return iD;
  }

  public void incSuccessorsCount ()
  {
    successors ++ ;
  }

  public void decSuccessorsCount()
  {
    successors -- ;
  }

  public int getSuccessorsCount ()
  {
    return successors ;

  }

  public void incPredecessorsCount ()
  {
    predecessors ++ ;
  }

  public void decPredecessorsCount()
  {
    predecessors -- ;
  }

  public int getPredecessorsCount ()
  {
    return predecessors;

  }


  public void setEarliestEvent ( int eeTime )
  {
    this.eeTime =  eeTime ;
  }

  public int  getEarliestEvent ()
  {
    return eeTime ;

  }

    public void setLatestEvent ( int eeTime )
  {
    this.leTime =  eeTime ;
  }

  public int  getLatestEvent ()
  {
    return leTime ;

  }

  public boolean getCritical ()
  {
    return (eeTime == leTime) ;


  }

  public String toString()
  {
    return "Event " + this.getId();

  }

  public void reset ()
  {
    eeTime = 0  ;
    leTime = Integer.MAX_VALUE  ;

  }


}
