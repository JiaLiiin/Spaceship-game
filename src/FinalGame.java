
//飞机大战 一个主体j键攻击 WASD移动  子弹是enemy 要攻击bonus point 来加分
//做个主页 用鼠标

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.Image;
import javax.swing.ImageIcon; // for loading images




public class FinalGame extends JPanel implements MouseListener, ActionListener, KeyListener{
  Label l;
  Random r=new Random();
  Enemy enemy=new Enemy();
  int XCoord=r.nextInt(290); int YCoord=r.nextInt(20)+5;
 
  static boolean Bullet=false;


  ImageIcon Spaceship = new ImageIcon("13-138961_vector-spaces-ship-8-bit-spaceship-sprite.png");
  ImageIcon MenuBack = new ImageIcon("7e8ff56b0a33a7580db3fac27ce855f9.png");
  ImageIcon PlayBack = new ImageIcon("8-bit-weapon-bits-with-byte-background.png");
  ImageIcon HorizontalEnemy = new ImageIcon("hori.png");
  ImageIcon VerticalEnemy = new ImageIcon("verti.png");
  Image image;
  Image Background;
  
  
  enum direction
  {
    RIGHT,
    LEFT,
    UP,
    DOWN,
    STEADY
  }
  direction d=direction.STEADY;
  
  enum page
  {
    MAINMENU,
    PLAYING,
    INSTRUCTIONS,
    WINNING,
    LOSING,
    PAUSE
  }
  page p=page.MAINMENU;


  int Width=r.nextInt(15)+15;
  int counter=0;
  
  int MouseX;
  int MouseY;
 
  
  
  int PlayerX=190; int PlayerY=300; int MovementX=0; int MovementY=0; //player movement and velocity
  int BulletX, BulletY;
  
  
  int z=0;
  final int VEL=2;//speed control
  
  Timer t=new Timer(5,this);
  

  
  
