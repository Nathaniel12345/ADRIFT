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
//import java.util.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class StaffLevelOrganizer
{

    /**Make gun dependent on Timer, Animation (if possible), cLevel, cEXP, firing,
     *and currentTime
     *
     *CHANGE FIRING TO WEAPONS
     */
    public int cLevel;
    public int cEXP;
    private Animation a;
    private Timer timer;
    private static int width;
    private static int height;
    public static Magic currentMagic;
    public RechargeBar rechargeBar;
    private long currentTime;//actual time, under the influence of the updating time
    private long tempTime;//keeps track of the previous timePassed
    private int staffLevel = 1;
    private int exp = 0;
    public Fire fire;
    public Laser laser;
    
    public StaffLevelOrganizer()
    {
        fire = new Fire();
        laser = new Laser();
        rechargeBar = new RechargeBar();
        timer = new Timer(100, new DelayTimer());
        currentTime = 0;
        tempTime = 0;
    }
    public void levelUp()
    {
        staffLevel++;
    }
    public int getLevel()
    {
        return staffLevel;
    }
    public void addExp(int exp)
    {
        this.exp = exp;
    }
    public int getExp()
    {
        return exp;
    }
    
    public void update(long timePassed)
    {
        currentTime += timePassed - tempTime;
        tempTime = timePassed;
        
        if(Main.scroll == fire.getSelect())
        {
            timer.setDelay(fire.getDelay());
        }
        
        if((fire.fired && Main.scroll == fire.getSelect()) ||
            (laser.fired && Main.scroll == laser.getSelect()))
        {
            timer.start();
        }
        else if((!fire.fired || Main.scroll != fire.getSelect()) ||
            (!laser.fired || Main.scroll != laser.getSelect()))
        {
            currentTime = 0;
            timer.stop();
        }
        
        if(fire.getLevel() ==  0 && fire.getEXP() >= 100)
        {
            fire.levelUp();
            cLevel += 1;
        }
        
        /*switch(cLevel)
        {
            case 1:
                break;
            case 2:
                break;
        }*/
        //currentGun = (Gun)fire;
        //currentGun.update(timePassed);
        fire.update(timePassed);
        laser.update(timePassed);
        rechargeBar.update(timePassed);
    }
    public void render(Graphics2D g)
    {
        fire.render(g);
        laser.render(g);
        rechargeBar.render(g);
    }
    
    public class DelayTimer implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(fire.fired)
            {
                fire.fireFireball();
                currentTime = 0;
            }
            if(laser.fired)
            {
                laser.dragging=true;
                currentTime = 0;
            }
        }
    }
    
    public class RechargeBar
    {
        public int width;
        public int height;
        public double fill;
        public boolean disable;
        
        public RechargeBar()
        {
            width = 8;
            height = 35;
            fill = 0;
            disable = false;
        }
        
        public void update(long timePassed)
        {
            if(Main.scroll == fire.getSelect())
                disable = false;
            else
                disable = true;
            if(!disable)
            {
                fill = (double)((double)currentTime/(double)fire.getDelay())*height;
                if(fill>height)
                {
                    fill = height;
                }
            }
        }
        public void render(Graphics2D g)
        {
            if(Main.scroll == fire.getSelect() && !disable)
            {
                g.setColor(Color.GREEN);
                g.fillRect((int)(Main.sprite.getX()-15-Main.focusX), (int)Math.floor((Main.sprite.getY()+(height-fill)-4-Main.focusY)), width, (int)fill);
                g.setColor(Color.BLUE);
                g.drawRect((int)(Main.sprite.getX()-15-Main.focusX), (int)Math.floor((Main.sprite.getY()-4-Main.focusY)), width, height);
            }
        }
    }
}