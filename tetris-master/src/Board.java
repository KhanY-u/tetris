import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.*;

public class Board extends JPanel implements KeyListener {
	
	public static final int BOARD_WIDTH =12; 
	public static final int BOARD_HEIGHT =20; 
	public static final int BLOCK_SIZE = 30;
	public static ReadHighScore readscore;
	
	public static int STATE_GAME_PLAY = 0;
	public static int STATE_GAME_OVER = 2;
	public static int STATE_GAME_PAUSE = 1;
	private static int score ;
	private int numbershape = 1;
	private int state = STATE_GAME_PLAY;
	private Sound sound;
	
	private static final int FPS = 60;
	private static int delay = FPS/1000;
	
	private Timer looper;
	private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
	private File highestScore = new File("highscore.txt");
	private PrintWriter writer;
	private Scanner scanner ;
	private boolean highscoreCheck = false;
	private Random random;
	
	private Color[] colors = {Color.decode("#ed1c24"), Color.decode("#ff7f27"), Color.decode("#fff200"), 
	        Color.decode("#22b14c"), Color.decode("#00a2e8"), Color.decode("#a349a4"), Color.decode("#3f48cc")};
	//shapes
	private Shape[] shapes = new Shape[7];
	private Shape chooseShape;
	
	public Board(){
		
		random = new Random();
		
		//Shapes
		
		shapes[0] = new Shape(new int[][]{
			{1, 1},
	        {1, 1}, 
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 1, 0}, 
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
            {1, 1, 1},
            {1, 0, 0}, 
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
            {1, 1, 1},
            {0, 0, 1}, 
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
        	{1, 1, 0},
            {0, 1, 1}, 
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
        	 {0, 1, 1},
             {1, 1, 0},  
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
        	{1, 1, 1, 1} 
        }, this, colors[6]);
        
        sound= new Sound();
        chooseShape = shapes[random.nextInt(shapes.length)];
        
