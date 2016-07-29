import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.awt.geom.AffineTransform;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.lang.*;
 
@SuppressWarnings("serial")
public class Main extends JFrame implements KeyListener, MouseMotionListener, MouseListener, MouseWheelListener
{
    //Updating speed per second
    final double GAME_HERTZ = 60.0;
    //If we are able to get as high as this FPS, don't render again.
    final double TARGET_FPS = 60;
    //Calculate how many ns each frame should take for our target game hertz.
    final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / TARGET_FPS;
    //At the very most we will update the game this many times before a new render.
    final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
    //If you're worried about visual hitches more than perfect timing, set this to 1.
    final int MAX_UPDATES_BEFORE_RENDER = 5;
    
    public static int winModeX, winModeY;          // top-left corner (x, y)
    public static int winModeWidth, winModeHeight; // width and height
    public static boolean inFullScreenMode;    // in fullscreen or windowed mode?
    public static boolean fullScreenSupported; // is fullscreen supported?
    protected static GraphicsDevice vc;
    private static int frameCount = 0;
    //private BufferedImage imageOfGameField;
    private boolean running;
    public static boolean pause = false;
    public static boolean updatePauseOnce = false;
    public static float focusX = 0;
    public static float focusY = 0;
    public static float tempfocusX = 0;
    public static float tempfocusY = 0;
    public static float lastMousePosX = 0;
    public static float lastMousePosY = 0;
    public static float mousePosX = 0;
    public static float mousePosY = 0;
    public static boolean mouseRight = false;
    public static boolean mouseMiddle = false;
    public static boolean mouseLeft = false;
    private boolean loadedImages = false;
    public static boolean startedGame = false;
    public static String mess = "";
    public static String fpsmess = "";
    public static int scroll = 0;
    public DrawCanvas dc = new DrawCanvas();
    public static Sprite sprite;
    public final static float grav = 0.06f;
    public static ArrayList <Block> tileMap;
    public static ArrayList enemyList;
    static TitleScreen ts;
    Transition transition;
    public static Terrain terrain;
    public UpgradeMenu upgradeMenu;
    public StatusBar statusBar;
    public Textbox textbox;
    public static Inventory inventory;
    public static GunLevelOrganizer GLO;
    public static StaffLevelOrganizer SLO;
    public static int WIDTH;
    public static int HEIGHT;
    private boolean updateIsDone = false;
    private boolean renderIsDone = false;
    //public boolean terrainUpdate = false;
    public float drag = .1f;//0.0001f;
    public float accX = mousePosX;
    public float accY = mousePosY;
    public float tempaccX = 0;
    public float tempaccY = 0;
    
    /** Constructor to set up the GUI components */
    public void init()
    {
        SplashScreen splash = new SplashScreen(2000);
        splash.showSplashAndExit();
        if(splash.ifSplashIsDone())
        {
            setFullScreen();
            ts = new TitleScreen();
            statusBar = new StatusBar();
            transition = new Transition();
            terrain = new Terrain();
            addKeyListener(this);
            addMouseListener(this);
            addMouseMotionListener(this);
            addMouseWheelListener(this);
            running = true;
            setContentPane(dc);
            runGameLoop();
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setVisible(true);
        }
    }
    
