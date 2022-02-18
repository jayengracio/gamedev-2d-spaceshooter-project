import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */


public class MainWindow {
    private static final JFrame frame = new JFrame("Starfighter 22");   // Change to the name of your game
    private static final Model gameWorld = new Model("Hello");
    private static final Viewer canvas = new Viewer(gameWorld);

    private static final JPanel scorePanel = new JPanel();
    private static final JLabel scoreCount = new JLabel("Score: ");
    private static final JPanel timePanel = new JPanel();
    private static final JLabel time = new JLabel();

    private static final JPanel player1AmmoPanel = new JPanel();
    private static final JPanel player1LifePanel = new JPanel();
    private static final JLabel player1AmmoCount = new JLabel(new ImageIcon("res/ammo_icon.png"));
    private static final JLabel player1LifeCount = new JLabel(new ImageIcon("res/playerLife1_red.png"));

    private static final JPanel player2AmmoPanel = new JPanel();
    private static final JPanel player2LifePanel = new JPanel();
    private static final JLabel player2AmmoCount = new JLabel(new ImageIcon("res/ammo_icon.png"));
    private static final JLabel player2LifeCount = new JLabel(new ImageIcon("res/playerLife1_blue.png"));

    private static final JPanel gameOver = new JPanel();
    private static final int TargetFPS = 300;
    private static boolean startGame = false;
    private static boolean multiMode = false;
    private final KeyListener Controller = new Controller();
    private final KeyListener Controller2 = new Controller2();
    private JLabel BackgroundImageForStartMenu;

