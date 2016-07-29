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

public class Fireball
{
    private Animation a;
    private double x;
    private double y;
    private double x2;
    private double y2;
    private double grav = 1;
    private BufferedImage image;
    private boolean visible;
    private int width;
    private int height;
    private double currentSpeed = 10;
    private double speedIncrement = 7;
    private double rotate;
    private double scale = 1;
    //private Timer moveTimer;
    //private static int moveDelay = 2;
    private long currentTime;//actual time, under the influence of the updating time
    private long tempTime;//keeps track of the previous timePassed
    private AffineTransform txBounds;

    public Fireball( float x, float y, double rotate )
    {
        a = new Animation();
        try
        {
            image = ImageIO.read(new File("res/weapons/magic/fire/fireball.png"));
            /*ImageIcon ii = new ImageIcon( this.getClass().getResource( "res/weapons/magic/fire/fireball.png" ) );
            image = ii.getImage();*/
        }
        catch(Exception ex){}
        a.addScene(image, 250);
        visible = true;
        this.x = x;
        this.y = y;
        this.rotate = rotate;
        
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
        x2 = x + currentSpeed*Math.cos(rotate-Math.PI)-Main.focusX;
        y2 = y+(currentSpeed*Math.sin(rotate-Math.PI)) - Main.focusY;
        tx.translate(x2, y2+grav);
        tx.rotate(rotate-Math.PI, width/2, height/2);
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
        currentTime += timePassed - tempTime;
        tempTime = timePassed;
        
        //grav += Main.agrav;
        //y += grav;
        currentSpeed+=speedIncrement;
        
        if(x2>=Main.WIDTH+200 || x2<=-200 || y2>=Main.HEIGHT+200 || y2<=-200)
        {
            this.setVisible(false);
        }

        /*for ( int j = 0; j<Main.tileMap.size(); j++ )
        {
            Tile t = (Tile) Main.tileMap.get( j );
            Area tr = t.getBounds();

            if ( this.getBounds().intersects( tr ) )
            {
                this.setVisible(false);
            }
        }*/
    }
    
    public class MoveTimer implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            currentSpeed += speedIncrement;
        }
    }
}