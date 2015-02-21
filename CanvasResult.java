/**
 * CanvasResult lays out the canvas where the activity network will be drawn
 * and the information panel.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class CanvasResult extends JPanel {

  // Jlabel Results
  private JLabel nameResult ;
  private JLabel timeResult ;
  private JLabel criticalResult ;
  private JLabel eeResult ;
  private JLabel leResult ;
  private JLabel delay ;
  private JTextField timeField ;
  private JButton setTime ;

  private boolean EventChoice = true ;   // Event or Activity drawing

  private boolean active = true  ;

  private static int iDvertice;  // The Id of the events
  private static int iDedge ;    // The Id of the activities

  private JPanel information ;
  private GridBagConstraints c ;

  private AOEManager aoe ;

  private int timeActivity ;

  private CanvasRect canvas;

  private Edge anEdge;



  public CanvasResult ()
  {
    nameResult = new JLabel () ;
    nameResult.setForeground(Color.black);
    timeResult = new JLabel ( );
    timeResult.setForeground(Color.black);
    criticalResult = new JLabel () ;
    criticalResult.setForeground(Color.black);
    eeResult = new JLabel () ;
    eeResult.setForeground(Color.black);
    leResult = new JLabel () ;
    leResult.setForeground(Color.black);
    delay = new JLabel ();
    delay.setForeground(Color.black);
    timeField = new JTextField() ;
    timeField.setForeground(Color.black);
    timeField.setColumns(3);
    setTime = new JButton ("SetTime" );
    setTime.addActionListener( new SetTimeListener());

    setLabel();

    information = new JPanel(new BorderLayout());
    information.setLayout( new GridBagLayout());

    c = new GridBagConstraints();
    c.anchor = GridBagConstraints.CENTER;
    c.gridwidth = 2;

    c.gridy = 0 ;

    information.add(nameResult , c  );
    c.gridy  = 1;
    c.gridwidth = 1;
    information.add(timeResult , c )  ;
    c.gridx = 1;
    c.anchor = GridBagConstraints.WEST;
    information.add(timeField , c);
    c.gridx = 2;
    information.add(setTime, c) ;
    c.anchor = GridBagConstraints.CENTER;
    c.gridx = 0 ;
    c.gridy = 2 ;
    c.gridwidth = 2 ;
    information.add(criticalResult , c);
    c.gridy = 3 ;
    information.add(eeResult , c) ;
    c.gridy = 4 ;
    information.add(leResult , c)  ;
    c.gridy = 5 ;
    information.add(delay , c )  ;


    iDvertice = 0;
    iDedge = 0 ;
    aoe = new AOEManager();

    canvas = new CanvasRect();
    this.setLayout(new BorderLayout());
    this.add(canvas , BorderLayout.CENTER);
    this.add(information , BorderLayout.SOUTH)  ;

    repaint();





  }

  public void paintComponent ( Graphics g )
  {

    super.paintComponent(g);
    canvas.repaint();
    information.repaint();
  }


  private class CanvasRect extends Canvas
  {
    private int CANVAS_WIDTH = 600;
    private int CANVAS_HEIGHT = 400;



  // Two vertex that determine an Edge
  // If one of them is null the Edge will not be created
    private Vertex v1 ;
    private Vertex v2 ;


  // Constructor
  // add a mouse listener
  public CanvasRect()
  {
    addMouseListener(new MouseKeeper());
    setBackground(Color.white);
    setSize(CANVAS_WIDTH, CANVAS_HEIGHT);

    v1 = null;
    v2 = null;
    anEdge = null;

    repaint();

  }

  public void paint( Graphics g )
  {
      aoe.draw(g);
  }

  // this class add the vertices to the vector on the click
  // create an edge on the mouse pressed and released if the press and release
  // is inside a vertice
  // connect the graph on the back screen
  private class MouseKeeper extends MouseAdapter
  {
    public void mouseClicked ( MouseEvent e )
    {
      if ( active )
      {

        if ( EventChoice )
        {
        aoe.addVertex(e.getX(),e.getY(),iDvertice );
        iDvertice++;
        repaint();
        }
     }
     else
     {
      v1 = aoe.findVertex(e.getX(), e.getY()) ;
      if ( v1 != null )
      {
        setResult ( v1 );
        anEdge = null ;
        return ;
      }
      anEdge = aoe.findEdge( e.getPoint() );
      if ( anEdge != null )
        setResult ( anEdge);
     }
    }

    public void mousePressed ( MouseEvent e )
    {
      if ( active )
        if ( !EventChoice)
        {
          v1 = aoe.findVertex(e.getX(),e.getY());


        }
    }

    public void mouseReleased(MouseEvent e )
    {
      if ( active )
        if ( !EventChoice )
        {

          v2 = aoe.findVertex(e.getX(), e.getY());
          if (v1 != null && v2 != null )
          {
            aoe.addEdge(v1,v2, timeActivity,iDedge)  ;
            iDedge ++ ;
          }
        repaint();
        }
    }
  }

  }
  public void setVertice()
  {
    EventChoice = true;

  }

  // set the choice of the drawing between the vertice and the edge to edge
  public void setEdge()
  {
    EventChoice = false;
  }


  public void criticalPath  ()
  {
      setInactive();
      aoe.criticalPath();
      repaint();
  }



  private void setInactive ()
  {
    active = false ;

  }

  public void setTimeActivity ( int   time )
  {
    timeActivity = time ;
  }

  public void clear ()
  {
    aoe = new AOEManager();
    active = true ;
    iDvertice = 0 ;
    iDedge = 0 ;
    setLabel();
    repaint ();
  }

  private void setResult ( Vertex v  )
  {
     nameResult.setText(  "Name: " +  v.toString() ) ;
     criticalResult.setText( "Critical: " + v.getCritical()) ;
     eeResult.setText( "Early Start Time: " + v.getEarliestEvent() );
     leResult.setText( "Latest Start Time: " + v.getLatestEvent() ) ;


      timeField.setText( "NA") ;
      delay.setText("Delay: NA" );
    repaint ();
  }

  private void setResult ( Edge e  )
  {
     nameResult.setText(  "Name: " +  e.toString() ) ;
     criticalResult.setText( "Critical: " + e.getCritical()) ;
     eeResult.setText( "Early Start Time: " + e.getEarliestEvent()  );
     leResult.setText( "Latest Start Time: " + e.getLatestEvent() ) ;


      timeField.setText( "" + e.getTime() )  ;
      delay.setText( "Delay: " + (e.getLatestEvent() - e.getEarliestEvent()))  ;


    repaint ();
  }

  private void setLabel ()
  {
     nameResult.setText("Name: NA") ;
     timeResult.setText("Time: " );
     timeField.setText("NA");
     criticalResult.setText("Critical: NA") ;
     eeResult.setText("Earliest Start Time: NA") ;
     leResult.setText("Latest Start Time: NA") ;
     delay.setText("Delay: NA");
  }

  private class SetTimeListener implements ActionListener
  {
      int time ;
      public void actionPerformed ( ActionEvent event )
      {
        if ( anEdge!= null && !active )
        {


          try
          {
            String userInput =  timeField.getText();
            time = Integer.parseInt(userInput);
            anEdge.setTime(time);
            aoe.resetEarlyLateTime();
            setLabel();
            canvas.repaint();

          }

          catch ( NumberFormatException e )
          {
          }



        }



      }

  }

}

