/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sator
 */
public abstract class GameLoop extends Thread {
    private final long TIME_BETWEEN_UPDATES = 1000L / 60L;
    
    public GameLoop() {
        
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = System.currentTimeMillis();
        
        while (true) {
            try {
                Thread.sleep(lastUpdateTime+TIME_BETWEEN_UPDATES-currentTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            currentTime = System.currentTimeMillis();
            
            update(currentTime-lastUpdateTime);
            render();
            
            lastUpdateTime = System.currentTimeMillis();
        }
    }
    
    abstract void update(float dt);
    abstract void render();
}
