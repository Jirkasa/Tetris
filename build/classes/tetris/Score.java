/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author sator
 */
public class Score extends JLabel {
    private int score = 0;
    public Score() {
        this.setText("Skóre: 0");
        this.setForeground(new Color(10, 56, 55));
    }
    
    public void increase(int amount) {
        score += amount;
        this.setText("Skóre: " + score);
    }
    
    public int getValue() {
        return score;
    }
    
    public void reset() {
        score = 0;
        this.setText("Skóre: 0");
    }
}
