/****************************************************************
This class simulates the activity in an activity network
*****************************************************************/
import java.awt.*;

public class Edge
{
  private Vertex vertexOne;
  private Vertex vertexTwo;

  private int activityTime ;

  private int startX ;
  private int startY ;
  private int endX ;
  private int endY ;

  private final int ID ;

  private int beginX ;
  private int beginY ;

  public Edge ( Vertex vertex1 , Vertex vertex2  , int  activityTime , int ID )
  {
    vertexOne = vertex1;
    vertexTwo = vertex2;
    this.activityTime = activityTime ;
    startX =  vertexOne.getX() ;
    startY = vertexOne.getY() ;
    endX = vertexTwo.getX();
    endY =  vertexTwo.getY();

    if ( startX < endX )
      beginX = startX ;
    else
      beginX = endX ;

    if ( startY < endY )
      beginY = startY ;
    else
      beginY = endY ;

    this.ID = ID ;
  }
  /* it draws an activity from the center of two events
     it prints the time and the name of the activity on the medium of the line
  */
  public void draw ( Graphics g )
  {
    if (getCritical () )
      g.setColor(Color.red);
    else
      g.setColor(Color.black);
    g.drawLine(startX,startY,endX,endY);

    g.drawString("T" + ID + " " + activityTime ,beginX +
    Math.abs(startX - endX) /2 , beginY + Math.abs(startY - endY)/2);

  }
  /*
    return true if a Point pt is inside a line
  */
   public boolean isInclude ( Point pt )
  {
    return LineInside.inside(pt , startX , startY , endX , endY );

  }

  public int getTime ()
  {
    return activityTime ;
  }

  public void setTime ( int time )
  {

    activityTime = time ;
  }

  public int getSource ()
  {
    return vertexOne.getId() ;
  }

  public int getDestination ()
  {
    return vertexTwo.getId() ;

  }

  public Vertex getVertexSource ()
  {
    return vertexOne ;

  }
   public int getId()
  {
    return ID;
  }


  public String toString()
  {
    return "Activity " + this.getId();

  }

  public boolean getCritical ()
  {
    return(vertexTwo.getLatestEvent() - getTime())
    ==vertexOne.getEarliestEvent();
  }

  public int   getLatestEvent()
  {
    return  vertexTwo.getLatestEvent() - getTime() ;
  }
  public int  getEarliestEvent()
  {
    return vertexOne.getEarliestEvent();

  }
}

