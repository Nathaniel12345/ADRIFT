import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class Sprite
{
    private Animation a;
    private float x;//make float
    private float y;//make float
    private float vx = 0;
    private float vy = 0;
    private float vxMax = 8f;
    private float vyMax = 8f;
    private float jumpSpeed = 6f;
    private float jsVar = jumpSpeed;//3f for 1 block jump, 4f for perfect 1 block jump, 
    /*private int delayWand = 700;
    private int delayStaff = 1000;
    private int delaySword = 750;
    private int delayBow = 560;
    private int delayGun = 70;
    private int delaySpecialist = 90;
    private ArrayList <Wand> wand;
    private ArrayList <Staff>staff;
    private ArrayList <Sword> sword;
    private ArrayList <Gun> gun;
    private ArrayList <Bow> bow;
    private ArrayList <Specialist> specialist;*/
    public static boolean moveRight = false;
    public static boolean moveLeft = false;
    public static boolean jumpUp = false;
    public static boolean jumpDown = false;
    public static boolean jumping = false;
    public static boolean startMoving = true;
    public static boolean falling = false;
    
    public static boolean wallJumpLeft = false;//
    public static boolean wallJumpRight = false;//
    public static boolean WallJump = false;//
    public static boolean dashRight = false;//
    public static boolean dashLeft = false;//
    
    /*public static boolean fireWand = false;
    public static boolean fireStaff = false;
    public static boolean fireSword = false;
    public static boolean fireGun = false;
    public static boolean fireBow = false;
    public static boolean fireSpecialist = false;
    private Timer wandTimer;
    private Timer staffTimer;
    private Timer bowTimer;
    private Timer gunTimer;
    private Timer swordTimer;
    private Timer specialistTimer;
    private static int wandDelay = 700;
    private static int staffDelay = 1000;
    private static int bowDelay = 560;
    private static int gunDelay = 20;
    private static int swordDelay = 750;
    private static int specialistDelay = 750;*/
    //private long timePassedAfterUpdate = 0;
    private static AffineTransform at;
    private BufferedImage charac;
    private long currentTimeL;//actual time, under the influence of the updating time
    private long currentTimeR;
    private long tempTimeL;//keeps track of the previous timePassed
    private long tempTimeR;
    private int prevKeyCode;
    
    public Sprite()
    {
        a = new Animation();
        try
        {
            charac = ImageIO.read(new File("res/character.png"));
        }catch(Exception ex){System.out.println("cant load sprite");}
        a.addScene(charac, 2000);
        //a.addScene(charac2, 2000);
        
        /*wand = new ArrayList();
        staff = new ArrayList();
        sword = new ArrayList();
        gun = new ArrayList();
        bow = new ArrayList();
        specialist = new ArrayList();
        
        wandTimer = new Timer(wandDelay, new DelayTimer());
        staffTimer = new Timer(staffDelay, new DelayTimer());
        bowTimer = new Timer(bowDelay, new DelayTimer());
        gunTimer = new Timer(gunDelay, new DelayTimer());
        swordTimer = new Timer(swordDelay, new DelayTimer());
        specialistTimer = new Timer(specialistDelay, new DelayTimer());*/
    }
    
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_D)//RIGHT)
        {
            moveRight = true;
            if(keyCode == prevKeyCode && currentTimeR <= 200)
            {
                dashRight = true;
            }
        }   else{}
        if(keyCode == KeyEvent.VK_A)//LEFT)
        {
            moveLeft = true;
            if(keyCode == prevKeyCode && currentTimeL <= 200)
            {
                dashLeft = true;
            }
        }   else{}
        if(keyCode == KeyEvent.VK_SPACE)//
        {
            jumping = true;
        }   else{e.consume();}
    }
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        
        if(keyCode == KeyEvent.VK_D)//RIGHT)
        {
            moveRight = false;
            currentTimeR = 0;
        }   else if(keyCode != KeyEvent.VK_D && !jumping){dashRight = false;}
        if(keyCode == KeyEvent.VK_A)//LEFT)
        {
            moveLeft = false;
            currentTimeL = 0;
        }   else if(keyCode != KeyEvent.VK_A && !jumping){dashLeft = false;}
        if(keyCode == KeyEvent.VK_SPACE)//
        {
            jumping = false;
        }   else{e.consume();}
        prevKeyCode = e.getKeyCode();
    }
    
    public void stopMoving()
    {
        startMoving = false;
        jumping = false;
    }
    public void startMoving()
    {
        startMoving = true;
    }
    public void resetJump()
    {
        jsVar = jumpSpeed;
    }
    
    public void render(Graphics2D g)
    {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        at = new AffineTransform();
        //tx.translate(-sprite.getY()+focusY, sprite.getX()-focusX);
        at.translate(Math.floor(this.getX())-Main.focusX, Math.floor(this.getY())-Main.focusY);
        //at.rotate(Math.toRadians(90), this.getWidth() / 2, this.getHeight() / 2);
        g.drawImage(a.getImage(), at, null);
    }
    
    public void update(long timePassed)
    {
        a.update(timePassed);
        
        currentTimeL += timePassed - tempTimeL;
        tempTimeL = timePassed;
        currentTimeR += timePassed - tempTimeR;
        tempTimeR = timePassed;
        
        setVelocityY(getVelocityY()+Main.grav);
        
        /////////////////////////////////////////////////
        
        if(moveLeft && !dashLeft)
        {
            /*if(vx > -vxMax)
            {
                setVelocityX(-0.001f); //accelerates over time
            }
            else
            {
                setVelocityX(-vxMax); //accelerates over time
            }*/
            setVelocityX(-vxMax/2); //does not accelerate
        }
        else if(moveRight && !dashRight)
        {
            /*if(vx < vxMax)
            {
                setVelocityX(.001f); //accelerates over time
            }
            else
            {
                setVelocityX(vxMax); //accelerates over time
            }*/
            
            setVelocityX(vxMax/2); //does not accelerate
            /*if(timePassed - timePassedAfterUpdate < 0.5f)
            {
                setVelocityX(0.1f);
            }*/
        }
        else if(moveRight && dashRight)
        {
            setVelocityX(vxMax);
        }
        else if(moveLeft && dashLeft)
        {
            setVelocityX(-vxMax);
        }
        else if(!moveRight && !moveLeft)
        {
            dashRight = false;
            dashLeft = false;
            setVelocityX(0);
        }
        else
        {
            
        }
        
        
        
        /////////////////////////////place timers for the inputs
        Rectangle charRect = new Rectangle(Math.round(getX()), Math.round(getY()), getWidth(), getHeight());
        Rectangle rightRect = new Rectangle(Math.round(getX() + getWidth()), Math.round(getY() + getHeight()/2 - 4), 1, 8);//just in case it hits ground
        Rectangle leftRect = new Rectangle(Math.round(getX()-10), Math.round(getY()+getHeight()/2-4), 1, 8);
        Rectangle topRect = new Rectangle(Math.round(getX() + getWidth()/2-2), Math.round(getY()), 4, 1);
        Rectangle bottomRect = new Rectangle(Math.round(getX() + getWidth()/2 -7), Math.round(getY() + getHeight()), 14, 1);
        
        for ( int j = 0; j<Main.tileMap.size(); j++ )
        {
            Tile b = ( Tile ) Main.tileMap.get( j );
            Rectangle r = b.getBounds();
            
            if ( bottomRect.intersects( r ) && jumpDown )
            {
                setY( b.getY() - getHeight() );
                jumping = false;
                falling = false;
                setVelocityY(0);
                resetJump();
				//Main.mess = "bottom";
            }
            if ( topRect.intersects( r ) && jumpUp )
            {
                //setY( b.getY() + b.getHeight() + 8 );
                //jumpUp = false;
                //jumpDown = true;
                //jumping = false;
                //setVelocityY(0);
				//Main.mess = "top";
            }
            /*if ( topRect.intersects( r ) && jumpUp && moveRight )
            {
                setY( b.getY() + b.getHeight() );
                jumping = false;
                Main.grav = 1;
            }
            if ( topRect.intersects( r ) && jumpUp && moveLeft )
            {
                setY( b.getY() + b.getHeight() );
                jumping = false;
                Main.grav = 1;
            }*/
          
            if ( ( bottomRect.intersects( r ) && !jumping && !moveRight ) || ( bottomRect.intersects( r ) && !jumping && !moveLeft ) )
            {
                setY( b.getY() - getHeight()-1 );
                setVelocityY(0);
                resetJump();
				//Main.mess = "bottom2";
            }
            
            if ( moveLeft && leftRect.intersects( r ) && !jumping )
            {
                setX( r.x + b.tileWidth );
				//Main.mess = "left";
            }
            if ( moveRight && rightRect.intersects( r ) && !jumping )
            {
                setX( r.x - getWidth()-1 );
				//Main.mess = "right";
            }
            
            if ( leftRect.intersects( r ) && jumping && moveLeft)// && !wallJumpLeft )
            {
                setX( r.x + b.tileWidth );
                //WallJump = false;
                //stopMoving();
                //Main.grav = 0;
                //wallJumpLeft = true;
				//Main.mess = "leftJump";
            }
            if ( rightRect.intersects( r ) && jumping && moveRight)// && !wallJumpRight )
            {
                setX( r.x - getWidth() );
                //WallJump = false;
                //stopMoving();
                //Main.grav = 0;
                //wallJumpRight = true;
				//Main.mess = "rightJump";
            }
            if(!charRect.intersects(r) && !jumping)
            {
                falling = true;
                jumping = false;
            }
            else
            {
                falling = false;
            }
        }
        
        /*if ( wallJumpLeft && moveRight && WallJump )
        {
            wallJumpLeft = false;
            Main.grav = 1;
            jumping = true;
            startMoving();
        }
        if ( wallJumpRight && moveLeft && WallJump )
        {
            wallJumpRight = false;
            Main.grav = 1;
            jumping = true;
            startMoving();
        }
        if ( wallJumpRight && moveLeft)
        {
            Main.grav = 1;
            wallJumpRight = false;
            startMoving();
        }
        if ( wallJumpLeft && moveRight )
        {
            Main.grav = 1;
            wallJumpLeft = false;
            startMoving();
        }
        if( wallJumpLeft && WallJump || wallJumpRight && WallJump )
        {
            WallJump = false;
            Main.grav = 1;
        }*/
        
        
        if(jumping && !falling)
        {
            if ( getVelocityY() <= 0 )
            {
                jumpUp = true;
                jumpDown = false;
            }
            else if ( getVelocityY() >= 0 )
            {
                jumpUp = false;
                jumpDown = true;
            }
            else
            {
                setVelocityY(0);
                resetJump();
            }
            setVelocityY(getVelocityY() - jsVar + Main.grav);
            
            if(getVelocityY() <= -15f)
            {
                setVelocityY(-15f);
            }
            jsVar = 0;
        }
        else
        {
            jumpUp = false;
            jumpDown = false;
        }
        if ( !jumping && !moveRight || !jumping && !moveLeft )
        {
            setVelocityY(getVelocityY() + Main.grav);
            if(getVelocityY() <= -15f)
            {
                setVelocityY(-15f);
            }
        }
        
        //////////////////////////////
        setX(getX()+getVelocityX());
        setY(getY()+getVelocityY());
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
        return a.getImage().getWidth(null);
    }
    public int getHeight()
    {
        return a.getImage().getHeight(null);
    }
    
    public float getVelocityX()//make float
    {
        return vx;
    }
    public float getVelocityY()//make float
    {
        return vy;
    }
    public void setVelocityX(float x)//make float
    {
        this.vx = x;
    }
    public void setVelocityY(float y)//make float
    {
        this.vy = y;
    }
    
    public Image getImage()
    {
        return a.getImage();
    }
    
    /*public ArrayList getWand()
    {
        return wand;
    }
    public ArrayList getStaff()
    {
        return staff;
    }
    public ArrayList getSword()
    {
        return sword;
    }
    public ArrayList getSpecialist()
    {
        return specialist;
    }
    public ArrayList getGun()
    {
        return gun;
    }
    public ArrayList getBow()
    {
        return bow;
    }
    
     public void fireWand()
    {
        wand.add( new Wand( (int)x + a.getImage().getWidth(null), (int)y + a.getImage().getHeight(null)/2 ) );
    }
    public void fireStaff()
    {
        staff.add( new Staff( (int)x + a.getImage().getWidth(null), (int)y + a.getImage().getHeight(null)/2 ) );
    }
    public void fireSword()
    {
        sword.add( new Sword( (int)x + a.getImage().getWidth(null), (int)y ) );
    }
    public void fireSpecialist()
    {
        specialist.add( new Specialist( (int)x + a.getImage().getWidth(null), (int)y + a.getImage().getHeight(null)/2 ) );
    }
    public void fireGun()
    {
        gun.add( new Gun( (int)x + a.getImage().getWidth(null), (int)y + a.getImage().getHeight(null)/2 ) );
    }
    public void fireBow()
    {
        bow.add( new Bow( (int)x + a.getImage().getWidth(null), (int)y + a.getImage().getHeight(null)/2 ) );
    }
    
    public class DelayTimer implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(fireWand)
            {
                fireWand();
            }
            if(fireStaff)
            {
                fireStaff();
            }
            if(fireBow)
            {
                fireBow();
            }
            if(fireGun)
            {
                fireGun();
            }
            if(fireSword)
            {
                fireSword();
            }
            if(fireSpecialist)
            {
                fireSpecialist();
            }
        }
    }*/
}