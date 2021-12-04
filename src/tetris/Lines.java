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
public class Lines extends JLabel {
    private int lines = 0;
    
    public Lines() {
        lines = 0;
        this.setText("Řady: 0");
        this.setForeground(new Color(10, 56, 55));
    }
    
    public void increase(int amount) {
        lines += amount;
        this.setText("Řady: " + lines);
    }
    
    public int getValue() {
        return lines;
    }
    
    public void reset() {
        lines = 0;
        this.setText("Řady: 0");
    }
}
