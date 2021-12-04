/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author sator
 */
public class TetrisFrame extends JFrame {
    public TetrisFrame(PlaySpace playSpace) {
        this.setTitle("Tetris");
        this.setIconImage(new ImageIcon("images/icon.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(146, 169, 132));
        this.setVisible(true);
        
        this.addKeyListener(playSpace);
    }
}