  public FinalGame() { //activates listeners
    t.start();    
    addKeyListener(this);
    addMouseListener(this); 
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);   
    enemy.setEnemyCoord();


  }
  
  
  public void paintComponent(Graphics g) {
    
    //background
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, 400, 400);
    
    //main menu
    if (p==page.MAINMENU)
    { 
      image = MenuBack.getImage(); 
      g.drawImage(image, 0, 0, 500, 400, l);
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
      
      g.drawString("SPACE SHIPS", 80, 50);
    
      g.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
      g.drawString("Start", 170, 200);
      g.drawRect(110, 170 , 170, 40);
      
      g.drawString("How To Play", 130, 270);
      g.drawRect(110, 240,170,40);
      
    }
    //winning page
    else if (counter>=15)
    {
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
      g.drawString("Congratulations! You win!", 90, 175);
    }
    
    //pause
    else if (p==page.PAUSE && counter>=1)
    {
      Background = PlayBack.getImage();
      g.drawImage(Background, 0, 0, 400, 400, l);
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
      g.drawString("PAUSE", 120, 100);
      
      g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
      g.drawString("Press ESCAPE to Resume", 70, 200);
      g.drawRect(65, 175, 275, 35);

      g.drawString("Click To Go Back To Main Menu", 40, 275);
      g.drawRect(30, 250, 350, 35);
    }
    
    //playing game
    else if (p==page.PLAYING && counter<15)
    { 
    image = Spaceship.getImage(); 
    Background = PlayBack.getImage();
    //player
    
    
    g.drawImage(Background, 0, 0, 400, 400, l);
    g.drawImage(image, PlayerX, PlayerY, 30, 30, l);
    //boundary
    if (PlayerX>=380) PlayerX=380;
    else if(PlayerX<=0) PlayerX=0;
    if (PlayerY<=80) PlayerY=80;
    else if(PlayerY>=380) PlayerY=380;
    
    g.setColor(Color.WHITE);
    //Bullet
    if (Bullet==true&&BulletY>0)
    {
      g.fillRect(BulletX, BulletY, 3, 5);
      
      
    }
    
    //bonus point
    g.setColor(Color.RED);
    g.fillOval(XCoord, YCoord, Width, Width);
    
    g.drawString("Score: "+counter, 20, 10);
    
    //collision of bullet and bonus
    //plus 3 because bullet is not a line but a rectangle
    if((BulletX+3>=XCoord&&BulletX<=XCoord+Width)&&(BulletY>=YCoord&&BulletY<=Width+YCoord))
    {
      counter++;
      
      XCoord=r.nextInt(290);
      YCoord=r.nextInt(20)+5;
      Width=r.nextInt(15)+15;

      Bullet=false;
      z=0;
    }
    else if (BulletY<=0) 
    {
      Bullet=false;
      
    }
    

    //enemy
    int[] secondList = enemy.getList();
    

    for(int repeat=0; repeat<counter && counter<15; repeat++)
    {
     

      if(repeat%2==0)
      {//enemy going horizontally
        image = HorizontalEnemy.getImage(); 
        
        g.drawImage(image, z, secondList[repeat], 30, 30, l);
       
        if (z>=400)
        {
          z=0;
        }
        
      //horizontal collision of enemy and player
        
        if((z+20>=PlayerX&&z<=PlayerX+30)&&(secondList[repeat]+20>=PlayerY&&secondList[repeat]<=PlayerY+30))
        {
          p=page.LOSING;
          Bullet=false;
        }
        
      }
      

      else
      {//enemy going vertically
        image = VerticalEnemy.getImage(); 

        g.drawImage(image, secondList[repeat], z, 30, 30, l);
        if (z>=400)
        {
          z=0;
        }
        //vertical collision of enemy and player
        if((secondList[repeat]+20>=PlayerX&&secondList[repeat]<=PlayerX+30)&&(z+20>=PlayerY&&z<=PlayerY+30))
        {
          p=page.LOSING;
          Bullet=false; 
        }
        
        
      }
      
    }
    
    
  }
    //losing page

    else if (p==page.LOSING)
    {
      g.setColor(Color.WHITE);
      g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
      g.drawString("You earned "+counter+" points", 120, 175);
      g.drawString("Press \"R\"to restart", 127, 195);
    }
    //how to play page
    else if(p==page.INSTRUCTIONS)
    {
      g.setColor(Color.WHITE);
      
      g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
      g.drawString("Press \"R\" to restart", 10, 50);
      g.drawString("Press \"J\" to shoot bullet, you can only shoot if there's no bullet", 10, 70);
      g.drawString("on the screen", 10, 90);
      g.drawString("Press \"W\" to move up", 10, 110);
      g.drawString("Press \"A\" to move left", 10, 130);
      g.drawString("Press \"S\" to move down", 10, 150);
      g.drawString("Press \"D\" to move right", 10, 170);
      
      g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
      g.drawString("Earn 15 points to win!", 90, 30);
      //back button
      g.drawString("Back", 10, 380);
      g.drawRect(5, 355, 60, 35);
    }

  }
  
  
  
  public void actionPerformed(ActionEvent e) {


       
    if (d==direction.LEFT)
    {
      MovementX=-1;
    }
    if (d==direction.RIGHT)
    {
      MovementX=1;
    }
    if (d==direction.UP) 
    {
      MovementY=-1;
    }
    if (d==direction.DOWN)
    {
      MovementY=1;
    }
    if (Bullet==true) 
    {
      BulletY-=4;
    }
    if (d==direction.UP&&Bullet==false) 
    {
      MovementY=-1;
    }
    if (d==direction.LEFT&&Bullet==false) 
    {
      MovementX=-1;
    }
    if (d==direction.RIGHT&&Bullet==false) 
    {
      MovementX=1;
    }
    if (d==direction.DOWN && Bullet==false)
    {
      MovementY=1;
    }
    
  //player movement and enemy movement
    z+=VEL;
    PlayerX+=MovementX;
    PlayerY+=MovementY;
    
    //making sure bullet is shooting from the middle

    
    if (Bullet==false) 
    {
    BulletX=PlayerX+15;
    BulletY=PlayerY;
    }

  
    repaint();
  }
  
  //mouse listener
  public void mouseClicked(MouseEvent e) {
  }
    
  public void mousePressed(MouseEvent e) {
    //this makes sure mouse control only works in the beginning
    if (counter==0 && (p==page.MAINMENU||p==page.INSTRUCTIONS))
    {
    //click on start
      if ((e.getX()>=110 && e.getX()<=280)&&(e.getY()>=170&&e.getY()<=210))
      {
       p=page.PLAYING;
      }
    //click on how to play
      else if((e.getX()>=110 && e.getX()<=280)&&(e.getY()>=240&&e.getY()<=280))
      {
        p=page.INSTRUCTIONS;
      
      }
    }
      //brings back to main menu
     if (counter==0 && p==page.INSTRUCTIONS)
      {
        if ((e.getX()>=5 && e.getX()<=65)&&(e.getY()>=355&&e.getY()<=390))
        {
          p=page.MAINMENU;
        }
      }
     //brings from pause to main menu 30, 250, 350, 35
     if (p==page.PAUSE &&((e.getX()>=30 && e.getX()<=380)&&(e.getY()>=250&&e.getY()<=285)))
     {
       p=page.MAINMENU;
       counter=0;
     }
    }
    
    
  

  public void mouseReleased(MouseEvent e) {
    
  }

  public void mouseEntered(MouseEvent e) {
    
  }
  
  public void mouseExited(MouseEvent e) {
    
  }
  
  
  
  public void keyTyped(KeyEvent e)
  {
 
  }