    /** Constructor to set up the GUI components */
    public void setFullScreen()
    {
        // Get the screen width and height
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        // Set the windowed mode initial width and height to about fullscreen
        winModeWidth = (int)dm.getWidth();
        winModeHeight = (int)dm.getHeight() - 35; // minus task bar
        WIDTH = winModeWidth;
        HEIGHT = winModeHeight;
        winModeX = 0;
        winModeY = 0;
        
        // Check if full screen mode supported?
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();
        fullScreenSupported = vc.isFullScreenSupported();
        
        if (fullScreenSupported)
        {
            setUndecorated(true);
            setResizable(false);
            vc.setFullScreenWindow(this); // full-screen mode
            inFullScreenMode = true;
            WIDTH = getWidth();
            HEIGHT = getHeight();
        }
        else
        {
            setUndecorated(false);
            setResizable(true);
            vc.setFullScreenWindow(null); // windowed mode
            setBounds(winModeX, winModeY, winModeWidth, winModeHeight);
            inFullScreenMode = false;
        }
        
        // Use ESC key to switch between Windowed and fullscreen modes
        //this.addKeyListener(this);
        
        // To save the window width and height if the window has been resized.
        this.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentMoved(ComponentEvent e)
            {
                if (!inFullScreenMode)
                {
                    winModeX = getX();
                    winModeY = getY();
                }
            }
        
            @Override
            public void componentResized(ComponentEvent e)
            {
                if (!inFullScreenMode)
                {
                    winModeWidth = getWidth();
                    winModeHeight = getHeight();
                    WIDTH = winModeWidth;
                    HEIGHT = winModeHeight;
                }
                else
                {
                    WIDTH = getWidth();
                    HEIGHT = getHeight();
                }
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    /**Overridden so we can exit when window is closed*/
    protected void processWindowEvent(WindowEvent e)
    {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING)
        {
            System.exit(0);
        }
    }
    
    public void runGameLoop()
    {
        Thread loop = new Thread()
        {
            public void run()
            {
                gameLoop();
            }
        };
        loop.start();
    }
    public void gameLoop()
    {
        long startTime = System.nanoTime()/1000000;
        long cumTime = startTime;
        double timePassedAfterUpdate = 1;
        double timePassedAfterRender = 1;
        double fps = 30;
        double hertz = 30;
        
        //We will need the last update time.
        double lastUpdateTime = System.nanoTime();
        //Store the last time we rendered.
        double lastRenderTime = System.nanoTime();
        
        //Simple way of finding FPS.
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
      
        while(running)
        {
            long timePassed = (System.nanoTime()/1000000) - cumTime;
            double now = System.nanoTime();
            int updateCount = 0;
         
            if (!pause)
            {
                //Do as many game updates as we need to, potentially playing catchup.
                while( now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER && renderIsDone)
                {
                    renderIsDone = false;
                    update(timePassed);
                    timePassedAfterUpdate = (((double)System.nanoTime()/1000000) - (double)cumTime) - (double)timePassed;
                    hertz = ((double)1000/((double)timePassedAfterUpdate+1));
                    /*if(updateIsDone)
                    {
                        dc.repaint();
                    }*/
                    if(startedGame)
                    {
                        //with mouse movement
                        tempfocusX = (float)Math.ceil(-tempaccX-drag);
                        tempfocusY = (float)Math.ceil(-tempaccY-drag);
                        focusX =  (int)Math.ceil((double)(tempfocusX+sprite.getX()-WIDTH/2+150));//((Math.round((int)sprite.getX() - WIDTH/2)) + (mousePosX/3));
                        focusY =  (int)Math.ceil((double)(tempfocusY+sprite.getY()-HEIGHT/2+50));//((Math.round((int)sprite.getY() - HEIGHT/2 + 50)) + (mousePosY/3))*accX;
                    }
                    lastUpdateTime += TIME_BETWEEN_UPDATES;
                    updateCount++;
                }
                
                //If for some reason an update takes forever, we don't want to do an insane number of catchups.
                //If you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
                if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
                {
                    lastUpdateTime = now - TIME_BETWEEN_UPDATES;
                }
                
                //fpsmess = "Hertz: " + (hertz) + "    FPS: " + (hertz) + "              " + (timePassed-cumTime);
                if(startedGame && !pause)
                {
                    terrain.update(timePassed);
                    //try{
                    //Thread.sleep(20);}catch(Exception ex){}
                }
                if(updateIsDone)
                {
                    updateIsDone = false;
                    dc.repaint();
                    timePassedAfterRender = (((double)System.nanoTime()/1000000) - (double)cumTime) - (double)timePassed;
                    fps = ((double)1000/((double)timePassedAfterRender+1));
                }
                lastRenderTime = now;
                
                //Update the frames we got.
                int thisSecond = (int) (lastUpdateTime / 1000000000);
                if (thisSecond > lastSecondTime)
                {
                    //System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    fps = frameCount;
                    frameCount = 0;
                    lastSecondTime = thisSecond;
                }
                
                //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
                while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
                {
                    Thread.yield();
                    
                    //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
                    //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
                    //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
                    try {Thread.sleep(1);} catch(Exception e) {} 
                    
                    now = System.nanoTime();
                    now = System.nanoTime();
                }
            }
            accX = (lastMousePosX - mousePosX)/6;
            accY = (lastMousePosY - mousePosY)/6;
            tempaccX += accX;
            tempaccY += accY;
            lastMousePosX = mousePosX;
            lastMousePosY = mousePosY;
        }
    }
    
    public void loadMap()
    {
        // BLOCK = Superclass, Tile=sub, MovingTile=sub, BackgroundBlock=sub
        
        terrain.loadMap1();
        tileMap = new ArrayList();
        enemyList = new ArrayList();
        sprite = new Sprite();
        terrain.createMap(sprite);
        inventory = new Inventory();
        upgradeMenu = new UpgradeMenu();
        upgradeMenu.make();
        statusBar.make();
        
        this.add(upgradeMenu);
        
        textbox = new Textbox();
        GLO = new GunLevelOrganizer();
        SLO = new StaffLevelOrganizer();
        
        /*addKeyListener(launcher);
        addMouseListener(launcher);
        addMouseMotionListener(launcher);
        addMouseWheelListener(launcher);
        addKeyListener(sprite);
        addMouseWheelListener(sprite);*/
        addMouseListener(upgradeMenu);
        addMouseMotionListener(upgradeMenu);
        addKeyListener(upgradeMenu);
    }
    
    public static ArrayList getMap()
    {
        return tileMap;
    }
    
    public void update(long timePassed)
    {
        updateIsDone = false;
        try
        {
            if(ts.start && !terrain.loadedMap)
            {
                loadMap();
                transition.start();
                ts.start = false;
                // = "START";
            }
            if(transition.start && !transition.stop)
            {
                transition.update(timePassed);
                //transition.stop=true;
            }
            if(transition.stop && !startedGame)
            {
                startedGame = true;
                textbox.add("Right-Click to Delete a block. Scroll to Equip a weapon. Left-Click to Fire " +
                            "the weapon. Press 'A' to move Left. Press 'D' to move Right. Double-press " +
                            "either of these to dash.");
            }
            if(transition.stop && startedGame)
            {
                
            }
            if(startedGame && !pause)
            {
                sprite.update(timePassed);
                GLO.update(timePassed);
                SLO.update(timePassed);
                statusBar.update(timePassed);
                //terrain.update(timePassed);
                textbox.startText();
                textbox.update(timePassed);
                inventory.update(timePassed);
            }
            if(startedGame)
            {
                upgradeMenu.update(timePassed);
            }
        }catch(Exception ex){System.out.println("RUNTIME UPDATE ERROR");}
        updateIsDone = true;
    }
    public void render(Graphics2D g)
    {
        try
        {
            if(ts.loadedTitle && !startedGame)
            {
                g.drawImage(ts.backgroundTitle, 0, 0, WIDTH, HEIGHT, null);//
                
                g.setStroke(new BasicStroke(2));
                g.setColor(Color.BLUE);
                g.draw(ts.getStartGameButtonRect());
                g.draw(ts.getOptionsButtonRect());
            }
            if(transition.start && !transition.stop)
            {
                transition.render(g);
            }
            
            if(terrain.loadedMap && startedGame)
            {
                //without mouse movement
                //focusX = Math.round((int)sprite.getX() - 150);
                //focusY = Math.round((int)sprite.getY() - s.getHeight()/2 + 50);
                g.drawImage(terrain.background, 0, 0, WIDTH, HEIGHT, null);
                
                /*for (int i = 0; i < tileMap.size(); i++)
                {
                    if(tileMap.get(i) instanceof Tile)
                    {
                        Tile t = (Tile) tileMap.get(i);
                        t.render(g);
                    }
                }*/
                terrain.render(g);
                /*for(int i=0; i<enemyList.size(); i++)
                {
                    Remnant r = ( Remnant ) Main.enemyList.get( i );
                    if (r.isVisible())
                    {
                        r.render(g);
                    }
                    else
                    {
                        enemyList.remove(i);
                    }
                }*/
                
                sprite.render(g);
                //machineGun.render(g);
                GLO.render(g);
                SLO.render(g);
                //launcher.render(g);
                //fire.render(g);
                //laser.render(g);
                //waterGun.render(g);
                statusBar.render(g);
                inventory.render(g);
                textbox.render(g);
                upgradeMenu.render(g);
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                if(pause)
                {
                    g.setColor(Color.BLACK);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f));
                    g.fillRect(0, 0, WIDTH, HEIGHT);
                    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }
            // Paint messages
            /*g.setColor(Color.YELLOW);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
            FontMetrics fm = g.getFontMetrics();
            String msg = Main.inFullScreenMode ? "In Full-Screen mode" : "In Windowed mode";
            int msgWidth = fm.stringWidth(msg);
            int msgAscent = fm.getAscent();
            int msgX = getWidth() / 2 - msgWidth / 2;
            int msgY = getHeight() / 2 + msgAscent / 2;
            g.drawString(msg, msgX, msgY);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
            fm = g.getFontMetrics();
            msg = "Press SPACE to toggle between Full-screen/windowed modes, ESC to quit.";
            msgWidth = fm.stringWidth(msg);
            int msgHeight = fm.getHeight();
            msgX = getWidth() / 2 - msgWidth / 2;
            msgY += msgHeight * 1.5;
            g.drawString(msg, msgX, msgY);*/
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
            g.drawString(fpsmess, 30, 30);
            g.drawString(mess, 30, 90);
        }catch(Exception ex){System.out.println("RUNTIME RENDER ERROR");}
        renderIsDone = true;
    }
    
