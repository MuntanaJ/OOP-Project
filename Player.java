/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package after_life;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player {
    private BufferedImage player;
    private int x, y;
    private boolean facingRight = true; // Keep track of the direction the player is facing
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    private static int SPEED = 5;

    public static BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(Player.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Player() {
        player = loadSprite("player.png");
        x = 0; // Initial X position
        y = 500; // Initial Y position
    }

    public void moveLeft() {
        if (x - SPEED >= 0) { // Ensure player does not go off the left side
            x -= SPEED;
            facingRight = false; // Change direction
        }
    }

    public void moveRight() {
        if (x + SPEED <= 1000 - WIDTH) { // Ensure player does not go off the right side
            x += SPEED;
            facingRight = true; // Change direction
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (player != null) {
            if (facingRight) {
                g2d.drawImage(player, x, y, WIDTH, HEIGHT, null); // Draw normally if facing right
            } else {
                g2d.drawImage(player, x + WIDTH, y, -WIDTH, HEIGHT, null); // Flip image horizontally if facing left
            }
        } else {
            System.out.println("Player image not loaded!");
        }
    }
    
    public static void increaseSpeed(){
        SPEED+=0.5;
    }
    
    public Rectangle getBounds() { 
        return new Rectangle(x, y, WIDTH, HEIGHT); 
    }
    
}
