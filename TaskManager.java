/*********************************************************************
Project: Task Manager

*********************************************************************/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TaskManager extends JFrame
{

  private JRadioButton iVertex;
  private JRadioButton iEdge;

  private ButtonGroup insertion;

  private JLabel chooseTimeLabel ;
  private JTextField chooseTimeText ;
  private JButton chooseTimeButton ;


  private GridBagConstraints c;

  private JPanel choicepannel ;
  private JPanel actionpannel ;
  private JPanel information ;
  private JPanel controlpannel ;
  private JPanel app ;

  private JButton criticalPath ;

  private JButton clearGraph ;
  private JButton quit ;

  private Container container;
  // Set up the Canvas
  private CanvasResult  canvas;

  private static int initialTime = 5 ;    // Initail Time for an activity

  // Set up a window listener
  // initialize the components
  // add action events to them

  public TaskManager()
  {

     super ( "Task Manager");
     this.setSize(600,500);

     addWindowListener(
        new WindowAdapter() {
            public void windowClosing (WindowEvent e)
            {
                System.exit(0);
            }
        }
        );

    iEdge = new JRadioButton("Insert Activity",false);
    iVertex = new JRadioButton("Insert Event",true);

    insertion = new ButtonGroup();
    insertion.add(iVertex);
    insertion.add(iEdge);

    choicepannel = new JPanel(new BorderLayout());
    choicepannel.setLayout( new GridBagLayout());

    c = new GridBagConstraints();

    c.anchor = GridBagConstraints.NORTHWEST;
    c.insets = new Insets ( 2,5,2,5 );
    c.fill = GridBagConstraints.NONE;

    c.gridx = 0;
    c.gridy = 0 ;
    c.weightx = 0;
    choicepannel.add(iVertex,c);

    c.gridx = 0;
    c.gridy = 1 ;
    choicepannel.add(iEdge,c);

    chooseTimeLabel = new JLabel ( "Choose Time " );
    chooseTimeLabel.setForeground(Color.black);
    chooseTimeText = new JTextField("5" , 5 );
    chooseTimeButton = new JButton ("Set Time");

    c.gridx = 0;
    c.gridy = 2 ;
    c.insets = new Insets ( 2,30,2,5 );
    choicepannel.add(chooseTimeLabel,c);

    c.gridx = 1;
    c.gridy = 2 ;
    c.insets = new Insets ( 2,5,2,5 );
    c.weightx = 1 ;
    choicepannel.add(chooseTimeText,c);

    c.gridx = 0;
    c.gridy = 3 ;
    c.weightx = 0 ;
    c.insets = new Insets ( 2,30,2,5 );
    choicepannel.add(chooseTimeButton,c);

    choicepannel.setBorder(BorderFactory.createLineBorder(Color.black));

    criticalPath = new JButton ("Critical Path") ;


    actionpannel = new JPanel(new BorderLayout());
    actionpannel.setLayout( new GridBagLayout());

    c = new GridBagConstraints();

    c.anchor = GridBagConstraints.NORTHWEST;
    c.insets = new Insets ( 2,5,2,5 );
    c.fill = GridBagConstraints.NONE;

    c.gridx = 0;
    c.gridy = 0 ;
    actionpannel.add(criticalPath , c);

    quit = new JButton ("Quit") ;
    clearGraph = new JButton ("Clear Graph");

    controlpannel = new JPanel(new BorderLayout());
    controlpannel.setLayout( new GridBagLayout());

    c = new GridBagConstraints();

    c.anchor = GridBagConstraints.NORTHWEST;
    c.insets = new Insets ( 2,5,2,5 );
    c.fill = GridBagConstraints.NONE;

    c.gridx = 0;
    c.gridy = 0 ;
    c.weightx = 1;
    controlpannel.add(clearGraph,c);

    c.gridy = 1 ;
    controlpannel.add(quit , c);


    app = new JPanel(new BorderLayout());
    app.setLayout( new GridBagLayout());

    c = new GridBagConstraints();

    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets ( 30,5,2,5 );
    c.fill = GridBagConstraints.NONE;

    c.gridx = 0;
    c.gridy = 0 ;
    c.weightx = 1;

    app.add(choicepannel , c );

    c.weighty = 0.5;
    c.gridy = 1 ;
    c.gridheight = 2 ;
    app.add(actionpannel , c);

    c.weighty = 0.5;
    c.gridy = 3 ;
    c.gridheight = 1;
    app.add(controlpannel , c);

    canvas = new CanvasResult();
    canvas.setTimeActivity(initialTime);




    iVertex.addActionListener(new theChoice());
    iEdge.addActionListener(new theChoice());

    criticalPath.addActionListener(new theChoice());
    chooseTimeButton.addActionListener(new theChoice());
    quit.addActionListener(new theChoice());
    clearGraph.addActionListener(new theChoice());




    container = this.getContentPane();
    container.setLayout(new BorderLayout());
    container.add(app,BorderLayout.WEST);
    container.add(canvas,BorderLayout.CENTER);

    show();
  }



  public static void main ( String [] args )
  {
    TaskManager taskmanager = new TaskManager() ;
  }

  // class that detect the action of the user and fires the appropiate
  // action
  private class theChoice implements ActionListener
  {
    public void actionPerformed ( ActionEvent event )
    {
      Object source = event.getSource();
      if ( source == iVertex)
        canvas.setVertice();
      if ( source == iEdge)
        canvas.setEdge();
      if ( source ==   chooseTimeButton )
        try
        {
          String userInput =  chooseTimeText.getText();
          int element = Integer.parseInt(userInput);
          canvas.setTimeActivity(element);

        }

        catch ( NumberFormatException e )
        {
        }
      if ( source == criticalPath)
      {
        canvas.criticalPath();
      }

      if ( source == quit )
          System.exit(0);
      if ( source == clearGraph )
          canvas.clear();

    }
  }
}



