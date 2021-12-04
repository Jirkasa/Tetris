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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author sator
 */
public class PlaySpace extends JPanel implements KeyListener {
    private JPanel gamePanel;
    private ResultsPanel resultsPanel;
    private TetrisFrame tetrisFrame;
    
    private boolean[][] table = new boolean[10][20];
    
    private CurrentBrick currentBrick;
    private Score score;
    private Lines lines;
    private float timePassed = 0;
    private float speed = 1000;
    
    private boolean gameRunning = false;
    
    public PlaySpace(NextBrick nextBrick, Score score, Lines lines, JPanel gamePanel) {
        this.setPreferredSize(new Dimension(Constants.PLAY_SPACE_WIDTH, Constants.PLAY_SPACE_HEIGHT));
        this.setBorder(BorderFactory.createLineBorder(new Color(15, 95, 92)));
        this.setBackground(new Color(223, 238, 214));
        
        // fill table
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = false;
            }
        }
        
        currentBrick = new CurrentBrick(this, nextBrick);
        this.score = score;
        this.lines = lines;
        
        this.gamePanel = gamePanel;
    }
    
    public void addTetrisFrame(TetrisFrame tetrisFrame) {
        this.tetrisFrame = tetrisFrame;
    }
    
    public void addResultsPanel(ResultsPanel resultsPanel) {
        this.resultsPanel = resultsPanel;
    }
    
    public void update(float dt) {
        if (!gameRunning) return;
        timePassed += dt;
        if (timePassed >= speed) {
            timePassed = timePassed - speed;
            currentBrick.moveDown();
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!gameRunning) return;
        
        Graphics2D g2D = (Graphics2D) g;
        
        drawBricks(g2D);
        currentBrick.draw(g2D);
        drawGrid(g2D);
    }
    
    private void drawGrid(Graphics2D g) {
        g.setColor(new Color(186, 186, 186));
        g.setStroke(new BasicStroke(1));
        
        for (int i = 1; i < 20; i++) {
            g.drawLine(0, i * Constants.BRICK_WIDTH, Constants.PLAY_SPACE_WIDTH, i * Constants.BRICK_WIDTH);
        }
        for (int i = 1; i < 10; i++) {
            g.drawLine(i * Constants.BRICK_WIDTH, 0, i * Constants.BRICK_WIDTH, Constants.PLAY_SPACE_HEIGHT);
        }
    }
    
    private void drawBricks(Graphics2D g) {
        g.setColor(new Color(15, 95, 92));
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                if (table[i][j]) {
                    g.fillRect(i*Constants.BRICK_WIDTH, j*Constants.BRICK_WIDTH, Constants.BRICK_WIDTH, Constants.BRICK_WIDTH);
                }
            }
        }
    }
    
    public void startGame() {
        gameRunning = true;
        gamePanel.setVisible(true);
        
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                table[i][j] = false;
            }
        }
        
        currentBrick.setNew();
        currentBrick.playStartSound();
        
        score.reset();
        lines.reset();
    }
    
    public void gameOver() {
        gameRunning = false;
        resultsPanel.setResults(score.getValue(), lines.getValue());
        resultsPanel.setVisible(true);
        resultsPanel.setPreferredSize(new Dimension(gamePanel.getWidth(), gamePanel.getHeight()));
        gamePanel.setVisible(false);
        tetrisFrame.add(resultsPanel);
    }
    
    public boolean isBrickAtCoordinates(int x, int y) {
        if (x < 0 || y < 0) return false;
        return table[x][y];
    }
    
    public void setBrickAtCoordinates(int x, int y, boolean value) {
        table[x][y] = value;
    }
    
    public void increaseSpeed() {
        speed -= speed/200;
    }
    
    public void increaseScore(int amount) {
        score.increase(amount);
    }
    
    public void increaseLines(int amount) {
        lines.increase(amount);
    }
    
    public int removeFullLines() {
        int cleared = 0;
        for (int i = 19; i >= 0; i--) {
            boolean clearLine = true;
            for (int j = 0; j < 10; j++) {
                if (!this.table[j][i]) {
                    clearLine = false;
                    break;
                }
            }
            if (clearLine) {
                cleared++;
                for (int j = 0; j < 10; j++) {
                    this.table[j][i] = false;
                }
                for (int j = i-1; j >= 0; j--) {
                    for (int k = 0; k < 10; k++) {
                        if (this.table[k][j]) {
                            this.table[k][j] = false;
                            this.table[k][j+1] = true;
                        }
                    }
                }
                i++;
            }
        }

        return cleared;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameRunning) return;
        switch (e.getKeyCode()) {
            case 37:
            case 65:
                currentBrick.moveHorizontal(true);
                break;
            case 38:
            case 87:
                currentBrick.rotate();
                break;
            case 39:
            case 68:
                currentBrick.moveHorizontal(false);
                break;
            case 40:
            case 83:
                timePassed = 0;
                currentBrick.moveDown();
                break;
            case 32:
                currentBrick.put();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