        //TIMER or ticker
		looper = new Timer(delay, new ActionListener(){
			
			@Override  
			public void actionPerformed(ActionEvent e) {
				//check collision
				update();
				repaint();
			}
	});
		looper.start();
	
}	
	public void update() {
		if(state == STATE_GAME_PLAY) {
			
			chooseShape.update();
			checkSpeedUp();
		}
	}
	
	public void setChooseShape() {
		chooseShape = shapes[random.nextInt(shapes.length)];
		chooseShape.reset();
		checkOverGame();
	}
	
	private void checkOverGame() {
		int [][] coords = chooseShape.getCoords();
		for(int row = 0; row < coords.length;row++)
		{
			for(int col = 0; col < coords[0].length;col++)
			{
				if(coords[row][col] != 0)
				{
					if(board[row + chooseShape.getY()][col + chooseShape.getX()] != null)
					{
						writeHighScore();
						state = STATE_GAME_OVER;			
					}
				}
			}
		}
		if(state == STATE_GAME_OVER)
		{
			addsoundgameover();
			chooseShape.setSpeed(550);
		}
	}
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		chooseShape.renderShape(g);
		for(int row = 0; row < board.length; row++)
		{
			for( int col = 0; col < board[row].length; col++)
			{
				if(board[row][col] != null)
				{
					g.setColor(board[row][col]);
					g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
		
		//draw things to board
		g.setColor(Color.white);
		
		for(int col = 0; col < BOARD_WIDTH +1;col++)
		{
			if(col == BOARD_WIDTH || col == 0)
			{
			
				g.drawLine(col*BLOCK_SIZE, 0, col* BLOCK_SIZE,BOARD_HEIGHT*BLOCK_SIZE);
			
			}
			
		}
		//Score board;
		g.setColor(Color.WHITE);
		g.drawRect(380, 0, 250 , 150);
		
		//Score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 25));
		g.drawString("SCORE: ", 385, 30);
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString(ToString(), 490, 30);
		
		//Highscore Board
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.drawString("HIGHSCORE: ", 385, 110);
		g.drawString(Integer.toString(ReadHighScore.getScore()), 540, 110);
		
		//Status
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 25));
		g.drawString("STATUS: ", 385, 70);
		
		//Game over status
		if(state == STATE_GAME_OVER)
		{
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.BOLD, 30));
		g.drawString("GAMER OVER", 70, 100);
		
		}
		
		//Game on status
		if(state == STATE_GAME_PLAY)
		{
			
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial Black", Font.BOLD, 25));
			g.drawString("ON", 500, 70);
		}
		//Pause status
		if(state == STATE_GAME_PAUSE)
		{
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial Black", Font.BOLD, 25));
		g.drawString("PAUSE", 435, 70);
		
		}
		
		//New high-score announce
		
		if(highscoreCheck == true)
		{
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Arial Black", Font.BOLD, 20));
			g.drawString("NEW HIGHSCORE !!!",80,120);
			
		}
			
	}
	
	public void writeHighScore() {
		if(state == STATE_GAME_OVER || state == STATE_GAME_PLAY)
		{
			try {
				scanner = new Scanner(highestScore);
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(scanner.nextInt() < score)
			{
				try {
					writer = new PrintWriter(highestScore);
					writer.print(score);
					writer.close();
					highscoreCheck = true;
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			scanner.close();
		}
				
	}
	
	public Color[][] getBoard(){
		return board;
		
	}
	
	public int getScore() {
		return score;
	}
	
	public int getNumberShape() {
		return numbershape;
	}
	
	public void addScoreShape() {
		score = score + numbershape;
	}
	
	public void addScore()
	{
		addsoundline();
		score = score + 100;
		
	}
	
	public int getState() {
		return state;
	}
	
	//Sound effects
	public void addsoundmove() {
		sound.playSoundEffect("move.wav");
	}
	public void addsounddown() 
	{
		sound.playSoundEffect("land.wav");
	}
	public void addsoundgameover() 
	{
		sound.playSoundEffect("gameover.wav");
	}
	public void addsoundpause() 
	{
		sound.playSoundEffect("pause.wav");
	}
	public void addsoundline() 
	{
		sound.playSoundEffect("line.wav");
	}
	public void addsoundrotate() 
	{
		sound.playSoundEffect("rotate.wav");
	}

	//Controls
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			addsoundmove();
			chooseShape.moveRight();
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			addsoundmove();
			chooseShape.moveLeft();
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			addsoundrotate();
			chooseShape.rotateShape();
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			chooseShape.speedUp();
		}
		
		if(state == STATE_GAME_OVER || state == STATE_GAME_PLAY)
		{
			if(e.getKeyCode() == KeyEvent.VK_R)
			{
				for(int row = 0; row < board.length; row++)
				{
					for( int col = 0; col < board[row].length; col++)
					{
						board[row][col] = null;
						score = 0;
						chooseShape.setSpeed(550);
					}
					
					highscoreCheck = false;
				}
				setChooseShape();
				state = STATE_GAME_PLAY;
			}
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_P)
		{
			addsoundpause();
			if(state == STATE_GAME_PLAY)
			{
				
				state = STATE_GAME_PAUSE;
			
			}
			else if(state == STATE_GAME_PAUSE)
			{
				state = STATE_GAME_PLAY;
			}
		
			} 	
	}
	@Override
	public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				
				chooseShape.speedDown();	
			}
			if(chooseShape.getFast() == 60)
			{
				chooseShape.speedDown();
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	public String ToString() {
		return score + "";
	}
	
	public void checkSpeedUp() {
		
		if(score > 300 )
		{
			chooseShape.setSpeed(410);
		}
		if(score > 800)
		{
			chooseShape.setSpeed(310);
		}
		if(score > 1000)
		{
			chooseShape.setSpeed(200);
		}
		if(score > 1300)
		{
			chooseShape.setSpeed(120);
		}
		if(score > 1500)
		{
			chooseShape.setSpeed(90);
		}
	}
	  public void startGame()
	    {
	        System.out.println("completed");
	    }

}