    /** DrawCanvas (inner class) is a JPanel used for custom drawing */
    public class DrawCanvas extends JPanel
    {
        public DrawCanvas()
        {
            super();
            this.setDoubleBuffered(true);
        }
        @Override
        public void paintComponent(Graphics gr)
        {
            super.paintComponent(gr);
            Graphics2D g = (Graphics2D)gr;
            /*imageOfGameField = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D offScreen = imageOfGameField.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            setBackground(Color.BLACK);
            render(offScreen);
            g.drawImage(imageOfGameField, 0, 0, null);*/
            render(g);
            g.dispose();
            //offScreen.dispose();
        }
    }
    
    public void pause()
    {
        if(startedGame)
        {
            pause = true;
            reset();
        }
    }
    public void reset()
    {
        SLO.fire.fired = false;
        SLO.laser.dragging = false;
        GLO.machineGun.fire(false);
        GLO.waterGun.fire(false);
    }
    public void process(MouseEvent e)
    {
        int input = e.getButton();
        
        if(!pause && startedGame)
        {
            if(input == e.BUTTON1)
            {
                if(scroll == SLO.laser.getSelect())
                {
                    SLO.laser.dragging = true;
                }
                else
                {
                    SLO.laser.dragging = false;
                }
                
                if(scroll == GLO.machineGun.getSelect())
                {
                    GLO.machineGun.fire(true);
                }
                else
                {
                    GLO.machineGun.fire(false);
                }
                
                if(scroll == GLO.waterGun.getSelect())
                {
                    GLO.waterGun.fire(true);
                }
                else
                {
                    GLO.waterGun.fire(false);
                }
                
                if(scroll == SLO.fire.getSelect())
                {
                    SLO.fire.fired = true;
                }
                else
                {
                    SLO.fire.fired = false;
                }
                
                if(scroll == GLO.machineGun.getSelect() &&
                    (GLO.machineGun.getSelect() >= 1 || GLO.machineGun.getSelect() <= 6))
                {
                    GLO.machineGun.selected(true);
                }
                if(scroll == GLO.waterGun.getSelect() &&
                    (GLO.waterGun.getSelect() >= 1 || GLO.waterGun.getSelect() <= 6))
                {
                    GLO.machineGun.selected(true);
                }
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
        int input = e.getButton();
        
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            mouseLeft = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            mouseRight = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON2)
        {
            mouseMiddle = true;
        }
        
        if(startedGame && !pause)
        {
            process(e);
        }
        else
        {
            pause();
        }
    }
    @Override
    public void mouseReleased(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
        int input = e.getButton();
        
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            mouseLeft = false;
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            mouseRight = false;
        }
        else if(e.getButton() == MouseEvent.BUTTON2)
        {
            mouseMiddle = false;
        }
        
        if(!startedGame)
        {
            ts.update(input, e);
        }
        else
        {
            reset();
        }
        if(pause)
        {
            //pause();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }
    @Override
    public void mouseEntered(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
        if(pause)
        {
            pause = false;
        }
    }
    @Override
    public void mouseExited(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
        
        if(!pause)
        {
            pause();
            repaint();
        }
    }
    @Override
    public void mouseDragged(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
        int input = e.getButton();
        
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            mouseLeft = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON3)
        {
            mouseRight = true;
        }
        else if(e.getButton() == MouseEvent.BUTTON2)
        {
            mouseMiddle = true;
        }
        
        if(startedGame && !pause)
        {
            process(e);
        }
    }
    @Override
    public void mouseMoved(MouseEvent e)
    {
        mousePosX = e.getX();
        mousePosY = e.getY();
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        int notches = e.getWheelRotation();
        
        if(!pause)
        {
            if(notches < 0)
            {
                if(scroll > 5)
                {
                    scroll = 0;
                }
                scroll++;
                //mess = "Mouse wheel moved UP " + (scroll) + " notch(es)";
            }
            else if(notches > 0)
            {
                if(scroll < 2)
                {
                    scroll = 6 + 1;
                }
                scroll--;
                //mess = "Mouse wheel moved DOWN " + (scroll) + " notch(es)";
            }
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        boolean toggle = true;
        if(startedGame)
        {
            if(keyCode == KeyEvent.VK_P && pause && toggle)
            {
                pause = false;
                toggle = false;
            }
            if(keyCode == KeyEvent.VK_P && !pause && toggle)
            {
                pause();
                toggle = false;
                repaint();
            }
            if(keyCode == KeyEvent.VK_D)
            {
                sprite.keyPressed(e);
            }
            if(keyCode == KeyEvent.VK_A)
            {
                sprite.keyPressed(e);
            }
            if(keyCode == KeyEvent.VK_SPACE)
            {
                sprite.keyPressed(e);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_F3)
        {
            if (fullScreenSupported)
            {
                if (!inFullScreenMode)
                {
                    // Switch to fullscreen mode
                    setVisible(false);
                    setResizable(false);
                    dispose();
                    setUndecorated(true);
                    vc.setFullScreenWindow(this);
                    setVisible(true);
                    WIDTH = winModeWidth;
                    HEIGHT = winModeHeight;
                }
                else
                {
                    // Switch to windowed mode
                    setVisible(false);
                    dispose();
                    setUndecorated(false);
                    setResizable(true);
                    vc.setFullScreenWindow(null);
                    setBounds(winModeX, winModeY, winModeWidth, winModeHeight);
                    setVisible(true);
                    WIDTH = getWidth();
                    HEIGHT = getHeight();
                }
                inFullScreenMode = !inFullScreenMode;
                repaint();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
    @Override
    public void keyReleased(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        
        if(startedGame)
        {
            if(keyCode == KeyEvent.VK_D)
            {
                sprite.keyReleased(e);
            }
            if(keyCode == KeyEvent.VK_A)
            {
                sprite.keyReleased(e);
            }
            if(keyCode == KeyEvent.VK_SPACE)
            {
                sprite.keyReleased(e);
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
    }
    
    /** The Entry main method */
    public static void main(String[] args)
    {
        new Main().init();
    }
}