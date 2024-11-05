package after_life;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Monster {
    private BufferedImage monster;
    private int x, y;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 100;
    private static double SPEED = 2;
    private int screenWidth, screenHeight;

    public static BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(Monster.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
   public Monster(Rectangle bounds) {
    this.x = bounds.x;
    this.y = bounds.y;
}

    public Monster(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        monster = loadSprite("monster.png");
        x = (int) (Math.random() * (screenWidth - WIDTH));
        y = -HEIGHT;
    }

    public void update() {
        y += SPEED;
        if (y > screenHeight) {
            x = (int) (Math.random() * (screenWidth - WIDTH));
            y = -HEIGHT;
        }
    }

    public void paint(Graphics g) {
        if (monster != null) {
            g.drawImage(monster, x, y, WIDTH, HEIGHT, null);
        } else {
            System.out.println("Monster image not loaded!");
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public static void increaseSpeed(){
        SPEED+=0.5;
    }
    
    public int getY(){
        return y;
    }
}

