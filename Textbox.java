import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Textbox
{
    public char[] text;
    public int charPointer;
    public int linePointer;
    
    private long movieTime;//actual time, under the influence of the updating time
    private long totalTime;//total time animation should be played
    private long tempTime;//keeps track of the previous timePassed
    private long endTime=10;//end time after each character
    public static boolean playedOnce = false;
    public static boolean pause = false;
    private long tempT;
    private boolean draw = false;
    public static boolean start;
    
    public Textbox()
    {
        start = false;
        charPointer = 0;
        totalTime = 0;
        linePointer = 0;
    }
    
    public void render(Graphics2D g)
    {
        if(start)
        {
            g.setColor(Color.WHITE);
            //g.setBackground(new Color(0, 100, 255));
            //g.setForeground(new Color(0, 100, 0));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g.fillRect( (Main.WIDTH/2)-300, (Main.HEIGHT/2)+200, 600, 150);
            g.setColor(Color.BLACK);
            Font font = new Font("Serif", Font.PLAIN, 12);
            g.setFont(font);
            for(int i=0; i<charPointer; i++)//charPointer
            {
                if(charPointer<=70)
                {
                    linePointer = 1;
                    if(i<=70)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+(i*7), (Main.HEIGHT/2)+220);
                }
                else if(charPointer<=140)
                {
                    linePointer = 2;
                    if(i<=70)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+(i*7), (Main.HEIGHT/2)+220);
                    if(i>=70 && i<=140)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+((i-50)*7), (Main.HEIGHT/2)+240);
                }
                else if(charPointer<=210)
                {
                    linePointer = 3;
                    if(i<=70)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+(i*7), (Main.HEIGHT/2)+220);
                    if(i>=70 && i<=140)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+((i-70)*7), (Main.HEIGHT/2)+240);
                    if(i>=140 && i<=210)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+((i-140)*7), (Main.HEIGHT/2)+260);
                }
                else if(charPointer<=280)
                {
                    linePointer = 4;
                    if(i<=70)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+(i*7), (Main.HEIGHT/2)+220);
                    if(i>=70 && i<=140)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+((i-70)*7), (Main.HEIGHT/2)+240);
                    if(i>=140 && i<=210)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+((i-140)*7), (Main.HEIGHT/2)+260);
                    if(i>=210 && i<=280)
                        g.drawString(String.valueOf(text[i]), (Main.WIDTH/2)-200+((i-210)*7), (Main.HEIGHT/2)+280);
                }
                else if(charPointer>=200)
                {
                    draw = false;
                }
            }
            if(draw && charPointer<text.length)
            {
                charPointer++;
                draw = false;
            }
        }
    }
    
    public void update(long timePassed)
    {
        if(start)
        {
            movieTime += timePassed - tempTime;
            tempTime = timePassed;
            
            /*if(movieTime >= totalTime)//repeats lines
            {
                movieTime = 0;
                charPointer = 1;
            }*/
            if(charPointer == text.length-1)
            {
                playedOnce = true;
            }
            if(pause && movieTime>=endTime)
            {
                endTime -= tempT;
                pause = false;
            }
            if(charPointer<text.length && movieTime>=endTime)
            {
                draw = true;
                movieTime = 0;
            }
        }
    }
    public void pause(long t)//pauses temporarily
    {
        endTime += t;
        totalTime += t;
        tempT = t;
        pause = true;
    }
    public void setDelay(long t)//pause delay after every character
    {
        endTime = t;
        totalTime = t*text.length;
    }
    public void startText()
    {
        if(!start)
        {
            tempTime = 0;
            movieTime = 0;
            charPointer = 1;
            start = true;
        }
    }
    public void close()
    {
        start = false;
    }
    public void add(String s)
    {
        text = s.toCharArray();
        totalTime = text.length*endTime;
    }
}