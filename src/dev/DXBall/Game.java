package dev.DXBall;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener{
    
	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	private int delay = 8;
	
	private int playerX = 360;
	
	private int ballposX = 300;
	private int ballposY = 400;
	
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Game() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
		
	}
	public void paint(Graphics g) {
	    //background
		g.setColor(Color.black);
		g.fillRect(1,1, 692, 592);
		
		//drawing map
		
		map.draw((Graphics2D)g);
		
		// borders need to be fixed
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		//g.fillRect(0, 0, 600, 3); this border is not essential
		g.fillRect(682, 0, 3, 592);
		
		//the paddle
		
		g.setColor(Color.ORANGE);
		g.fillRect(playerX, 550, 100, 8);
		
		// the ball
		g.setColor(Color.magenta);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		//g.dispose();
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			
			if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}
			
			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map.length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = (j * map.brickWidth) + 80;
						int brickY = (i * map.brickHeight ) + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
					  	
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);   
						Rectangle brickRect = rect;
						
						if (ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
							if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width){
								ballXdir = - ballXdir;
								
							} else {
								ballYdir = -ballYdir;
							}
							break A;
						}
					}
				}
			}
			ballposX += ballXdir ;
			ballposY += ballYdir ;
			
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			if(ballposY < 0) {
				ballYdir = -ballYdir;
				
			}
			if(ballposX > 670 ) {
				ballXdir = -ballXdir;
			}
			
		}
		repaint();
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()== KeyEvent.VK_RIGHT) {
			if (playerX >= 556) {
				  playerX = 556;
			}
		} else {
			moveRight();
		}
		
		
	   if (e.getKeyCode()== KeyEvent.VK_LEFT) {
		  if (playerX < 10) {
			  playerX = 10;
		  }
	   } else {
		   moveLeft();
	   }
    }
	
    public void moveRight() {
	   play = true;
	   playerX -= 22;
    }
    public void moveLeft() {
	   play = true;
	   playerX += 22;
    }
}
