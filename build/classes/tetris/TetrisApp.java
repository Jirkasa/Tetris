/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import kuusisto.tinysound.TinySound;

/**
 *
 * @author sator
 */
public class TetrisApp extends GameLoop {
    private PlaySpace playSpace;
    private TetrisFrame tetrisFrame;
    
    public TetrisApp() {
        TinySound.init();
        
        // game panel
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout());
        gamePanel.setOpaque(false);
        gamePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // next brick window
        NextBrick nextBrick = new NextBrick();
        // score
        Score score = new Score();
        // lines
        Lines lines = new Lines();
        // play space
        playSpace = new PlaySpace(nextBrick, score, lines, gamePanel);
        // window
        tetrisFrame = new TetrisFrame(playSpace);
        playSpace.addTetrisFrame(tetrisFrame);
        gamePanel.add(playSpace, BorderLayout.CENTER);
        // side panel
        JPanel container = new JPanel();
        container.setOpaque(false);
        JPanel sidePanel = new JPanel();
        sidePanel.setOpaque(false);
        container.add(sidePanel);
        sidePanel.setLayout(new GridLayout(0, 1));
        sidePanel.add(nextBrick);
        JPanel statsContainer = new JPanel();
        statsContainer.setLayout(new BoxLayout(statsContainer, BoxLayout.Y_AXIS));
        statsContainer.setOpaque(false);
        statsContainer.add(score);
        statsContainer.add(lines);
        sidePanel.add(statsContainer);
        gamePanel.add(container, BorderLayout.EAST);
        
        
        // introduction panel
        IntroductionPanel introductionPanel = new IntroductionPanel(gamePanel.getWidth(), gamePanel.getHeight(), playSpace);
        
        // results panel
        ResultsPanel resultsPanel = new ResultsPanel(gamePanel.getWidth(), gamePanel.getHeight(), playSpace);
        resultsPanel.setVisible(false);
        playSpace.addResultsPanel(resultsPanel);
        
        tetrisFrame.add(gamePanel);
        tetrisFrame.pack();
        gamePanel.setVisible(false);
        tetrisFrame.add(introductionPanel);
    }

    @Override
    void update(float dt) {
        playSpace.update(dt);
    }

    @Override
    void render() {
        playSpace.repaint();
    }
}
