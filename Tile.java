import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.lang.*;
import java.awt.geom.*;

public class Tile extends Block
{
    private float x, y;
    private boolean visible;
    private int width;
    private int height;
    private double scaleX, scaleY;
    private float speedRight;
    private float speedLeft;
    private float speedUp;
    private float speedDown;
    private double rotate = Math.toRadians(45.0);
    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;
    private Animation a;
    private AffineTransform at;

    public Tile(float x, float y)
    {
        a = new Animation();
        visible = true;
        
        this.x = x;
        this.y = y;
        speedRight = 0;
        speedLeft = 0;
        speedUp = 0;
        speedDown = 0;
    }

    public Image getImage()
    {
        return a.getImage();
    }
    public void setImage(BufferedImage i)
    {
        a.addScene(i, 200);
        width = a.getImage().getWidth( null );
        height = a.getImage().getHeight( null );
    }

    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
    public void setX(float x)
    {
        this.x = x;
    }
    public void moveRight(float speed)
    {
        speedRight = speed;
    }
    public void addRight(float speed)
    {
        speedRight += speed;
    }
    public void moveLeft(float speed)
    {
        speedLeft = speed;
    }
    public void addLeft(float speed)
    {
        speedLeft += speed;
    }
    public void moveUp(float speed)
    {
        speedUp = speed;
    }
    public void addUp(float speed)
    {
        speedUp += speed;
    }
    public void moveDown(float speed)
    {
        speedDown = speed;
    }
    public void addDown(float speed)
    {
        speedDown += speed;
    }
    
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    
    public void update(long timePassed)
    {
        if(this.isVisible())
        {
            super.update(timePassed);
            a.update(timePassed);
            width = a.getImage().getWidth(null);
            height = a.getImage().getHeight(null);
            scaleX = (double)tileWidth/(double)getWidth();
            scaleY = (double)tileHeight/(double)getHeight();
            setX(getX() + speedRight - speedLeft);
            setY(getY() + speedDown - speedUp);
        }
    }
    public void render(Graphics2D g)
    {
        if (this.isVisible())
        {
            super.render(g);
            at = new AffineTransform();
            at.translate((int)this.getX()-Main.focusX, (int)this.getY()-Main.focusY);
            at.scale(scaleX, scaleY);
            //at.rotate(rotate, getWidth()/2, getHeight()/2);
            //rotate += .5;
            g.drawImage(a.getImage(), at, null);
        }
    }

    public Rectangle getBounds()
    {
        return new Rectangle( (int)x, (int)y, (int)Math.round(width*scaleX), (int)Math.round(height*scaleY) );
    }
}