//key listener
  public void keyPressed(KeyEvent e)
  {
    int c=e.getKeyCode();
    
  //left
    if (c == KeyEvent.VK_A) 
    {
     //left=true;
     d=direction.LEFT;
    }
    //stimulate win
    if (c == KeyEvent.VK_P) 
    {
     counter=15;

    }
    //up
    if (c == KeyEvent.VK_W) 
    {
      //up=true;
      d=direction.UP;
    }
    
    //right
    if (c == KeyEvent.VK_D) 
    {
     //right=true;
     d=direction.RIGHT;
    }
    
    //down
    if (c == KeyEvent.VK_S) 
    {
      d=direction.DOWN;
    }
    //game restart
    if (c == KeyEvent.VK_R)
    {
      enemy.setEnemyCoord();
      
      p=page.PLAYING;
      counter=0;
      
    }
    
    //bullet
    if (c == KeyEvent.VK_J&&Bullet==false)
    {

      Bullet=true;

    }
    
    if (c==KeyEvent.VK_ESCAPE && p!=page.PAUSE && counter>=1 && p!=page.LOSING)
    {
      p=page.PAUSE;
    }
    else if (c==KeyEvent.VK_ESCAPE && p==page.PAUSE)
    {
      p=page.PLAYING;
    }
    
 

  }


  public void keyReleased(KeyEvent e)
  {  
    int c=e.getKeyCode();
    
    if (c == KeyEvent.VK_D)
     {
      //right=false;
      d=direction.STEADY;
     }
    else if (c == KeyEvent.VK_A )
     {
      //left=false;
      d=direction.STEADY;
     }
    else if (c == KeyEvent.VK_W )
    {
      //up=false;
      d=direction.STEADY;
    }
    else if (c == KeyEvent.VK_S )
    {
      //down=false;
      d=direction.STEADY;
    }

    
    
      MovementY=0;

      MovementX=0;

  }

  public static void main(String[] args) {
    
    JFrame application = new JFrame();
   

    application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    application.setLocation(588,280);

    FinalGame myPanel = new FinalGame();
    
    application.add(myPanel);

    application.setSize(400, 420);

    application.setVisible(true);
    
  }



}
