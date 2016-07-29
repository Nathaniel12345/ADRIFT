import java.awt.Image;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class Gun
{
    Animation a;
    float x;
    float y;
    boolean visible;
    int width;
    int height;
    double scaleX;
    double scaleY;
    int centerX;
    int centerY;
    boolean selected;
    int delay;
    
    public void update(long timePassed)
    {
        a.update(timePassed);
        
        width = a.getImage().getWidth( null );
        height = a.getImage().getHeight( null );
    }
    
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }
    
    public void selected(boolean tf)
    {
        selected = tf;
    }

    public float getX()
    {
        return (float)x;
    }
    public float getY()
    {
        return (float)y;
    }

    public boolean isVisible()
    {
        return visible;
    }
    public void setVisible( boolean visible )
    {
        this.visible = visible;
    }
    public int getDelay()
    {
        return delay;
    }

    //public Area getBounds()
    //{
    //    return new Area( (int)x, (int)y, width, height );
    //}
}