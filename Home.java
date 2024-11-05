/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package after_life;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Home extends JPanel implements ActionListener{
    private Image bgHome;
    private JButton jbtstart = new JButton("Start");
    private int buttonW = 200;
    private int buttonH = 100;
    private Game frame;

    public Home(Game frame) {
        this.frame = frame;
        try {
            // Load the image from the resources folder
            //URL imageURL = new URL("https://media.istockphoto.com/id/1333010525/vector/simple-flat-pixel-art-illustration-of-cartoon-outdoor-landscape-background-pixel-arcade.jpg?s=170667a&w=0&k=20&c=6Ga9NO3Tl_eSOMCWTbH7qY4LEra7aUwCQZXldsHYtQ0=");
            //bgHome = new ImageIcon(imageURL).getImage();
            bgHome = new ImageIcon(getClass().getResource("BG.jpg")).getImage();
            setLayout(null);
            
            jbtstart.setBounds(400,350,buttonW,buttonH);
            jbtstart.addActionListener(this);
            add(jbtstart);
        } catch (Exception e) {
            System.out.println("Bro!");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image if it loaded successfully
        if (bgHome != null) {
            g.drawImage(bgHome, 0, 0, getWidth(), getHeight(), this);
        }
        else{
            System.out.println("OH NO");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Start button clicked!");
        frame.showSceen1();
    }
}


