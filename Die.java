/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package after_life;

import java.awt.Color;
import java.awt.Font;
import static java.awt.Font.BOLD;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Die extends JPanel {
    private Image bgDie;
    private int score;

    public Die(int score) {
        this.score = score;
        try {
            // Load the image from the resources folder
            bgDie = new ImageIcon(getClass().getResource("bgD.png")).getImage();
            if (bgDie == null) {
                System.out.println("Die image not found!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading die image!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the die image if it loaded successfully
        if (bgDie != null) {
            g.drawImage(bgDie, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("Die image not loaded!");
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Score: "+score, 420 , 350);
    }
}