    public MainWindow() {
        frame.setSize(1000, 1000);  // you can customise this later and adapt it to change on size.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
        frame.setLayout(null);
        frame.add(scorePanel);
        frame.add(timePanel);
        frame.add(player1AmmoPanel);
        frame.add(player1LifePanel);
        frame.add(player2AmmoPanel);
        frame.add(player2LifePanel);
        frame.add(gameOver);
        frame.add(canvas);

        JPanel buttonsPanel = setupPlayerButtons();
        frame.add(buttonsPanel);

        canvas.setBounds(0, 35, 1000, 965);
        canvas.setBackground(new Color(255, 255, 255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen
        canvas.setVisible(false);   // this will become visible after you press the key.

        setupUI();

        //loading background image
        File BackgroundToLoad = new File("res/splash_screen.png");  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE
        try {
            BufferedImage myPicture = ImageIO.read(BackgroundToLoad);
            BackgroundImageForStartMenu = new JLabel(new ImageIcon(myPicture));
            BackgroundImageForStartMenu.setBounds(0, 35, 1000, 965);
            frame.add(BackgroundImageForStartMenu);
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        MainWindow hello = new MainWindow();  //sets up environment

        //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple
        while (true) {
            //swing has timer class to help us time this but I'm writing my own, you can of course use the timer, but I want to set FPS and display it
            int TimeBetweenFrames = 1000 / TargetFPS;
            long FrameCheck = System.currentTimeMillis() + (long) TimeBetweenFrames;

            //wait till next time step
            while (FrameCheck > System.currentTimeMillis()) {
            }

            if (startGame) {
                if (gameWorld.getPlayer().getLives() == 0 && !gameWorld.isMultiplayerMode()) {
                    gameOver.setVisible(true);
                    gameWorld.setGameStart(false);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (gameWorld.getPlayer().getLives() == 0 && gameWorld.getPlayer2().getLives() == 0
                        && gameWorld.isMultiplayerMode()) {
                    gameOver.setVisible(true);
                    gameWorld.setGameStart(false);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    gameLoop();
                }
            }

            //UNIT test to see if framerate matches
            //UnitTests.CheckFrameRate(System.currentTimeMillis(), FrameCheck, TargetFPS);
        }
    }

    private JPanel setupPlayerButtons() {
        Icon icon = new ImageIcon("res/button1Player.png");
        JButton OnePlayerButton = new JButton(icon);  // start button

        Icon icon2 = new ImageIcon("res/button2Player.png");
        JButton TwoPlayerButton = new JButton(icon2);

        JPanel panel = new JPanel();
        panel.setBounds(370, 500, 222, 99);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(OnePlayerButton);
        panel.add(TwoPlayerButton);

        OnePlayerButton.addActionListener(e -> {
            panel.setVisible(false);
            playerButtonsHelper();
            canvas.addKeyListener(Controller);    //adding the controller to the Canvas
            canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
            startGame = true;
        });

        TwoPlayerButton.addActionListener(e -> {
            panel.setVisible(false);
            playerButtonsHelper();
            player2AmmoPanel.setVisible(true);
            player2LifePanel.setVisible(true);
            canvas.addKeyListener(Controller);    //adding the controller to the Canvas
            canvas.addKeyListener(Controller2);    //adding the controller to the Canvas
            canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .
            multiMode = true;
            startGame = true;
        });

        panel.validate();
        return panel;
    }

    private void playerButtonsHelper() {
        BackgroundImageForStartMenu.setVisible(false);
        canvas.setVisible(true);
        scorePanel.setVisible(true);
        player1AmmoPanel.setVisible(true);
        player1LifePanel.setVisible(true);
        timePanel.setVisible(true);
    }

    private void setupUI() {
        scorePanel.setVisible(false);
        scorePanel.setBounds(450, 0, 115, 35);
        scorePanel.setBackground(Color.black);
        scoreCount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        scoreCount.setForeground(Color.white);
        scorePanel.add(scoreCount);

        timePanel.setVisible(false);
        timePanel.setBounds(325, 0, 115, 35);
        timePanel.setBackground(Color.black);
        time.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        time.setForeground(Color.white);
        timePanel.add(time);

        ImageIcon t = new ImageIcon("res/enemy_ships/enemyBlue2.png");
        Image timeImg = t.getImage();
        Image newTimeIcon = timeImg.getScaledInstance(28, 26, Image.SCALE_SMOOTH);
        ImageIcon newImgIcon = new ImageIcon(newTimeIcon);
        time.setIcon(newImgIcon);

        player1LifePanel.setVisible(false);
        player1LifePanel.setBounds(0, 0, 110, 35);
        player1LifePanel.setBackground(Color.black);
        player1LifeCount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        player1LifeCount.setForeground(Color.white);
        player1LifePanel.add(player1LifeCount);

        player1AmmoPanel.setVisible(false);
        player1AmmoPanel.setBounds(111, 0, 110, 35);
        player1AmmoPanel.setBackground(Color.black);
        player1AmmoCount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        player1AmmoCount.setForeground(Color.white);
        player1AmmoPanel.add(player1AmmoCount);

        player2LifePanel.setVisible(false);
        player2LifePanel.setBounds(880, 0, 110, 35);
        player2LifePanel.setBackground(Color.black);
        player2LifeCount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        player2LifeCount.setForeground(Color.white);
        player2LifePanel.add(player2LifeCount);

        player2AmmoPanel.setVisible(false);
        player2AmmoPanel.setBounds(768, 0, 110, 35);
        player2AmmoPanel.setBackground(Color.black);
        player2AmmoCount.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        player2AmmoCount.setForeground(Color.white);
        player2AmmoPanel.add(player2AmmoCount);

        ImageIcon icon = new ImageIcon("res/ammo_icon.png");
        Image img = icon.getImage();
        Image newIcon = img.getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        ImageIcon newIc = new ImageIcon(newIcon);
        player2AmmoCount.setIcon(newIc);
        player1AmmoCount.setIcon(newIc);

        gameOver.setLayout(new GridBagLayout());
        gameOver.setVisible(false);
        gameOver.setBounds(250, 250, 500, 500);
        gameOver.setBackground(Color.black);
        JLabel ko = new JLabel("Game Over!");
        ko.setFont(new Font("Bahnschrift", Font.PLAIN, 20));
        ko.setForeground(Color.white);
        ko.setHorizontalAlignment(JLabel.CENTER);
        gameOver.add(ko);
    }

    //Basic Model-View-Controller pattern
    private static void gameLoop() {
        // GAMELOOP

        // controller input  will happen on its own thread
        // So no need to call it explicitly

        if (multiMode) {
            gameWorld.setMultiplayerMode(true);
        }

        // model update
        gameWorld.Logic();

        // view update
        canvas.updateView();

        // Both these calls could be setup as  a thread but we want to simplify the game logic for you.
        //score update
        //frame.setTitle("Score =  " + gameWorld.getScore() + " " + "Ammo = " + gameWorld.getPlayer().getAmmo());
        scoreCount.setText("Score: " + gameWorld.getScore());
        time.setText(" " + gameWorld.getElapsedTime());

        player1LifeCount.setText(" " + gameWorld.getPlayer().getLives());
        player2LifeCount.setText(" " + gameWorld.getPlayer2().getLives());

        if (gameWorld.getPlayer().getAmmo() == -1) {
            player1AmmoCount.setForeground(Color.red);
            player1AmmoCount.setText(" R!");
        } else {
            player1AmmoCount.setForeground(Color.white);
            player1AmmoCount.setText(" " + gameWorld.getPlayer().getAmmo());
        }

        if (gameWorld.getPlayer2().getAmmo() == -1) {
            player2AmmoCount.setForeground(Color.red);
            player2AmmoCount.setText(" R!");
        } else {
            player2AmmoCount.setForeground(Color.white);
            player2AmmoCount.setText(" " + gameWorld.getPlayer2().getAmmo());
        }

        if (gameWorld.getPlayer().isDead()) {
            player1LifeCount.setForeground(Color.red);
            player1LifeCount.setText("DEAD");
        }

        if (gameWorld.getPlayer2().isDead()) {
            player2LifeCount.setForeground(Color.red);
            player2LifeCount.setText("DEAD");
        }
    }
}

/*
 * 
 * 

Hand shake agreement 
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,=+++
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,:::::,=+++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,:++++????+??
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,:,,:,:,,,,,,,,,,,,,,,,,,,,++++++?+++++????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,=++?+++++++++++??????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++?+++?++?++++++++++?????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,~+++++++++++++++????+++++++???????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,:===+=++++++++++++++++++++?+++????????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,~=~~~======++++++++++++++++++++++++++????????????????
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,::::,,,,,,=~.,,,,,,,+===~~~~~~====++++++++++++++++++++++++++++???????????????
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,,~~.~??++~.,~~~~~======~=======++++++++++++++++++++++++++????????????????II
:::::::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,,:=+++??=====~~~~~~====================+++++++++++++++++++++?????????????????III
:::::::::::::::::::::::::::::::::::::::::::::::::::,:,,,++~~~=+=~~~~~~==~~~::::~~==+++++++==++++++++++++++++++++++++++?????????????????IIIII
::::::::::::::::::::::::::::::::::::::::::::::::,:,,,:++++==+??+=======~~~~=~::~~===++=+??++++++++++++++++++++++++?????????????????I?IIIIIII
::::::::::::::::::::::::::::::::::::::::::::::::,,:+????+==??+++++?++====~~~~~:~~~++??+=+++++++++?++++++++++??+???????????????I?IIIIIIII7I77
::::::::::::::::::::::::::::::::::::::::::::,,,,+???????++?+?+++???7?++======~~+=====??+???++++++??+?+++???????????????????IIIIIIIIIIIIIII77
:::::::::::::::::::::::::::::::::::::::,,,,,,=??????IIII7???+?+II$Z77??+++?+=+++++=~==?++?+?++?????????????III?II?IIIIIIIIIIIIIIIIIIIIIIIIII
::::::::::::::::::::::::::::::,,,,,,~=======++++???III7$???+++++Z77ZDZI?????I?777I+~~+=7+?II??????????????IIIIIIIIIIIIIIIIIIIIII??=:,,,,,,,,
::::::::,:,:,,,,,,,:::~==+=++++++++++++=+=+++++++???I7$7I?+~~~I$I??++??I78DDDO$7?++==~I+7I7IIIIIIIIIIIIIIIIII777I?=:,,,,,,,,,,,,,,,,,,,,,,,,
++=++=++++++++++++++?+????+??????????+===+++++????I7$$ZZ$I+=~$7I???++++++===~~==7??++==7II?~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+++++++++++++?+++?++????????????IIIII?I+??I???????I7$ZOOZ7+=~7II?+++?II?I?+++=+=~~~7?++:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
+?+++++????????????????I?I??I??IIIIIIII???II7II??I77$ZO8ZZ?~~7I?+==++?O7II??+??+=====.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
?????????????III?II?????I?????IIIII???????II777IIII7$ZOO7?+~+7I?+=~~+???7NNN7II?+=+=++,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
????????????IIIIIIIIII?IIIIIIIIIIII????II?III7I7777$ZZOO7++=$77I???==+++????7ZDN87I??=~,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIII?II??IIIIIIIIIIIIIIIIIIIIIIIIIII???+??II7777II7$$OZZI?+$$$$77IIII?????????++=+.,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII?+++?IIIII7777$$$$$$7$$$$7IIII7I$IIIIII???I+=,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII???????IIIIII77I7777$7$$$II????I??I7Z87IIII?=,,,,,,,,,,,:,,::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777777777777I7I777777777~,,,,,,,+77IIIIIIIIIII7II7$$$Z$?I????III???II?,,,,,,,,,,::,::::::::,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
777777777777$77777777777+::::::::::::::,,,,,,,=7IIIII78ZI?II78$7++D7?7O777II??:,,,:,,,::::::::::::::,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$$$$$$$$$$$77=:,:::::::::::::::::::::::::::,,7II$,,8ZZI++$8ZZ?+=ZI==IIII,+7:,,,,:::::::::::::::::,:::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
$$$I~::::::::::::::::::::::::::::::::::::::::::II+,,,OOO7?$DOZII$I$I7=77?,,,,,,:::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::+ZZ?,$ZZ$77ZZ$?,,,,,::::::::::::::::::::::::::,::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::I$:::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,:,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,,,
:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::,,,,,,,,,,,,,,,,,,,,,,
                                                                                                                             GlassGiant.com
 * 
 * 
 */
