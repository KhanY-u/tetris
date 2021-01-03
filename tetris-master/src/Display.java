 
import java.awt.Color;
import java.awt.Container;

import javax.swing.*;

public class Display {
	public static final int width = 650, height = 637;
	private JFrame window;
	private Board board;
	private Title title;
	
	public static int getWidth() {
		return width;
	}
	public static int getHeight() {
		return height;
	}
	
	public Display()
	{
		window = new JFrame("Tetris");
		window.setSize(width,height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);

		board = new Board();

		title = new Title(this);
		window.addKeyListener(board);
		window.addMouseMotionListener(title);
		window.addMouseListener(title);
		window.add(title);

		BGM();

		window.setVisible(true);
	}

	public void startTetris() {
		window.remove(title);
		// window.addMouseMotionListener(board);
		// window.addMouseListener(board);
		window.add(board);
		window.addKeyListener(board);
		board.startGame();
		window.revalidate();
	}
	
	public void BGM()
	{
		if(board.getState() == 0)
		{
			Sound.playMusic();
		}
	}
}