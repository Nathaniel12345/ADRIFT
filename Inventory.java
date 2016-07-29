import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class Inventory
{
    private BufferedImage tile_cell;
    private BufferedImage tile_select;
    private boolean dual = false;
    private int celly;
    
    public Inventory()
    {
        try
        {
            tile_cell = ImageIO.read(new File("res/tile_cell.png"));
            tile_select = ImageIO.read(new File("res/tile_select.png"));
        }
        catch(Exception ex){System.out.println("can't load inventory");}
    }
    
    public void update(long timePassed)
    {
        switch(Main.scroll)
        {
            case 6:
                celly = (Main.HEIGHT/2)-126;
                break;
            case 5:
                celly = (Main.HEIGHT/2)-76;
                break;
            case 4:
                celly = (Main.HEIGHT/2)-26;
                break;
            case 3:
                celly = (Main.HEIGHT/2)+24;
                break;
            case 2:
                celly = (Main.HEIGHT/2)+74;
                break;
            case 1:
                celly = (Main.HEIGHT/2)+124;
                break;
            default:
                celly = (Main.HEIGHT/2)-126;
                break;
        }
    }
    public void render(Graphics2D g)
    {
        g.drawImage(tile_cell, 30, (Main.HEIGHT/2)-125, 30, 30, null);
        g.drawImage(tile_cell, 30, (Main.HEIGHT/2)-75, 30, 30, null);
        g.drawImage(tile_cell, 30, (Main.HEIGHT/2)-25, 30, 30, null);
        g.drawImage(tile_cell, 30, (Main.HEIGHT/2)+25, 30, 30, null);
        g.drawImage(tile_cell, 30, (Main.HEIGHT/2)+75, 30, 30, null);
        g.drawImage(tile_cell, 30, (Main.HEIGHT/2)+125, 30, 30, null);
        
        g.drawImage(tile_select, 29, celly, 30, 30, null);
    }
}