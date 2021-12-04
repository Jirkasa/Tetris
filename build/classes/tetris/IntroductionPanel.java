/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author sator
 */
public class IntroductionPanel extends JPanel implements ActionListener {
    private JButton playButton;
    private PlaySpace playSpace;
    
    public IntroductionPanel(int width, int height, PlaySpace playSpace) {
        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(false);
        
        this.setLayout(new GridBagLayout());
        
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);
        
        JPanel headingContainer = new JPanel();
        JLabel heading = new JLabel("Tetris");
        heading.setFont(heading.getFont().deriveFont(45f));
        heading.setForeground(new Color(10, 56, 55));
        headingContainer.add(heading);
        headingContainer.setOpaque(false);
        container.add(headingContainer);
        
        JPanel buttonContainer = new JPanel();
        playButton = new JButton("  hrát  ");
        playButton.setFont(playButton.getFont().deriveFont(15f));
        playButton.setBackground(new Color(145, 243, 89));
        playButton.setBorder(BorderFactory.createCompoundBorder(
               BorderFactory.createLineBorder(new Color(128, 210, 81), 3),
               BorderFactory.createLineBorder(new Color(145, 243, 89), 5)));
        playButton.addActionListener(this);
        playButton.setFocusable(false);
        buttonContainer.add(playButton);
        buttonContainer.setOpaque(false);
        container.add(buttonContainer);
        
        this.add(container);
        
        this.playSpace = playSpace;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.playButton) {
            this.setVisible(false);
            this.playSpace.startGame();
        }
    }
}
