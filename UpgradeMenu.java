import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;
import java.awt.geom.AffineTransform;

public class UpgradeMenu extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
    private float x = 0;
    private float y = 0;
    private static float translateX=0;
	private static float translateY=0;
	private float lastOffsetX;
	private float lastOffsetY;
	private boolean clicked = false;
	private boolean visible = true;
	private BufferedImage image;
    
    public void make()
    {
        try
        {
            image = ImageIO.read(new File("res/upgrade_menu.png"));
        }catch(Exception ex){System.out.println("cant load upgrademenu");}
        //x = translateX;
        //y = translateY;
        //setBounds((int)x, (int)y, image.getWidth(null), image.getHeight(null));
        //setDoubleBuffered(true);
        visible = true;
    }
    public void update(long timePassed)
    {
        setBounds((int)x, (int)y, image.getWidth(null), image.getHeight(null));
        if(visible)
        {
            x = translateX;
    		y = translateY;
        }
    }
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AffineTransform at = new AffineTransform();
        at.translate(x, y);
        if(visible)
        {
            g.drawImage(image, at, null);
            g.drawRect((int)x, (int)y, image.getWidth(null), image.getHeight(null));
        }
    }

    public void mousePressed(MouseEvent e)
    {
        if(visible)
        {
            if(e.getX()>=x && e.getX()<=x+image.getWidth(null) && e.getY()>=y && e.getY()<=y+image.getHeight(null))
            {
                lastOffsetX = e.getX();
                lastOffsetY = e.getY();
                clicked = true;
            }
        }
    }

	public void mouseDragged(MouseEvent e)
	{
	    if(visible)
        {
            if(clicked)
            {
                float newX = e.getX() - lastOffsetX;
                float newY = e.getY() - lastOffsetY;
                
                lastOffsetX += newX;
                lastOffsetY += newY;
                
                translateX += newX;
                translateY += newY;
            }
        }
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {clicked = false;}
	
	public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        boolean toggle = true;
        
        if(keyCode == KeyEvent.VK_I && visible && toggle)
        {
            visible = false;
            toggle = false;
        }
        if(keyCode == KeyEvent.VK_I && !visible && toggle)
        {
            make();
            toggle = false;
        }
    }
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
    }
    public void keyTyped(KeyEvent e)
    {
         e.consume();
    }
}
