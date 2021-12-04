/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tetris;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 *
 * @author sator
 */
public class CurrentBrick {
    private int[][] bricks = new int[4][2];
    private PlaySpace playSpace;
    private NextBrick nextBrick;
    
    private int brickType = 0;
    private int rotationState = 0;
    Sound fullLineSound;
    Sound putBrickSound;
    Sound failSound;
    
    public CurrentBrick(PlaySpace playSpace, NextBrick nextBrick) {
        for (int[] brick : bricks) {
            brick[0] = 0;
            brick[1] = 0;
        }
        
        this.playSpace = playSpace;
        this.nextBrick = nextBrick;
        
        setNew();
        
        this.fullLineSound = TinySound.loadSound(new File("sounds/fullLine.wav").getAbsoluteFile());
        this.putBrickSound = TinySound.loadSound(new File("sounds/putBrick.wav").getAbsoluteFile());
        this.failSound = TinySound.loadSound(new File("sounds/fail.wav").getAbsoluteFile());
    }
    
    public void draw(Graphics2D g) {
        g.setColor(new Color(15, 95, 92));
        
        for (int[] brick : bricks) {
            g.fillRect(brick[0] * Constants.BRICK_WIDTH, brick[1] * Constants.BRICK_WIDTH, Constants.BRICK_WIDTH, Constants.BRICK_WIDTH);
        }
        nextBrick.repaint();
    }
    
    public void setNew() {
        this.rotationState = 0;

        switch (this.nextBrick.getNext()) {
            case 0:
                this.bricks[0][0] = 4;
                this.bricks[0][1] = -1;
                this.bricks[1][0] = 5;
                this.bricks[1][1] = -1;
                this.bricks[2][0] = 5;
                this.bricks[2][1] = -2;
                this.bricks[3][0] = 4;
                this.bricks[3][1] = -2;
                this.brickType = 0;
                break;
            case 1:
                this.bricks[0][0] = 4;
                this.bricks[0][1] = -1;
                this.bricks[1][0] = 5;
                this.bricks[1][1] = -1;
                this.bricks[2][0] = 6;
                this.bricks[2][1] = -1;
                this.bricks[3][0] = 6;
                this.bricks[3][1] = -2;
                this.brickType = 1;
                break;
            case 2:
                this.bricks[0][0] = 4;
                this.bricks[0][1] = -1;
                this.bricks[1][0] = 5;
                this.bricks[1][1] = -1;
                this.bricks[2][0] = 6;
                this.bricks[2][1] = -1;
                this.bricks[3][0] = 4;
                this.bricks[3][1] = -2;
                this.brickType = 2;
                break;
            case 3:
                this.bricks[0][0] = 4;
                this.bricks[0][1] = -2;
                this.bricks[1][0] = 5;
                this.bricks[1][1] = -2;
                this.bricks[2][0] = 5;
                this.bricks[2][1] = -1;
                this.bricks[3][0] = 6;
                this.bricks[3][1] = -1;
                this.brickType = 3;
                break;
            case 4:
                this.bricks[0][0] = 4;
                this.bricks[0][1] = -1;
                this.bricks[1][0] = 5;
                this.bricks[1][1] = -1;
                this.bricks[2][0] = 5;
                this.bricks[2][1] = -2;
                this.bricks[3][0] = 6;
                this.bricks[3][1] = -2;
                this.brickType = 4;
                break;
            case 5:
                this.bricks[0][0] = 4;
                this.bricks[0][1] = -1;
                this.bricks[1][0] = 5;
                this.bricks[1][1] = -1;
                this.bricks[2][0] = 6;
                this.bricks[2][1] = -1;
                this.bricks[3][0] = 5;
                this.bricks[3][1] = -2;
                this.brickType = 5;
                break;
            case 6:
                this.bricks[0][0] = 3;
                this.bricks[0][1] = -1;
                this.bricks[1][0] = 4;
                this.bricks[1][1] = -1;
                this.bricks[2][0] = 5;
                this.bricks[2][1] = -1;
                this.bricks[3][0] = 6;
                this.bricks[3][1] = -1;
                this.brickType = 6;
                break;
        }
    }
    
    public boolean moveDown() {
        boolean build = false;

        for (int[] brick : bricks) {
            if (brick[1] == 19 || playSpace.isBrickAtCoordinates(brick[0], brick[1]+1)) {
                build = true;
                break;
            }
        }
        

        if (!build) {
            for (int[] brick : bricks) {
                brick[1]++;
            }
            return true;
        }

        
        for (int[] brick : bricks) {
            if (brick[1] >= 0) playSpace.setBrickAtCoordinates(brick[0], brick[1], true);
        }

        playSpace.increaseScore(4);
        playSpace.increaseSpeed();
        
        int clearedLines = playSpace.removeFullLines();

        if (clearedLines > 0) {
            playSpace.increaseScore(10*clearedLines);
            playSpace.increaseLines(clearedLines);
            fullLineSound.play();
        } else {
            putBrickSound.play();
        }

        for (int[] brick : bricks) {
            if (brick[1] < 0) {
                this.failSound.play();
                playSpace.gameOver();
                break;
            }
        }

        this.setNew();

        return false;
    }
    
