import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;
import java.awt.geom.AffineTransform;

public class StatusBar extends JPanel
{
    private float x = 0;
    private float y = 0;
    private int width = Main.WIDTH*2/3;
    private int height = Main.HEIGHT/28;
    private double scaleX = 1;
    private double scaleY = 1;
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
            image = ImageIO.read(new File("res/status_bar.png"));
        }catch(Exception ex){System.out.println("cant load statusbar");}
        //x = translateX;
        //y = translateY;
        //setBounds((int)x, (int)y, image.getWidth(null), image.getHeight(null));
        //setDoubleBuffered(true);
        scaleX = width/((double)image.getWidth(null));
        scaleY = height/((double)image.getHeight(null));
        //System.out.println(image.getWidth(null) + "\n" + Main.WIDTH);
        visible = true;
    }
    public void update(long timePassed)
    {
        setBounds((int)x, (int)y, width, height);
        if(visible)
        {
            
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
        at.scale(scaleX, scaleY);
        if(visible)
        {
            g.drawImage(image, at, null);
            //g.drawRect((int)x, (int)y, width, image.getHeight(null));
        }
    }
}
