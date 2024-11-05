
package after_life;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class HealMonster {
    private BufferedImage monster;
    private int x,y;
    private static int SPEED = 2;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private int screenWidth,screenHeight;
    
    public static BufferedImage loadSprite(String path) { 
        try{
            return ImageIO.read(HealMonster.class.getResourceAsStream(path)); 
        }catch(IOException e){ 
            e.printStackTrace(); return null; 
        } 
    }
    
    public HealMonster(int screenWidth,int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        monster = loadSprite("healMonster.png");
        x = (int)(Math.random() * (screenWidth - WIDTH));
        y = -HEIGHT;
    }
    
    public void update(){
        y += SPEED;
        if (y > screenHeight){
            x = (int)(Math.random() * (screenWidth - WIDTH));
            y = -HEIGHT;
        }
    }
    
    public void paint(Graphics g){
        if(monster != null){
            g.drawImage(monster,x,y,WIDTH,HEIGHT,null);
        }
        else{
            System.out.println("Monster Heal not load!");
        }
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x,y,WIDTH,HEIGHT);
    }
    
    public static void increaseSpeed(){
        SPEED++;
    }
    
}