    public void playStartSound() {
        fullLineSound.play();
    }
    
    public void put() {
        boolean move = true;
        while (move) {
            move = this.moveDown();
        }
    }
    
    public void moveHorizontal(boolean left) {
        int direction = 1;
        if (left) direction = -1;

        boolean canMove = true;

        for (int[] brick : bricks) {
            if (brick[0]+direction > 9 || brick[0]+direction < 0 || playSpace.isBrickAtCoordinates(brick[0]+direction, brick[1])) {
                canMove = false;
                break;
            }
        }

        if (canMove) {
            for (int[] brick : bricks) {
                brick[0] += direction;
            }
        }
    }
    
    public void rotate() {
        if (this.canRotate()) {
            switch (this.brickType) {
                case 1: // L
                    switch (this.rotationState) {
                        case 0:
                            this.bricks[0][0] += 1; this.bricks[0][1] -= 1; this.bricks[2][0] -= 1; this.bricks[2][1] += 1; this.bricks[3][1] += 2;
                            break;
                        case 1:
                            this.bricks[0][0] += 1; this.bricks[0][1] += 1; this.bricks[2][0] -= 1; this.bricks[2][1] -= 1; this.bricks[3][0] -= 2;
                            break;
                        case 2:
                            this.bricks[0][0] -= 1; this.bricks[0][1] += 1; this.bricks[2][0] += 1; this.bricks[2][1] -= 1; this.bricks[3][1] -= 2;
                            break;
                        case 3:
                            this.bricks[0][0] -= 1; this.bricks[0][1] -= 1; this.bricks[2][0] += 1; this.bricks[2][1] += 1; this.bricks[3][0] += 2;
                            break;
                    }
                    break;
                case 2: // L - inverted
                    switch (this.rotationState) {
                        case 0:
                            this.bricks[0][0] += 1; this.bricks[0][1] -= 1; this.bricks[2][0] -= 1; this.bricks[2][1] += 1; this.bricks[3][0] += 2;
                            break;
                        case 1:
                            this.bricks[0][0] += 1; this.bricks[0][1] += 1; this.bricks[2][0] -= 1; this.bricks[2][1] -= 1; this.bricks[3][1] += 2;
                            break;
                        case 2:
                            this.bricks[0][0] -= 1; this.bricks[0][1] += 1; this.bricks[2][0] += 1; this.bricks[2][1] -= 1; this.bricks[3][0] -= 2;
                            break;
                        case 3:
                            this.bricks[0][0] -= 1; this.bricks[0][1] -= 1; this.bricks[2][0] += 1; this.bricks[2][1] += 1; this.bricks[3][1] -= 2;
                            break;
                    }
                    break;
                case 3: // Z
                    switch (this.rotationState) {
                        case 0:
                            this.bricks[0][0] += 1; this.bricks[0][1] -= 1; this.bricks[2][0] -= 1; this.bricks[2][1] -= 1; this.bricks[3][0] -= 2;
                            break;
                        case 1:
                            this.bricks[0][0] += 1; this.bricks[0][1] += 1; this.bricks[2][0] += 1; this.bricks[2][1] -= 1; this.bricks[3][1] -= 2;
                            break;
                        case 2:
                            this.bricks[0][0] -= 1; this.bricks[0][1] += 1; this.bricks[2][0] += 1; this.bricks[2][1] += 1; this.bricks[3][0] += 2;
                            break;
                        case 3:
                            this.bricks[0][0] -= 1; this.bricks[0][1] -= 1; this.bricks[2][0] -= 1; this.bricks[2][1] += 1; this.bricks[3][1] += 2;
                            break;
                    }
                    break;
                case 4: // Z - inverted
                    switch (this.rotationState) {
                        case 0:
                            this.bricks[0][1] -= 2; this.bricks[1][0] -= 1; this.bricks[1][1] -= 1; this.bricks[3][0] -= 1; this.bricks[3][1] += 1;
                            break;
                        case 1:
                            this.bricks[0][0] += 2; this.bricks[1][0] += 1; this.bricks[1][1] -= 1; this.bricks[3][0] -= 1; this.bricks[3][1] -= 1;
                            break;
                        case 2:
                            this.bricks[0][1] += 2; this.bricks[1][0] += 1; this.bricks[1][1] += 1; this.bricks[3][0] += 1; this.bricks[3][1] -= 1;
                            break;
                        case 3:
                            this.bricks[0][0] -= 2; this.bricks[1][0] -= 1; this.bricks[1][1] += 1; this.bricks[3][0] += 1; this.bricks[3][1] += 1;
                            break;
                    }
                    break;
                case 5: // T
                    switch (this.rotationState) {
                        case 0:
                            this.bricks[0][1] -= 2; this.bricks[1][0] -= 1; this.bricks[1][1] -= 1; this.bricks[2][0] -= 2;
                            break;
                        case 1:
                            this.bricks[0][0] += 2; this.bricks[1][0] += 1; this.bricks[1][1] -= 1; this.bricks[2][1] -= 2;
                            break;
                        case 2:
                            this.bricks[0][1] += 2; this.bricks[1][0] += 1; this.bricks[1][1] += 1; this.bricks[2][0] += 2;
                            break;
                        case 3:
                            this.bricks[0][0] -= 2; this.bricks[1][0] -= 1; this.bricks[1][1] += 1; this.bricks[2][1] += 2;
                            break;
                    }
                    break;
                case 6: // I
                    switch (this.rotationState) {
                        case 0:
                            this.bricks[0][0] += 1; this.bricks[0][1] -= 2; this.bricks[1][1] -= 1; this.bricks[2][0] -= 1; this.bricks[3][0] -= 2; this.bricks[3][1] += 1;
                            break;
                        case 1:
                            this.bricks[0][0] += 2; this.bricks[0][1] += 1; this.bricks[1][0] += 1; this.bricks[2][1] -= 1; this.bricks[3][0] -= 1; this.bricks[3][1] -= 2;
                            break;
                        case 2:
                            this.bricks[0][0] -= 1; this.bricks[0][1] += 2; this.bricks[1][1] += 1; this.bricks[2][0] += 1; this.bricks[3][0] += 2; this.bricks[3][1] -= 1;
                            break;
                        case 3:
                            this.bricks[0][0] -= 2; this.bricks[0][1] -= 1; this.bricks[1][0] -= 1; this.bricks[2][1] += 1; this.bricks[3][0] += 1; this.bricks[3][1] += 2;
                            break;
                    }
                    break;
            }

            if (this.rotationState == 3) {
                this.rotationState = 0;
            } else {
                this.rotationState++;
            }
        }

    }
    
