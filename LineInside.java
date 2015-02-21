import java.awt.*;

public class LineInside

{
  private final static double TOL = 4.0;

  // static method that returns true when the point is inside the line

  public static boolean inside ( Point pt ,  double startx  , double starty,
                                    double endx , double endy )

  {

    if ( pt.x < startx || pt.x > endx )
      return false ;

    double distance = 0;

    double width = endx - startx;
    double height = endy - starty;

    double dx = (pt.x - startx);
    double dy = (pt.y - starty);

    if ( dx!= 0 && width != 0 )
    {
      double angleTocorner = Math.atan(height/width);
      double angleToPoint = Math.atan(dy/dx);

      distance = Math.sqrt( dx*dx + dy*dy) * Math.sin(Math.abs(angleTocorner - angleToPoint));

    }
    else
    {
      distance = Math.abs(dx);
    }


    return ( distance < TOL );


  }


}
