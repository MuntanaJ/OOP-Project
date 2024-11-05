package after_life;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sceen1 extends JPanel implements Runnable {
    private BufferedImage Bgsceen1;
    private Player player;
    private List<Monster> monsters;
    
    private List<HealMonster> healMonsters;
    private Game game;
    private boolean gameOver = false;

    private final int NUM_MONSTERS = 5;
    private final int NUM_HEAL_MONSTERS = 1;
    private Thread gameThread;
    private final int FPS = 60;
    private boolean leftPressed, rightPressed;
    private int score = 0;
    private long startTime;
    private boolean phase2 = false;
    private boolean phase3 = false;
    private boolean win = false;
    private int health = 100;
    
    public static BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(Sceen1.class.getResourceAsStream(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Sceen1(Game game) {
        this.game = game;
        Bgsceen1 = loadSprite("S1.jpg");
        player = new Player();
        monsters = new ArrayList<>();
        healMonsters = new ArrayList<>();

        this.setFocusable(true);
        this.requestFocusInWindow(); // Ensure this panel gets focus
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e, true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyPress(e, false);
            }
        });

        startGameThread(); // Start the game thread
    }

    private void handleKeyPress(KeyEvent e, boolean isPressed) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_A) {
            leftPressed = isPressed;
        } else if (keyCode == KeyEvent.VK_D) {
            rightPressed = isPressed;
        }
    }

    private void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
        startTime = System.nanoTime(); // Start the timer
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // One second divided by FPS
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            if (monsters.isEmpty() && getWidth() > 0 && getHeight() > 0) {
                for (int i = 0; i < NUM_MONSTERS; i++) {
                    monsters.add(new Monster(getWidth(), getHeight()));
                }
                for (int i = 0; i < NUM_HEAL_MONSTERS; i++) {
                    healMonsters.add(new HealMonster(getWidth(), getHeight()));
                }
            }

            update();
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException ex) {
                Logger.getLogger(Sceen1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void decreaseHealth(){
        health -= 10;
        if(health <= 0){
            health = 0;
            gameOver = true;
            game.showDie(score);
        }
    }
    
    public void increaseHealth(){
        health += 20;
        if(health > 100){
            health = 100;
        }
    }
   

    public void update() {
        if(gameOver){
            return;
        }
        if (leftPressed) {
            player.moveLeft();
        } else if (rightPressed) {
            player.moveRight();
        }

        long elapsedTime = (System.nanoTime() - startTime) / 1000000000;
        if (elapsedTime > 30 && !phase2) { // Trigger phase 2 after 30 seconds
            phase2 = true;
            for (Monster monster : monsters) {
                monster.increaseSpeed(); // Increase the speed of all monsters
                player.increaseSpeed();
            }
        }
        else if(elapsedTime > 60 && !phase3){
            phase3 = true;
            for (Monster monster : monsters) {
                monster.increaseSpeed(); // Increase the speed of all monsters
                player.increaseSpeed();
            }
        }
        else if(elapsedTime > 120 && !win){
            win = true;
            gameOver = true;
            game.showWin(score);
        }
        
//        for(Monster monster : monsters){
//            monster.update();
//            
//            if(monster.getY() > 600){
//                decreaseHealth();
//                monsterToRemove.add(monster);
//                monsterToAdd.add(new Monster(getWidth(),getHeight()));
//            }
//            
//            if(player.getBounds().intersects(monster.getBounds())){
//                score+=10;
//                monsterToRemove.add(monster);
//                monsterToAdd.add(new Monster(getWidth(),getHeight()));
//            }
//        }
//        
//        monsters.removeAll(monsterToRemove);
//        monsters.addAll(monsterToAdd);
//        
//        List<HealMonster> healMonsterToRemove = new ArrayList<>();
//        List<HealMonster> healMonsterToAdd = new ArrayList<>();
//        
//        for(HealMonster healmonster : healMonsters){
//            increaseHealth();
//            healMonsterToRemove.add(healmonster);
//            healMonsterToAdd.add(new HealMonster(getWidth(),getHeight()));  
//        }
//        
//        healMonsters.removeAll(healMonsterToRemove);
//        healMonsters.addAll(healMonsterToAdd);

        //add monster
      for (int i = 0; i < monsters.size(); i++) {
           Monster monster = monsters.get(i);
            monster.update();

            if (monster.getY() > 600) {
               decreaseHealth(); // Decrease player health if monster passes screen
               monsters.remove(i);
               i--;
               monsters.add(new Monster(getWidth(), getHeight()));
           }
          // Check for collision with player
           if (player.getBounds().intersects(monster.getBounds())) {
               score += 10; // Increment the score
               monsters.remove(i); // Remove the monster from the list
               i--; // Adjust index after removal
               // Add a new monster
                monsters.add(new Monster(getWidth(), getHeight()));
           }
       }
        

       for (int i = 0; i < healMonsters.size(); i++) {
           HealMonster healMonster = healMonsters.get(i);
            healMonster.update();

           if (player.getBounds().intersects(healMonster.getBounds())) {
               increaseHealth();
               healMonsters.remove(i);
               i--;
                healMonsters.add(new HealMonster(getWidth(), getHeight()));
          }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image if it loaded successfully
        if (Bgsceen1 != null) {
            g.drawImage(Bgsceen1, 0, 0, getWidth(), getHeight(), this);
        } else {
            System.out.println("Background image not loaded!");
        }

        // Draw the player
        player.paint(g);

        // Draw monsters
        for (Monster monster : monsters) {
            monster.paint(g);
        }
        for (HealMonster healMonster : healMonsters) {
            healMonster.paint(g);
        }

        // Draw the score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 20);

        // Draw Health
        g.setColor(Color.GREEN);
        g.fillRect(10, 40, health, 10);
        g.setColor(Color.WHITE);
        g.drawRect(10, 40, 100, 10);
        g.drawString("Health" + health, 120, 50);

        // Display "Phase 2" message
        if (phase2) {
            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Phase 2!", getWidth() / 2 - 50, 50);
        }
        if(phase3){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Phase 3!", getWidth() / 2 - 50, 50);
        }
    }
}

