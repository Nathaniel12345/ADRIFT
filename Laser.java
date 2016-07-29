import java.awt.Image;
import java.awt.Rectangle;
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

public class Laser
{
    private Animation a;
    private static double x;
    private static double y;
    private BufferedImage image;
    private BufferedImage circle;
    private BufferedImage spark;
    private boolean visible;
    private static int width;
    private static int height;
    private final float speed = .4f;
    public int select = 1;
    public boolean fired = false;
    private static double scaleX = 0.7;
    private double scaleY = 0.4;
    private static int centerX;
    private static int centerY;
    private double tempX;
    private double tempY;
    public double rotate;
    public static boolean dragging = false;
    private double rotateCircle = 0;
    private double rotateSpark = 0;

    public Laser( )
    {
        a = new Animation();
        try
        {
            image = ImageIO.read(new File("res/weapons/magic/plasma/laser.png"));
            circle = ImageIO.read(new File("res/weapons/magic/plasma/circle.png"));
            spark = ImageIO.read(new File("res/weapons/magic/plasma/spark.png"));
            /*ImageIcon ii = new ImageIcon( this.getClass().getResource( "res/weapons/magic/plasma/laser.png" ) );
            image = ii.getImage();
            ImageIcon circleI = new ImageIcon( this.getClass().getResource( "res/weapons/magic/plasma/circle.png" ) );
            circle = circleI.getImage();
            ImageIcon sparkI = new ImageIcon( this.getClass().getResource( "res/weapons/magic/plasma/spark.png" ) );
            spark = sparkI.getImage();*/
        }catch(Exception ex){System.out.println("cant load gun");}
        a.addScene(image, 2000);
        //a.addScene(image2, 2000);
        
        this.x = (double)x;
        this.y = (double)y;
        rotate = 0;
        x=0;
        y=0;
    }
    
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        AffineTransform stx = new AffineTransform();
        
        if(!Main.pause)
        {
            float stxPosX = Main.mousePosX-spark.getWidth(null)/2;
            float stxPosY = Main.mousePosY-spark.getHeight(null)/2;
            stx.translate(stxPosX, stxPosY);
            rotateSpark+=Math.random()*15;
        }
            
        stx.rotate(rotateSpark, spark.getWidth(null)/2, spark.getHeight(null)/2);
        
        AffineTransform ctx = new AffineTransform();
        ctx.translate(x-5 - Main.focusX, y-2 - Main.focusY);
        ctx.rotate(rotateCircle, circle.getWidth(null)/2, circle.getHeight(null)/2);
        if(!Main.pause)
            rotateCircle+=1;
        
        AffineTransform tx = new AffineTransform();
        tx.translate(x - Main.focusX, y - Main.focusY);
        tx.rotate(rotate + Math.PI, centerX, centerY);
        //tx.scale(-scaleX*Math.cos(rotate), -scaleY);
        scaleX = Math.abs(scaleX);
        //scaleY = Math.abs(scaleY);
        tx.scale(scaleX, scaleY);
        
        if((Main.scroll == select && dragging == true))
        {
            g.drawImage(a.getImage(), tx, null);
            g.drawImage(circle, ctx, null);
            g.drawImage(spark, stx, null);
        }
    }
    
    public void update(long timePassed)
    {
        if(!Main.pause)
        {
            a.update(timePassed);
            width = a.getImage().getWidth( null );
            height = a.getImage().getHeight( null );
            x = Main.sprite.getX()+Main.sprite.getWidth();
            y = Main.sprite.getY()+4;
            centerX = 0;
            centerY = (int)((height*scaleY)/2);
            rotate = Math.atan2(centerY - Main.mousePosY + y - Main.focusY, centerX - Main.mousePosX + x - Main.focusX);
            scaleX = (Math.sqrt(Math.pow((Main.mousePosX-(x-Main.focusX)), 2) + Math.pow((Main.mousePosY-(y-Main.focusY)), 2)))/width;
        }
    }
    
    public Image getImage()
    {
        return a.getImage();
    }
    public double getDegrees()
    {
        return rotate;
    }
    public int getSelect()
    {
        return select;
    }

    public float getX()
    {
        return (float)x;
    }
    public float getY()
    {
        return (float)y;
    }
    
    public static float getWidth()
    {
        return width;
    }
    public static float getHeight()
    {
        return height;
    }

    public boolean isVisible()
    {
        return visible;
    }
    
    public void setVisible( Boolean visible )
    {
        this.visible = visible;
    }

    public Rectangle getBounds()
    {
        return new Rectangle( (int)x, (int)y, width, height );
    }
}