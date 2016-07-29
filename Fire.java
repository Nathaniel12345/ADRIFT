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

public class Fire extends Magic
{
    private Animation a;
    private static double x;
    private static double y;
    private BufferedImage image;
    private boolean visible;
    private static int width;
    private static int height;
    private final float speed = .4f;
    private ArrayList <Fireball> fireball;
    public int select = 4;
    public boolean fired = false;
    public int fireLevel = 0;
    public int exp = 0;
    private static int fireDelay = 350;
    private double scaleX = 0.7;
    private double scaleY = 0.7;
    private static int centerX;
    private static int centerY;
    private double scale;
    private double rotate;
    private long currentTime;//actual time, under the influence of the updating time
    private long tempTime;//keeps track of the previous timePassed
    private AffineTransform txBounds;

    public Fire( )
    {
        a = new Animation();
        try
        {
            image = ImageIO.read(new File("res/weapons/staff/staff.png"));
            /*ImageIcon ii = new ImageIcon( this.getClass().getResource( "res/weapons/gun/guns/gun.png" ) );
            image = ii.getImage();
            ImageIcon ii2 = new ImageIcon( this.getClass().getResource( "res/weapons/gun/guns/gun.png" ) );
            image2 = ii2.getImage();*/
        }catch(Exception ex){System.out.println("cant load fire");}
        a.addScene(image, 2000);
        //a.addScene(image2, 2000);
        visible = true;
        width = image.getWidth( null );
        height = image.getHeight( null );
        this.x = (double)x;
        this.y = (double)y;
        scale = 0.7;
        rotate = 0;
        tempTime = 0;
        currentTime = 0;
        fireball = new ArrayList();
        x=0;
        y=0;
        centerX = width/2;
        centerY = height/2;
    }
    
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        for ( int i = 0; i < fireball.size(); i++ )
        {
            Fireball b = ( Fireball ) fireball.get( i );
            if ( b.isVisible() && b != null  )
            {
                b.render(g);
            }
            else
            {
                fireball.remove( i );
            }
        }
        
        AffineTransform tx = new AffineTransform();
        tx.translate(x - Main.focusX, y - Main.focusY);
        tx.rotate(rotate + Math.PI, centerX, centerY);
        tx.scale(scale, scale);
        txBounds = tx;
        if(Main.scroll == select)
        {
            g.drawImage(a.getImage(), tx, null);
        }
    }
    
    public void update(long timePassed)
    {
        a.update(timePassed);
        
        x = Main.sprite.getX()+Main.sprite.getWidth()-4;
        y = Main.sprite.getY()+5;
        
        currentTime += timePassed - tempTime;
        tempTime = timePassed;//fireTimer shoots correct # of fireballs, just not at the correct distance away
                  
        for ( int i = 0; i < fireball.size(); i++ )
        {
            Fireball b = ( Fireball ) fireball.get( i );
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
                        s.life -= 16;
                        if(s.life <= 0)
                        {
                            s.setVisible( false );
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
                fireball.remove( i );
            }
        }
        rotate = Math.atan2(centerY - Main.mousePosY + y - Main.focusY, centerX - Main.mousePosX + x - Main.focusX);
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
    public int getDelay()
    {
        return fireDelay;
    }
    public int getLevel()
    {
        return fireLevel;
    }
    public int getEXP()
    {
        return exp;
    }
    public void levelUp()
    {
        fireLevel++;
    }
    public void addEXP(int exp)
    {
        this.exp += exp;
    }
    public int getExp()
    {
        return exp;
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
        Rectangle r = new Rectangle((int)x, (int)y, width, height);
        return r;
        //Area a = new Area();
        //a.createTransformedArea(txBounds);
        //return a;
    }

    public ArrayList getFireball()
    {
        return fireball;
    }
    public void fireFireball()
    {
        fireball.add( new Fireball( (int)x, (int)y, rotate ) );
    }
}