    private boolean canRotate() {
        switch(this.brickType) {
            case 0:
                return true;
            case 1: // L
                switch (this.rotationState) {
                    case 0:
                        if (this.bricks[2][1]+1 <= 19 && this.bricks[3][1]+2 <= 19 && this.bricks[0][0]+1 <= 9 && this.bricks[2][0]-1 >= 0 && !this.playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0], this.bricks[3][1]+2)) {
                            return true;
                        }
                        break;
                    case 1:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[0][0]+1 <= 9 && this.bricks[2][0]-1 >= 0 && this.bricks[3][0]-2 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-2, this.bricks[3][1])) {
                            return true;
                        }
                        break;
                    case 2:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0], this.bricks[3][1]-2)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (this.bricks[2][1]+1 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]+1 <= 9 && this.bricks[3][0]+2 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+2, this.bricks[3][1])) {
                            return true;
                        }
                        break;
                }
                break;
            case 2: // L - inverted
                switch (this.rotationState) {
                    case 0:
                        if (this.bricks[2][1]+1 <= 19 && this.bricks[0][0]+1 <= 9 && this.bricks[2][0]-1 >= 0 && this.bricks[3][0]+2 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+2, this.bricks[3][1])) {
                            return true;
                        }
                        break;
                    case 1:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[3][1]+2 <= 19 && this.bricks[0][0]+1 <= 9 && this.bricks[2][0]-1 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0], this.bricks[3][1]+2)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]+1 <= 9 && this.bricks[3][0]-2 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-2, this.bricks[3][1])) {
                            return true;
                        }
                        break;
                    case 3:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[2][1]+1 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0], this.bricks[3][1]-2)) {
                            return true;
                        }
                        break;
                }
                break;
            case 3: // Z
                switch (this.rotationState) {
                    case 0:
                        if (this.bricks[0][0]+1 <= 9 && this.bricks[2][0]-1 >= 0 && this.bricks[3][0]-2 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-2, this.bricks[3][1])) {
                            return true;
                        }
                        break;
                    case 1:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[0][0]+1 <= 9 && this.bricks[2][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0], this.bricks[3][1]-2)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[2][1]+1 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]+1 <= 9 && this.bricks[3][0]+2 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+2, this.bricks[3][1])) {
                            return true;
                        }
                        break;
                    case 3:
                        if (this.bricks[2][1]+1 <= 19 && this.bricks[3][1]+2 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]-1 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0], this.bricks[3][1]+2)) {
                            return true;
                        }
                        break;
                }
                break;
            case 4: // Z - inverted
                switch (this.rotationState) {
                    case 0:
                        if (this.bricks[3][1]+1 <= 19 && this.bricks[1][0]-1 >= 0 && this.bricks[3][0]-1 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0], this.bricks[0][1]-2) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]-1, this.bricks[1][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-1, this.bricks[3][1]+1)) {
                            return true;
                        }
                        break;
                    case 1:
                        if (this.bricks[0][0]+2 <= 9 && this.bricks[1][0]+1 <= 9 && this.bricks[3][0]-1 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+2, this.bricks[0][1]) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]+1, this.bricks[1][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-1, this.bricks[3][1]-1)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (this.bricks[0][1]+2 <= 19 && this.bricks[1][1]+1 <= 19 && this.bricks[1][0]+1 <= 9 && this.bricks[3][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0], this.bricks[0][1]+2) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]+1, this.bricks[1][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+1, this.bricks[3][1]-1)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (this.bricks[1][1]+1 <= 19 && this.bricks[3][1]+1 <= 19 && this.bricks[0][0]-2 >= 0 && this.bricks[1][0]-1 >= 0 && this.bricks[3][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-2, this.bricks[0][1]) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]-1, this.bricks[1][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+1, this.bricks[3][1]+1)) {
                            return true;
                        }
                        break;
                }
                break;
            case 5: // T
                switch (this.rotationState) {
                    case 0:
                        if (this.bricks[1][0]-1 >= 0 && this.bricks[2][0]-2 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0], this.bricks[0][1]-2) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]-1, this.bricks[1][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-2, this.bricks[2][1])) {
                            return true;
                        }
                        break;
                    case 1:
                        if (this.bricks[0][0]+2 <= 9 && this.bricks[1][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+2, this.bricks[0][1]) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]+1, this.bricks[1][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0], this.bricks[2][1]-2)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (this.bricks[0][1]+2 <= 19 && this.bricks[1][1]+1 <= 19 && this.bricks[1][0]+1 <= 9 && this.bricks[2][0]+2 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0], this.bricks[0][1]+2) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]+1, this.bricks[1][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+2, this.bricks[2][1])) {
                            return true;
                        }
                        break;
                    case 3:
                        if (this.bricks[1][1]+1 <= 19 && this.bricks[2][1]+2 <= 19 && this.bricks[0][0]-2 >= 0 && this.bricks[1][0]-1 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-2, this.bricks[0][1]) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]-1, this.bricks[1][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0], this.bricks[2][1]+2)) {
                            return true;
                        }
                        break;
                }
                break;
            case 6: // I
                switch (this.rotationState) {
                    case 0:
                        if (this.bricks[3][1]+1 <= 19 && this.bricks[0][0]+1 <= 9 && this.bricks[2][0]-1 >= 0 && this.bricks[3][0]-2 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+1, this.bricks[0][1]-2) && !playSpace.isBrickAtCoordinates(this.bricks[1][0], this.bricks[1][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]-1, this.bricks[2][1]) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-2, this.bricks[3][1]+1)) {
                            return true;
                        }
                        break;
                    case 1:
                        if (this.bricks[0][1]+1 <= 19 && this.bricks[0][0]+2 <= 9 && this.bricks[1][0]+1 <= 9 && this.bricks[3][0]-1 >= 0 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]+2, this.bricks[0][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]+1, this.bricks[1][1]) && !playSpace.isBrickAtCoordinates(this.bricks[2][0], this.bricks[2][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]-1, this.bricks[3][1]-2)) {
                            return true;
                        }
                        break;
                    case 2:
                        if (this.bricks[0][1]+2 <= 19 && this.bricks[1][1]+1 <= 19 && this.bricks[0][0]-1 >= 0 && this.bricks[2][0]+1 <= 9 && this.bricks[3][0]+2 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-1, this.bricks[0][1]+2) && !playSpace.isBrickAtCoordinates(this.bricks[1][0], this.bricks[1][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[2][0]+1, this.bricks[2][1]) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+2, this.bricks[3][1]-1)) {
                            return true;
                        }
                        break;
                    case 3:
                        if (this.bricks[2][1]+1 <= 19 && this.bricks[3][1]+2 <= 19 && this.bricks[0][0]-2 >= 0 && this.bricks[1][0]-1 >= 0 && this.bricks[3][0]+1 <= 9 && !playSpace.isBrickAtCoordinates(this.bricks[0][0]-2, this.bricks[0][1]-1) && !playSpace.isBrickAtCoordinates(this.bricks[1][0]-1, this.bricks[1][1]) && !playSpace.isBrickAtCoordinates(this.bricks[2][0], this.bricks[2][1]+1) && !playSpace.isBrickAtCoordinates(this.bricks[3][0]+1, this.bricks[3][1]+2)) {
                            return true;
                        }
                        break;
                }
                break;
            default:
                return false;
        }
        return false;
    }
}
