THIS IS AN UNFINISHED PROJECT

As of now, this game is set up for the purpose of implementing specific features
that would normally be in a game. It does not have a goal, nor enemy AI; it simply
shows the versatility of how each feature can function.


STARTING UP THE GAME:
Go into the "src" folder, and using a compiler (preferably BlueJ), open the "package"
application. From there, many boxes will appear. Right-click the "Main" box, then
select the method "void main(Sring[] args)". Press OK. This will start the game.


CURRENT FEATURES (Title Screen):
-When the game starts (it may take a few seconds), a SplashScreen will appear, which
only lasts 2 seconds. It shows a white box with a red border, crediting a non-existent
game company.
-The screen will transition to a background with two boxes. Left-clicking the top box
will start the game. The bottom box was placed as a "Settings" menu, but has not been
expanded upon, and thus does nothing.
-When the top box is left-clicked, there will be a fading black transition, and once it
fades completely to black, the game will start.


CURRENT FEATURES (World):

Use a mouse for the actual gameplay; using a touchpad or a mousepad makes it much more
difficult.

-This world is created using two algorithms. The first is a preset terain, defined
within the Terrain class in an array, where I can alter specific points on the
game's terrain to add certain blocks. Then, the second algorithm adds randomly generated
terrain to overlap the preset terrain.
-In addition to the terrain, the enemies are also randomly scattered above the maximum
terrain height.
-Moving the mouse around, you can see that, depending on the position of the mouse, the
camera can follow to a certain extent.
-Right-clicking on a block of the terrain will delete the block.
-There is an end to the world, both vertically and horizontally in both directions.
Too big of a world often causes too long of load times.


CURRENT FEATURES (Sprite)
-The Sprite is the main character (the white and black person built with terrible
artistic skills).
-Pressing the Space bar will make him jump.
-Pressing the "A" key will move him to the left.
-Pressing the "D" key will move him to the right.
-Double-tapping the "A" or "D" keys will move him at twice the speed in that direction.
-GLITCH: When double-tapping, the character will glitch and move to the top of the
blocks that he's running towards. I use Rectangle objects for collision detection, and
because of this, I can't do anything to prevent the glitching. I plan to use a completely
separate form of collision detection, one that involves vectors and free-form objects.
-Scrolling will change the weapons on the Sprite. When scrolling, 4 out of the 6 boxes
will be weapons. 3 out of the 6 will have a weapon appear on the Sprite, which rotate
depending on where the mouse is pointed. Left-clicking will shoot the weapon.
-The Sprite falls with gravity.


CURRENT FEATURES (On the screen)
-Scrolling will change the weapons on the Sprite. On the left side of the screen, there
are boxes, and scrolling up or down will change which box is selected. 4 out of the 6
boxes are weapons, specifically the bottom 4 boxes. The top 2 don't do anything.
The bottom box won't have a weapon appear on the Sprite, rather when left-clicking, a
laser will shoot out.
-The bottom of the screen is a text box, which, depending on how it is set, can have
letters appear at specific intervals of time. The borders of the text weren't well-
defined.
-Pressing "P" will dim and freeze the screen. This is the Pause button.
-Pressing "F3" will make the full-screen into a windowed view.
-GLITCH: The mouse will be offset on the screen, so that wherever you point, the game
will think it's about 20 pixels lower.
-Moving the mouse off the screen in its windowed view will automatically pause the game.
-Pressing "I" makes a box visible or invissible on the top left of the screen. This box
is meant to act like a normal Windows window, where you can drag and drop it anywhere.
This is not complete, it's only to show how a window in the game can eventually work.
-At the top of the screen, there is a black bar. As of now, it does nothing, but is
meant to act as a status bar, to show the current objective.


CURRENT FEATURES (Enemies)
-These are the flapping things in the air. I built an Animation class to basically add
multiple images to create a loop, making it look like it's flapping its wings.
-When shot with a weapon (except for the laser), a health bar appears, and when its
health goes all the way down, it disappears.


CURRENT FEATURES (Weapons)
-When a weapon is selected (after scrolling), each weapon appears on the Sprite.
-The weapons rotate depending on where the mouse is pointing.
-Depending on the weapon, a blue bar appears, and when the weapon is shot (the mouse
must be held down), there is a recharge rate, which shows the blue box with
a green bar increasing inside it between shots.
-One of the guns is a Water Gun, which sprays water and reacts with a constant
gravitational pull. This does minor damage to the enemies. This also has a very high
recharge rate, meaning it shoots extremely fast.
-One of the guns is a generic gun. It shoots bullets, which do regular damage to the
enemies. It has a decent recharge rate
-The Staff shoots fireballs, which do a lot of damage to the enemies. It has a slow
recharge rate.


This project was simply my own idea and entirely my own programming. The graphics
were all created by a friend of mine, named Jared Newman. This project used to take
up my free time, and lasted for about 2 years with numerous re-writes.
