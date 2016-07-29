import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Area;
import javax.swing.ImageIcon;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Missile
{
    private Animation a;
    private double x;
    private double y;
    private double x2;
    private double y2;
    private BufferedImage image;
    private boolean visible;
    private int width;
    private int height;
    private double currentSpeed = 3;
    private double speedIncrement = 3;
    private double rotate;
    private double scale = 1;
    private static int centerX;
    private static int centerY;
    //private Timer moveTimer;
    //private static int moveDelay = 2;
    private long currentTime;//actual time, under the influence of the updating time
    private long tempTime;//keeps track of the previous timePassed
    private AffineTransform txBounds;

    public Missile( float x, float y)
    {
        a = new Animation();
        try
        {
            image = ImageIO.read(new File("res/weapons/gun/bullets/bullet.png"));
            /*ImageIcon ii = new ImageIcon( this.getClass().getResource( "res/weapons/gun/bullets/bullet.png" ) );
            image = ii.getImage();*/
        }
        catch(Exception ex){}
        a.addScene(image, 250);
        visible = true;
        this.x = x;
        this.y = y;
        
        //moveTimer = new Timer(moveDelay, new MoveTimer());
        //moveTimer.start();
    }

    public Image getImage()
    {
        return image;
    }

    public double getX()
    {
        return x;
    }
    public double getY()
    {
        return y;
    }
    
    public void setX(double nx)
    {
        x = nx;
    }
    public void setY(double ny)
    {
        y = ny;
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

    public void setVisible( Boolean visible )
    {
        this.visible = visible;
    }

    public Rectangle getBounds()
    {
        Rectangle r = new Rectangle((int)x2, (int)y2, width, height);
        return r;
        //Area a = new Area();
        //a.createTransformedArea(txBounds);
        //return a;
    }
    
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        AffineTransform tx = new AffineTransform();
        
        tx.translate(x2, y2);
        tx.rotate(rotate-Math.PI, centerX, centerY);
        tx.scale(scale, scale);
        txBounds = tx;
        if(isVisible())
        {
            g.drawImage(a.getImage(), tx, null);
        }
    }
    
    public void update(long timePassed)
    {
        a.update(timePassed);
        width = a.getImage().getWidth( null );
        height = a.getImage().getHeight( null );
        centerX = width/2;
        centerY = height/2;
        currentTime += timePassed - tempTime;
        tempTime = timePassed;
        
        rotate = Math.atan2(centerY - Main.mousePosY + y2, centerX - Main.mousePosX + x2);
        x2 = x + currentSpeed*Math.cos(rotate-Math.PI)-Main.focusX;
        y2 = y+(currentSpeed*Math.sin(rotate-Math.PI)) - Main.focusY;
        currentSpeed+=speedIncrement;

        /*for ( int j = 0; j<Main.tileMap.size(); j++ )
        {
            Tile t = (Tile) Main.tileMap.get( j );
            Rectangle tr = t.getBounds();

            if ( this.getBounds().intersects( tr ) )
            {
                this.setVisible(false);
            }
        }*/
    }
}
    