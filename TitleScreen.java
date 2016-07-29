import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class TitleScreen
{
    public BufferedImage startButtonImage;
    public BufferedImage optionsButtonImage;
    public BufferedImage backgroundTitle;
    boolean start = false;
    boolean options = false;
    public boolean loadedTitle = false;
    
    public TitleScreen()
    {
        try
        {
            backgroundTitle = ImageIO.read(new File("res/backgrounds/grey_cave.png"));
            loadedTitle = true;
        }
        catch(Exception ex){System.out.println("can't load title screen");}
    }
    
    public void update(int input, MouseEvent e)
    {
        if(!start)
        {
            if (input == e.BUTTON1)
            {
                Rectangle buttonRect = getStartGameButtonRect();
                if(buttonRect.contains(e.getX(), e.getY()))
                {
                    start = true;//start game or load level
                }
            }
            if (input == e.BUTTON1)
            {
                Rectangle buttonRect = getOptionsButtonRect();
                if(buttonRect.contains(e.getX(), e.getY()))
                {
                    options = true;//start game or load level
                }
            }
        }
                //sbg.enterState(GameState.ID, new FadeOutTransition(), new FadeInTransition());
            //}
        //}
        //starfield.update(gc, deltaMS);
    }
    
    public Rectangle getStartGameButtonRect()
    {
        int buttonWidth = 146;
        int buttonHeight = 59;
        return new Rectangle(Main.WIDTH/2 - buttonWidth/2, Main.HEIGHT - buttonHeight - 220,
            buttonWidth, buttonHeight);
    }
    public Rectangle getOptionsButtonRect()
    {
        int buttonWidth = 146;
        int buttonHeight = 59;
        return new Rectangle(Main.WIDTH/2 - buttonWidth/2, Main.HEIGHT - buttonHeight - 120,
            buttonWidth, buttonHeight);
    }
}