/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author sator
 */
public class NextBrick extends JPanel {
    private int next;
    private Random rand;
    
    public NextBrick() {
        rand = new Random();
        next = rand.nextInt(7);
        
        this.setPreferredSize(new Dimension(Constants.BRICK_WIDTH*4, Constants.BRICK_WIDTH*4));
        this.setBorder(BorderFactory.createLineBorder(new Color(15, 95, 92)));
        this.setBackground(new Color(223, 238, 214));
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        draw(g2D);
        drawGrid(g2D);
    }
    
    public int getNext() {
        int nextBrick = this.next;
        this.next = rand.nextInt(7);
        return nextBrick;
    }
    
    private void drawGrid(Graphics2D g) {
        g.setColor(new Color(186, 186, 186));
        g.setStroke(new BasicStroke(1));
        
        for (int i = 1; i < 4; i++) {
            g.drawLine(0, i * Constants.BRICK_WIDTH, Constants.PLAY_SPACE_WIDTH, i * Constants.BRICK_WIDTH);
        }
        for (int i = 1; i < 4; i++) {
            g.drawLine(i * Constants.BRICK_WIDTH, 0, i * Constants.BRICK_WIDTH, Constants.PLAY_SPACE_HEIGHT);
        }
    }
    
    private void draw(Graphics2D g) {
        g.setColor(new Color(15, 95, 92));
        switch(this.next) {
            case 0:
                g.fillRect(20, 20, 20, 20);
                g.fillRect(40, 20, 20, 20);
                g.fillRect(40, 40, 20, 20);
                g.fillRect(20, 40, 20, 20);
                break;
            case 1:
                g.fillRect(0, 40, 20, 20);
                g.fillRect(20, 40, 20, 20);
                g.fillRect(40, 40, 20, 20);
                g.fillRect(40, 20, 20, 20);
                break;
            case 2:
                g.fillRect(0, 40, 20, 20);
                g.fillRect(20, 40, 20, 20);
                g.fillRect(40, 40, 20, 20);
                g.fillRect(0, 20, 20, 20);
                break;
            case 3:
                g.fillRect(0, 20, 20, 20);
                g.fillRect(20, 20, 20, 20);
                g.fillRect(20, 40, 20, 20);
                g.fillRect(40, 40, 20, 20);
                break;
            case 4:
                g.fillRect(0, 40, 20, 20);
                g.fillRect(20, 40, 20, 20);
                g.fillRect(20, 20, 20, 20);
                g.fillRect(40, 20, 20, 20);
                break;
            case 5:
                g.fillRect(20, 20, 20, 20);
                g.fillRect(0, 40, 20, 20);
                g.fillRect(20, 40, 20, 20);
                g.fillRect(40, 40, 20, 20);
                break;
            case 6:
                g.fillRect(0, 40, 20, 20);
                g.fillRect(20, 40, 20, 20);
                g.fillRect(40, 40, 20, 20);
                g.fillRect(60, 40, 20, 20);
                break;
        }
    }
}
