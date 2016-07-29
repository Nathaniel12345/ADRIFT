import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.*;

public class Terrain
{
    private static String currentMap[][];
    public static boolean loadedMap = false;
    public boolean loadedMap1 = false;
    
    private BufferedImage tileset;
    private BufferedImage tile_dirt_top;
    private BufferedImage tile_dirt_block;
    private BufferedImage tile_dirt_side;
    private BufferedImage tile_grass_top;
    private BufferedImage tile_grass_edge;
    private BufferedImage tile_greyrock_top;
    private BufferedImage tile_greyrock_block;
    private BufferedImage tile_lava_top;
    private BufferedImage tile_lava_block;
    private BufferedImage tile_lava_side;
    private BufferedImage tile_lava_edge;
    public BufferedImage background;
    public final int MAXWIDTH = 10000;
    public final int MAXHEIGHT = 50;
    public int terrainWidth;
    public int terrainHeight;
    private boolean removed = false;
    
    public void loadMap1()
    {
        String map [][] = 
        {{" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", "#", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", "#", " ", "#", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", "S", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", ">", "#", "#", "#", "#", "#", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#", "#", "#", "#", "#", "#", "#", "#", "#", "#", "#", "#", "#"},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#", "#", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#", "#", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", "#", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", "#", "#", "#", "#", "#", "#", "#", "#", "#", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {"#", "#", "#", "#", "#", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "},
         {" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " "}};
        
        currentMap = generateLevelDirt(map, 200);
        //currentMap = map;
        
        try
        {
            tileset = ImageIO.read(new File("res/tiles/tileset.png"));
            background = ImageIO.read(new File("res/backgrounds/blue_sky.png"));
            tile_dirt_top = tileset.getSubimage(60, 60, 20, 20);
            tile_dirt_block = tileset.getSubimage(40, 60, 20, 20);
            tile_dirt_side = tileset.getSubimage(20, 60, 20, 20);
            tile_grass_top = tileset.getSubimage(60, 40, 20, 20);
            tile_grass_edge = tileset.getSubimage(0, 40, 20, 20);
            tile_greyrock_top = tileset.getSubimage(60, 20, 20, 20);
            tile_greyrock_block = tileset.getSubimage(40, 20, 20, 20);
            tile_lava_top = tileset.getSubimage(60, 0, 20, 20);
            tile_lava_block = tileset.getSubimage(40, 0, 20, 20);
            tile_lava_side = tileset.getSubimage(20, 0, 20, 20);
            tile_lava_edge = tileset.getSubimage(0, 0, 20, 20);
        }
        catch(Exception ex){System.out.println("LOAD MAP ERROR");}
        int terrainWidth = currentMap.length;
        int terrainHeight = currentMap[0].length;
        
        loadedMap1 = true;
        loadedMap = true;
    }
    
    public String[][] generateLevelDirt(String[][] map, int length)
    {
        int tempW;
        if(map[0].length>length)
        {
            tempW = map[0].length;
        }
        else
        {
            tempW = length;
        }
        String[][] newMap = new String[MAXHEIGHT][tempW];//[map.length(height)][map[0].length(width)]
        
        for(int y = 0; y<newMap.length; y++)
        {
            for(int x = 0; x<newMap[0].length; x++)
            {
                try
                {
                    if(y<newMap.length/2)
                    {
                        newMap[y][x]=" ";
                    }
                    else
                    {
                        newMap[y][x]="#";
                    }
                }catch(Exception ex){System.out.println(y + " " + x);System.exit(0);}
            }
        }
        
        for(int y = 0; y<newMap.length; y++)
        {
            for(int x = 0; x<newMap[0].length; x++)
            {
                try
                {
                    if(y>10 && x<newMap[0].length-1 && x>0 && y<newMap.length-1)
                    {
                        if(new Random().nextInt(100) > 20)
                        {
                            if(newMap[y-1][x-1].equals("#"))
                            {
                                newMap[y][x]="#";
                            }
                        }
                        if(new Random().nextInt(100) > 30)
                        {
                            if(newMap[y-1][x+1].equals("#"))
                            {
                                newMap[y][x]="#";
                            }
                        }
                        if(newMap[y-1][x].equals("#"))
                        {
                            newMap[y][x]="#";
                        }
                        if(new Random().nextInt(100) < 2)
                        {
                            newMap[y][x]="#";
                        }
                    }
                }catch(Exception ex){System.out.println(y + " " + x);System.exit(0);}
            }
        }
        
        int tempHeight;
        int tempWidth;
        if(map.length>newMap.length)
        {
            tempHeight = newMap.length;
        }
        else
        {
            tempHeight = map.length;
        }
        if(map[0].length>newMap[0].length)
        {
            tempWidth = newMap[0].length;
        }
        else
        {
            tempWidth = map[0].length;
        }
        for(int y = 0; y<tempHeight; y++)
        {
            for(int x = 0; x<tempWidth; x++)
            {
                if(!map[y][x].equals(" "))
                {
                    newMap[y][x]=map[y][x];
                }
            }
        }
        for(int y = 0; y<newMap.length; y++)
        {
            for(int x = 0; x<newMap[0].length; x++)
            {
                try
                {
                    if(x<newMap[0].length && y<newMap.length && y>=0 && x>=0)
                    {
                        if(newMap[y][x].equals("#") && newMap[y-1][x].equals(" "))
                        {
                            newMap[y][x] = "_";
                        }
                    }
                }catch(Exception ex){System.out.println(y + " " + x);System.exit(0);}
            }
        }
        
        for(int y = 0; y<newMap.length; y++)
        {
            for(int x = 0; x<newMap[0].length; x++)
            {
                try
                {
                    if(y>0 && x>0 && y<newMap.length/2)
                    {
                        if(newMap[y][x].equals(" "))
                        {
                            if(newMap[y+1][x].equals(" ") && newMap[y+2][x].equals(" "))
                            {
                                if(new Random().nextInt(100) < 5)
                                {
                                    newMap[y][x]= "s";
                                }
                            }
                        }
                    }
                }catch(Exception ex){System.out.println(y + " " + x);System.exit(0);}
            }
        }
        
        return newMap;
    }
    
    public String[][] generateLevelSky(String[][] map, int length)
    {
        int tempW;
        if(map[0].length>length)
        {
            tempW = map[0].length;
        }
        else
        {
            tempW = length;
        }
        String[][] newMap = new String[MAXHEIGHT][tempW];//[map.length(height)][map[0].length(width)]
        
        for(int y = newMap.length-1; y>=0; y--)
        {
            for(int x = newMap[0].length-1; x>=0; x--)
            {
                try
                {
                    if(y==newMap.length-1)
                    {
                        newMap[y][x] = "#";
                    }
                    else if(y>newMap.length/8 && x<=length-2 && y<newMap.length && x>0)
                    {
                        newMap[y][x]=" ";
                        if(new Random().nextInt(100) < 7)
                        {
                            newMap[y][x]="#";
                        }
                        if(new Random().nextInt(100) < 20)
                        {
                            if(newMap[y+1][x].equals("#") || newMap[y+1][x+1].equals("#") || newMap[y+1][x-1].equals("#"))
                            {
                                newMap[y][x]="#";
                            }
                        }
                        if(new Random().nextInt(100) < 55)
                        {
                            if(newMap[y+1][x].equals("#") && newMap[y+1][x+1].equals("#") && newMap[y+1][x-1].equals("#"))
                            {
                                newMap[y][x]="#";
                            }
                        }
                    }
                    else
                    {
                        newMap[y][x] = " ";
                    }
                }catch(Exception ex){System.out.println(y + " " + x);System.exit(0);}
            }
        }
        
        for(int y = 0; y<newMap.length; y++)
        {
            for(int x = 0; x<newMap[0].length; x++)
            {
                try
                {
                    if(newMap[y+1][x].equals("#") && newMap[y-1][x].equals(" "))
                    {
                        newMap[y][x] = "_";
                    }
                }catch(Exception ex){System.out.println(y + " " + x);System.exit(0);}
            }
        }
        
        int tempHeight;
        int tempWidth;
        if(map.length>newMap.length)
        {
            tempHeight = newMap.length;
        }
        else
        {
            tempHeight = map.length;
        }
        if(map[0].length>newMap[0].length)
        {
            tempWidth = newMap[0].length;
        }
        else
        {
            tempWidth = map[0].length;
        }
        for(int y = 0; y<tempHeight; y++)
        {
            for(int x = 0; x<tempWidth; x++)
            {
                if(!map[y][x].equals(" "))
                {
                    newMap[y][x]=map[y][x];
                }
            }
        }
        return newMap;
    }
    
    public void createMap(Sprite sprite)
    {
        for (int row = 0; row < currentMap.length; row++)
        {
            for (int col = 0; col < currentMap[0].length; col++)
            {
                if (currentMap[row][col].equals("#"))
                {
                    Tile tempTile = new Tile(col*Block.tileWidth, row*Block.tileHeight);
                    Main.tileMap.add(tempTile);
                    tempTile.setImage(tile_dirt_block);
                    tempTile.setVisible(true);
                }
                else if (currentMap[row][col].equals("S"))
                {
                    sprite.setX(col * Block.tileWidth);
                    sprite.setY(row * Block.tileHeight);
                    //machineGun = new MachineGun();//
                    //GLO = new GunLevelOrganizer();
                    //fire = new Fire();
                    //waterGun = new WaterGun();
                    //laser = new Laser();
                    //launcher = new Launcher();
                }
                else if (currentMap[row][col].equals("s"))
                {
                    Seeker tempSeeker = new Seeker();
                    tempSeeker.setX(col*Block.tileWidth);
                    tempSeeker.setY(row*Block.tileHeight);
                    Main.enemyList.add(tempSeeker);
                }
                else if (currentMap[row][col].equals("_"))
                {
                    Tile tempTile = new Tile(col*Block.tileWidth, row*Block.tileHeight);
                    Main.tileMap.add(tempTile);
                    tempTile.setImage(tile_dirt_top);
                    tempTile.setVisible(true);
                }
                else if (currentMap[row][col].equals(">"))
                {
                    Tile tempTile = new Tile(col*Block.tileWidth, row*Block.tileHeight);
                    Main.tileMap.add(tempTile);
                    tempTile.setImage(tile_lava_block);
                    tempTile.setVisible(true);
                    tempTile.moveRight(0.4f);
                    tempTile.right = true;
                }
                else if (currentMap[row][col].equals("<"))
                {
                    Tile tempTile = new Tile(col*Block.tileWidth, row*Block.tileHeight);
                    Main.tileMap.add(tempTile);
                    tempTile.setImage(tile_greyrock_block);
                    tempTile.setVisible(true);
                    tempTile.moveLeft(0.4f);
                    tempTile.left = true;
                }
                else if (currentMap[row][col].equals("^"))
                {
                    Tile tempTile = new Tile(col*Block.tileWidth, row*Block.tileHeight);
                    Main.tileMap.add(tempTile);
                    tempTile.setImage(tile_lava_top);
                    tempTile.setVisible(true);
                    tempTile.moveUp(0.4f);
                    tempTile.up = true;
                }
                else if (currentMap[row][col].equals("V"))
                {
                    Tile tempTile = new Tile(col*Block.tileWidth, row*Block.tileHeight);
                    Main.tileMap.add(tempTile);
                    tempTile.setImage(tile_lava_side);
                    tempTile.setVisible(true);
                    tempTile.moveDown(0.4f);
                    tempTile.down = true;
                }
            }
        }
    }
    
    public void update(long timePassed)
    {
        for (int i = 0; i < Main.tileMap.size(); i++)
        {
            if(Main.tileMap.get(i) instanceof Tile)
            {
                Tile t = (Tile) Main.tileMap.get(i);
                /*if(Main.mouseRight)
                {
                    if(t.getBounds().contains(new Point(Math.round(Main.mousePosX + Main.focusX), Math.round(Main.mousePosY + Main.focusY))))
                    {
                        Main.tileMap.remove(t);
                        removed = true;
                    }
                }*/
                t.update(timePassed);
                if( t.getX() < Main.WIDTH+Main.focusX+(Block.tileWidth*3) &&
                    t.getY() < Main.HEIGHT+Main.focusY+(Block.tileHeight*3) &&
                    t.getX()+(Block.tileWidth*3) > Main.focusX &&
                    t.getY()+(Block.tileHeight*3) > Main.focusY)
                    {
                        //t.update(timePassed);
                    }
                /*ArrayList w = Main.GLO.waterGun.getWater();
                for (int j = 0; j < w.size(); j++)
                {
                    Water wr = (Water)w.get(j);
                    if(t.getBounds().intersects(wr.getBounds()))
                    {
                        w.remove(j);
                    }
                }*/
            }
        }
        
        for(int i=0; i<Main.enemyList.size(); i++)
        {
            Remnant r = ( Remnant ) Main.enemyList.get( i );
            if (r.isVisible())
            {
                r.update(timePassed);
            }
            else
            {
                Main.enemyList.remove(i);
            }
        }
    }
    
    public void render(Graphics2D g)
    {
        for (int i = 0; i < Main.tileMap.size(); i++)
        {
            if(Main.tileMap.get(i) instanceof Tile)
            {
                Tile t = (Tile) Main.tileMap.get(i);
                if( t.getX() < Main.WIDTH+Main.focusX+(Block.tileWidth) &&
                    t.getY() < Main.HEIGHT+Main.focusY+(Block.tileHeight) &&
                    t.getX()+(Block.tileWidth) > Main.focusX &&
                    t.getY()+(Block.tileHeight*3) > Main.focusY)
                    {
                        t.render(g);
                    }
            }
        }
        if(Main.mouseRight)
        {
            for (int i = 0; i < Main.tileMap.size(); i++)
            {
                if(Main.tileMap.get(i) instanceof Tile)
                {
                    Tile t = (Tile) Main.tileMap.get(i);
                    if(t.getBounds().contains(new Point(Math.round(Main.mousePosX + Main.focusX), Math.round(Main.mousePosY + Main.focusY))))
                    {
                        Main.tileMap.remove(t);
                    }
                }
            }
        }
        /*for (int i = Main.tileMap.size()-1; i >=0 ; i--)
        {
            if(Main.tileMap.get(i) instanceof Tile)
            {
                Tile t = (Tile) Main.tileMap.get(i);
                if( t.getX() < Main.WIDTH+Main.focusX+(Block.tileWidth*3) &&
                    t.getY() < Main.HEIGHT+Main.focusY+(Block.tileHeight*3) &&
                    t.getX()+(Block.tileWidth*3) > Main.focusX &&
                    t.getY()+(Block.tileHeight*3) > Main.focusY)
                    {
                        t.render(g);
                    }
            }
        }*/
        
        for(int i=0; i<Main.enemyList.size(); i++)
        {
            Remnant r = ( Remnant ) Main.enemyList.get( i );
            if (r.isVisible())
            {
                r.render(g);
            }
            else
            {
                Main.enemyList.remove(i);
            }
        }
    }
    
    public static String[][] getCurrentMap()
    {
        return currentMap;
    }
    ///////////////////RIGHT-DOWN works FINE, LEFT-UP DOESN'T WORK
}