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

public class GunLevelOrganizer
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
    public static Gun currentGun;
    public MachineGun machineGun;
    public WaterGun waterGun;
    private RechargeBar rechargeBar;
    private long currentTime;//actual time, under the influence of the updating time
    private long tempTime;//keeps track of the previous timePassed
    
    public GunLevelOrganizer()
    {
        machineGun = new MachineGun();
        waterGun = new WaterGun();
        rechargeBar = new RechargeBar();
        timer = new Timer(100, new DelayTimer());//getCurrentGun().getDelay()
        currentTime = 0;
        tempTime = 0;
    }
    
    public void update(long timePassed)
    {
        currentTime += timePassed - tempTime;
        tempTime = timePassed;
        
        if(Main.scroll == machineGun.getSelect())
        {
            timer.setDelay(machineGun.getDelay());
        }
        if(Main.scroll == waterGun.getSelect())
        {
            timer.setDelay(waterGun.getDelay());
        }
        
        if((machineGun.fired && Main.scroll == machineGun.getSelect()) ||
            (waterGun.fired && Main.scroll == waterGun.getSelect()))
        {
            timer.start();
        }
        else if((!machineGun.fired || Main.scroll != machineGun.getSelect()) ||
            (!waterGun.fired || Main.scroll != waterGun.getSelect()))
        {
            currentTime = 0;
            timer.stop();
        }
        
        if(machineGun.getLevel() ==  0 && machineGun.getEXP() >= 100)
        {
            machineGun.levelUp();
            cLevel += 1;
        }
        
        /*switch(cLevel)
        {
            case 1:
                break;
            case 2:
                break;
        }*/
        //currentGun = (Gun)machineGun;
        //currentGun.update(timePassed);
        //if(Main.scroll == 1)
        machineGun.update(timePassed);
        //if(Main.scroll == 2)
        waterGun.update(timePassed);
        rechargeBar.update(timePassed);
    }
    public void render(Graphics2D g)
    {
        //if(Main.scroll == 1)
        machineGun.render(g);
        //if(Main.scroll == 2)
        waterGun.render(g);
        rechargeBar.render(g);
    }
    
    public Gun getCurrentGun()
    {
        return currentGun;
    }
    
    public class DelayTimer implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(machineGun.fired)
            {
                machineGun.fireBullet();
                currentTime = 0;
            }
            if(waterGun.fired)
            {
                waterGun.fireWaterGun();
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
            if(Main.scroll == machineGun.getSelect())
                disable = false;
            else
                disable = true;
            if(!disable)
            {
                fill = (double)((double)currentTime/(double)machineGun.getDelay())*height;
                if(fill>height)
                {
                    fill = height;
                }
            }
        }
        public void render(Graphics2D g)
        {
            if(Main.scroll == machineGun.getSelect() && !disable)
            {
                g.setColor(Color.GREEN);
                g.fillRect((int)(Main.sprite.getX()-15-Main.focusX), (int)Math.floor((Main.sprite.getY()+(height-fill)-4-Main.focusY)), width, (int)fill);
                g.setColor(Color.BLUE);
                g.drawRect((int)(Main.sprite.getX()-15-Main.focusX), (int)Math.floor((Main.sprite.getY()-4-Main.focusY)), width, height);
            }
        }
    }
}