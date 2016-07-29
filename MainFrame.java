 

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame
{
  JPanel contentPane;
  JButton hourglassButton = new JButton();
  JButton normalButton = new JButton();
  Image image;
  /**Construct the frame*/
  public MainFrame()
  {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try
    {
      jbInit();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
  }
  /**Component initialization*/
  private void jbInit() throws Exception
  {
    hourglassButton.setText("Hourglass");
    hourglassButton.setBounds(new Rectangle(84, 120, 104, 27));
    hourglassButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        hourglassButton_actionPerformed(e);
      }
    });
    setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("res/sprites/16616.gif")));//this actually places an icon in the top left of window
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(null);
    this.setSize(new Dimension(400, 300));
    this.setTitle("Frame Title");
    normalButton.setText("Normal");
    normalButton.setBounds(new Rectangle(193, 120, 81, 27));
    normalButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        normalButton_actionPerformed(e);
      }
    });
    contentPane.add(normalButton, null);
    contentPane.add(hourglassButton, null);
  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e)
  {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING)
    {
      System.exit(0);
    }
  }
  
  void hourglassButton_actionPerformed(ActionEvent e)
  {
    Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);//WAIT, CROSSHAIR, CUSTOM, DEFAULT, HAND, MOVE, TEXT
    //setCursor(hourglassCursor);                             //E_RESIZE, N_RESIZE, NE, NW, S, SE, SW, W
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    image = toolkit.getImage("res\\3.gif");
  Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");//
  setCursor (c);
  }

  void normalButton_actionPerformed(ActionEvent e)
  {
    Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
    setCursor(normalCursor);
  }
}