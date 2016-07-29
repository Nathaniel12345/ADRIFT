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

public class Block
{
    private float x, y;
    private boolean visible;
    public static int tileWidth = 32;
    public static int tileHeight = 32;

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
    
    public void update(long timePassed)
    {
        //tileWidth = (int)(30 + (Main.WIDTH/1000));
        //tileHeight = (int)(30 + (Main.HEIGHT/1000));
    }
    public void render(Graphics2D g)
    {
        
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible( Boolean visible )
    {
        this.visible = visible;
    }
}