import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.lang.*;

public class Transition
{
    public double delay = 10;
    public boolean stop = false;
    public boolean start = false;
    private int pos = 0;
    
    public void start()
    {
        start = true;
    }
    public void update(long timePassed)
    {
        if(start)
        {
            pos+=8;
            if(pos>=1000)
            {
                stop = true;
            }
        }
    }
    public void render(Graphics2D g)
    {
        if(start)
        {
            g.setColor(Color.BLACK);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, pos*0.001f));//0.001f
            g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
        }
    }
}