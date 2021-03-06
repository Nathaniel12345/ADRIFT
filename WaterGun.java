import java.awt.Image;
import java.awt.Rectangle;
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

public class WaterGun extends Gun
{
    public int gunLevel = 0;
    public int exp = 0;
    private Animation a;
    private BufferedImage image;
    private final float speed = .4f;
    private ArrayList <Water> water;
    private static int delay = 10;
    private double damage = 4;
    private double rotate;
    public int select = 3;
    public boolean fired = false;
    private boolean selected = false;
    private AffineTransform txBounds;

    public WaterGun( )
    {
        a = new Animation();
        try
        {
            image = ImageIO.read(new File("res/weapons/gun/guns/gun.png"));
        }
        catch(Exception ex)
        {System.out.println("cant load waterGun");}
        a.addScene(image, 2000);
        width = a.getImage().getWidth( null );
        height = a.getImage().getHeight( null );
        rotate = 0;
        water = new ArrayList();
        scaleX = 0.7;
        scaleY = 0.7;
        x=0;
        y=0;
        centerX = width/2 - 10;
        centerY = height/2;
        visible = true;
    }
    
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        for ( int i = 0; i < water.size(); i++ )
        {
            Water b = ( Water ) water.get( i );//something is wrong when too many
            if ( b.isVisible() && b != null )
            {
                b.render(g);
            }
            else
            {
                water.remove( i );
            }
        }
        AffineTransform tx = new AffineTransform();
        tx.translate(x - Main.focusX, y - Main.focusY);
        tx.rotate(rotate + Math.PI, centerX, centerY);
        tx.scale(scaleX, scaleY);
        txBounds = tx;
        if(Main.scroll == select)
        {
            g.drawImage(a.getImage(), tx, null);
        }
    }
    
    public void update(long timePassed)
    {
        a.update(timePassed);
        
        x = (float)Math.floor(Main.sprite.getX());
        y = (float)Math.floor(Main.sprite.getY());
        
        /*if(fired && Main.scroll == 2)// && currentTime <= waterGunDelay)//not correct code
        {
            waterGunTimer.start();
        }
        else if(!fired || Main.scroll != 2)
        {
            currentTime = 0;
            waterGunTimer.stop();
        }*/
                  
        for ( int i = 0; i < water.size(); i++ )
        {
            Water b = ( Water ) water.get( i );
            Rectangle r1 = b.getBounds();
            
            for ( int j = 0; j<Main.enemyList.size(); j++ )
            {
                Remnant r = ( Remnant ) Main.enemyList.get( j );
                if(r instanceof Seeker)
                {
                    Seeker s = (Seeker)r;
                    Rectangle r2 = s.getBounds();
                
                    if ( r1.intersects( r2 ) )
                    {
                        b.setVisible( false );
                        s.life -= 0.2;
                        if(s.life <= 0)
                        {
                            s.setVisible( false );
                            addEXP(40);
                        }
                    }
                }
            }
            
            if ( b.isVisible() )
            {
                b.update(timePassed);
            }
            else
            {
                water.remove( i );
            }
        }
        
        rotate = Math.atan2(centerY - Main.mousePosY + y - Main.focusY, centerX - Main.mousePosX + x - Main.focusX);
    }
    
    public Image getImage()
    {
        return a.getImage();
    }
    public int getDelay()
    {
        return delay;
    }
    public double getDegrees()
    {
        return rotate;
    }
    public void selected(boolean tf)
    {
        selected = tf;
    }
    public int getSelect()
    {
        return select;
    }
    public void setSelect(int s)
    {
        select = s;
    }

    public float getX()
    {
        return (float)x;
    }
    public float getY()
    {
        return (float)y;
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
    
    public void setVisible( boolean visible )
    {
        this.visible = visible;
    }

    public Rectangle getBounds()
    {
        //Area a = new Area();
        //a.createTransformedArea(txBounds);
        //a.transform(txBounds);
        //return a;
        Rectangle r = new Rectangle((int)x, (int)y, width, height);
        return r;
    }
    
    public void fire(boolean tf)
    {
        fired = tf;
    }
    public int getLevel()
    {
        return gunLevel;
    }
    public int getEXP()
    {
        return exp;
    }
    public void levelUp()
    {
        gunLevel++;
    }
    public void addEXP(int exp)
    {
        this.exp += exp;
    }
    public int getExp()
    {
        return exp;
    }

    public ArrayList getWater()
    {
        return water;
    }
    public void fireWaterGun()
    {
        try{
            water.add( new Water( (int)x + 15, (int)y + height/2 - 6, rotate ) );
            water.add( new Water( (int)x + 15, (int)y + height/2 - 6, rotate+Math.toRadians(3.5) ) );
            water.add( new Water( (int)x + 15, (int)y + height/2 - 6, rotate-Math.toRadians(3.5) ) );
        }catch(Exception ex){System.out.println("can't fire watergun");}
    }
}