/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package after_life;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game extends JFrame{
    
   public Game(){
       setTitle("After Life");
       setSize(1000, 750);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setLocationRelativeTo(null);
       showhomeSceen();
   }
   
   public void showhomeSceen(){
       getContentPane().removeAll();
       Home home = new Home(this);
       add(home);
       revalidate();
       repaint();
   }
    
    public void showSceen1() {
        getContentPane().removeAll();  // Clear current content
        Sceen1 sceen1 = new Sceen1(this);
        add(sceen1);           // Add Home panel with a reference to this frame
        revalidate();
        repaint();
        sceen1.requestFocusInWindow();
    }
    
    public void showDie(int score){
        getContentPane().removeAll();
        Die die = new Die(score);
        add(die);
        revalidate();
        repaint();
    }
    
    public void showWin(int score){
        getContentPane().removeAll();
        Win win = new Win(score);
        add(win);
        revalidate();
        repaint();
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game frame = new Game();
            frame.setVisible(true);
        });
    }
}