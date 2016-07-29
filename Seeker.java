import java.awt.Image;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Area;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class Seeker extends Remnant
{
    private Animation a;
    private BufferedImage seeker;
    private BufferedImage seek01;
    private BufferedImage seek02;
    private BufferedImage seek03;
    private BufferedImage seek04;
    private BufferedImage seek05;
    private BufferedImage seek06;
    private float x;//make float
    private float y;//make float
    private static int centerX;
    private static int centerY;
    private int width;
    private int height;
    private double MAXLIFE = 100;
    public double life = MAXLIFE;
    private boolean visible;
    private double rotate;
    private AffineTransform txBounds;
    private HealthBar healthBar;
    
    public Seeker()
    {
        a = new Animation();
        healthBar = new HealthBar();
        visible = true;
        try{
            seeker = ImageIO.read(new File("res/remnants/seeker/seeker.png"));
            seek01 = seeker.getSubimage(0, 0, 29, 17);
            seek02 = seeker.getSubimage(29, 0, 29, 17);
            seek03 = seeker.getSubimage(58, 0, 29, 17);
            seek04 = seeker.getSubimage(87, 0, 29, 17);
            seek05 = seeker.getSubimage(116, 0, 29, 17);
            seek06 = seeker.getSubimage(145, 0, 29, 17);
        }
        catch(Exception ex){System.out.println("seekernotloaded");}
        
        a.addScene(seek01, 60);
        a.addScene(seek02, 60);
        a.addScene(seek03, 60);
        a.addScene(seek04, 150);
        a.addScene(seek05, 120);
        a.addScene(seek06, 120);
        
        x=0;
        y=0;
        
        rotate = 0;
    }
    
    public void update(long timePassed)
    {
        //x += vx * timePassed;
        //y += vy * timePassed;
        a.update(timePassed);
        width = a.getImage().getWidth(null);
        height = a.getImage().getHeight(null);
        centerX = width/2;
        centerY = height/2;
        healthBar.update(timePassed);
    }
    
    public void render(Graphics2D g)
    {
        AffineTransform tx = new AffineTransform();
        tx.translate(getX()-Main.focusX, getY()-Main.focusY);
        tx.rotate(rotate, centerX, centerY);
        txBounds = tx;
        g.drawImage(a.getImage(), tx, null);
        healthBar.render(g);
    }
    
    public float getX()//make float
    {
        return x;
    }
    public float getY()//make float
    {
        return y;
    }
    public void setX(float x)//make float
    {
        this.x = x;
    }
    public void setY(float y)//make float
    {
        this.y = y;
    }
    
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public boolean isVisible()
    {
        return visible;
    }
    public void setVisible(Boolean visible)
    {
        this.visible = visible;
    }
    
    public Image getImage()
    {
        return a.getImage();
    }
    
    public Rectangle getBounds()
    {
        Rectangle r = new Rectangle((int)(x-Main.focusX), (int)(y-Main.focusY), width, height);
        return r;
        //Area a = new Area();
        //a.createTransformedArea(txBounds);
        //a.transform(txBounds);
        //return a;
        //return new Area(Math.round(x-Main.focusX), Math.round(y-Main.focusY), Math.round(getWidth()), Math.round(getHeight()));
    }
    
    public class HealthBar
    {
        public int width;
        public int height;
        public double fill;
        public boolean disable;
        
        public HealthBar()
        {
            width = 30;
            height = 6;
            fill = 1;
            disable = false;
        }
        
        public void update(long timePassed)
        {
            fill=life/MAXLIFE * width;
        }
        public void render(Graphics2D g)
        {
            if(!disable && life<MAXLIFE)
            {
                g.setColor(Color.RED);
                g.fillRect((int)(getX()+2+(width-fill)-Main.focusX), (int)Math.floor((getY()+20-Main.focusY)), (int)fill, height);
                g.setColor(Color.BLACK);
                g.drawRect((int)(getX()+1-Main.focusX), (int)Math.floor((getY()+20-Main.focusY)), width, height);
            }
        }
    }
}