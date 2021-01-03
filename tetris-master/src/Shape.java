import java.awt.Color;
import java.awt.Graphics;

public class Shape {
	
	public static final int BOARD_WIDTH =12; 
	public static final int BOARD_HEIGHT =20; 
	public static final int BLOCK_SIZE = 30;
	public ReadHighScore readscore;
	
	private int x = 5;
	private int y = 0;
	private static double normal = 550;
	private double fast = 60;
	private double delayTimeForBlockSpeed = normal;
	private long startTime;
	private int sideWayMove = 0;
	private boolean collision = false;
	private int[][] coords;
	private Board board;
	private Color color;
	
	public Shape(int[][] coords,Board board, Color color)
	{
		this.coords = coords;
		this.board = board;
		this.color = color;
	}
	
	public double getFast()
	{
		return fast;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	public int getY()
	{
		return y;
	}
	public int [][] getCoords(){
		return coords;
	}
	public void reset()
	{
		this.x = 4;
		this.y = 0;
		collision = false;
	}
	public void update() {

		//draw color for board		
		if(collision)
		{
			for(int row = 0; row < coords.length;row++)
			{
				for(int col = 0; col < coords[0].length;col++)
				{
					if(coords[row][col] != 0)
					{
						board.getBoard()[y + row][x + col] = color; 
					}
				}
			}
			checkLine();
			board.addScoreShape();
			board.setChooseShape();
			
			return;
		}
		//horizontal movement check collision
		boolean xMove = true;
		if(x + sideWayMove + coords[0].length <= BOARD_WIDTH && x + sideWayMove >= 0) {
			for(int row = 0; row < coords.length;row++) {
				for(int col = 0; col < coords[row].length;col++)
				{
					if(coords[row][col] != 0 ) {
					
						if(board.getBoard()[y + row][x + sideWayMove + col] != null) {
						xMove = false;
					}
				}
			}
		}
			if(xMove) {
			x += sideWayMove;
			}
		}
		
		sideWayMove = 0;
		
		
		if(System.currentTimeMillis() - startTime > delayTimeForBlockSpeed)
		{		
			
			//vertical movement check collision
			if(!(y + coords.length + 1 > BOARD_HEIGHT)) {
				for( int row = 0; row <coords.length;row++)
				{
					for(int col = 0; col < coords[row].length;col++)
					{
						if(coords[row][col] != 0)
						{
							if(board.getBoard()[y + 1 + row][x + sideWayMove + col] != null)
							{
								
								collision = true;
								
								
							}
						}
					}
				}
				if(!collision) {
				y++;
			}
		}
			else
			{
				collision = true;
					
			}
			
			startTime = System.currentTimeMillis();
		}
	}
	
	//check bottom line collision
	private void checkLine() {
			
		int bottomLine = board.getBoard().length - 1;
		
		for(int topLine = board.getBoard().length - 1; topLine > 0; topLine-- )
		{
			int count = 0;
			
			for(int col = 0; col < board.getBoard()[0].length;col++)
			{
				if(board.getBoard()[topLine][col] != null)
				{
					count++;
					
				}
	
				board.getBoard()[bottomLine][col] = board.getBoard()[topLine][col];
			}
			
			if(count < board.getBoard()[0].length)
			
			{
				
				bottomLine--;
				
			}
			
			if(count >= board.getBoard()[0].length)
			{
				board.addScore();
				
			}
			
		}	
		
		
	}
	public void rotateShape() {
		
		int[][] rotatedShape = transposeMatrix(coords);
		reverseRows(rotatedShape);
		
		//check rotate borders	
		
		if((x + rotatedShape[0].length > Board.BOARD_WIDTH) || (y + rotatedShape.length > Board.BOARD_HEIGHT))
		{
			return;
		}
		
		//check rotate collision shapes
		
		for(int row = 0; row < rotatedShape.length;row++)
		{
			for(int col = 0; col < rotatedShape[row].length;col++)
			{
				if(rotatedShape[row][col] != 0)
				{
					if(board.getBoard()[y + row][x + col] != null)
					{
						return;
					}
				}
			}
		}
		
		coords = rotatedShape;
		
	}
	//hold shape for rotation
	private int[][] transposeMatrix(int[][] matrix)
	{
		int[][] temp = new int[matrix[0].length][matrix.length];
		
		for(int row = 0; row < matrix.length;row++)
		{
			
			for(int col = 0;col < matrix[0].length;col++)
			{
				temp[col][row] = matrix[row][col];
			}
		}
		return temp;
	}
	
	//Reverse the shape
	private void reverseRows(int[][] matrix) {
		int middle = matrix.length/2;
		
		for(int row = 0; row < middle; row++)
		{
			int[] temp = matrix[row];
			
			matrix[row] = matrix[matrix.length - row -1];
			
			matrix[matrix.length - row -1] = temp;
			
		}
	}

	//Draw shape
	public void renderShape(Graphics g) {
		for(int i = 0; i < coords.length;i++)
		{
			for(int j = 0; j < coords[0].length;j++)
			{
				if(coords[i][j] != 0) {
						
					g.setColor(color);
						
					g.fillRect(j * BLOCK_SIZE + x * BLOCK_SIZE, i * BLOCK_SIZE + y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
	}
	
	public double getSpeed()
	{
		return normal;
	}
	
	public void setSpeed(double n)
	{
		this.normal = n;
	}
	public void speedUp()
	{
		delayTimeForBlockSpeed = fast;
	}
	public void speedDown()
	{
		delayTimeForBlockSpeed = normal;
	}

	public void moveRight() {
		sideWayMove++;
	}
	public void moveLeft() {
		sideWayMove--;
	}
}
