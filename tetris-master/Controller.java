package application;


import javafx.scene.shape.Rectangle;

public class Controller {
	// Getting the numbers and the MESH from Tetris
	public static final int MOVE = Tetris.MOVE;//25
	public static final int SIZE = Tetris.SIZE;//25
	public static int XMAX = Tetris.XMAX;//12*25
	public static int YMAX = Tetris.YMAX;//24*25
	public static int[][] MESH = Tetris.MESH;//[12][24]

	public static void MoveRight(Form form) {
		if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
				&& form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
			int movea = MESH[((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
			int moveb = MESH[((int) form.b.getX() / SIZE) + 1][((int) form.b.getY() / SIZE)];
			int movec = MESH[((int) form.c.getX() / SIZE) + 1][((int) form.c.getY() / SIZE)];
			int moved = MESH[((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				form.a.setX(form.a.getX() + MOVE);
				form.b.setX(form.b.getX() + MOVE);
				form.c.setX(form.c.getX() + MOVE);
				form.d.setX(form.d.getX() + MOVE);
			}
		}
	}

	public static void MoveLeft(Form form) {
		if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
				&& form.d.getX() - MOVE >= 0) {
			int movea = MESH[((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
			int moveb = MESH[((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
			int movec = MESH[((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
			int moved = MESH[((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];
			if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
				form.a.setX(form.a.getX() - MOVE);
				form.b.setX(form.b.getX() - MOVE);
				form.c.setX(form.c.getX() - MOVE);
				form.d.setX(form.d.getX() - MOVE);
			}
		}
	}

	public static Form makeRect() {
		int block = (int) (Math.random() * 100);
		String name;
		Rectangle a = new Rectangle(SIZE-1, SIZE-1), b = new Rectangle(SIZE-1, SIZE-1), c = new Rectangle(SIZE-1, SIZE-1),
				d = new Rectangle(SIZE-1, SIZE-1);
		if (block < 15) {
			a.setX(XMAX / 2 - SIZE);//125, y= 25
			b.setX(XMAX / 2 - SIZE);//125
			b.setY(SIZE);//25
			c.setX(XMAX / 2);//12*25/2=150
			c.setY(SIZE);// 25
			d.setX(XMAX / 2 + SIZE);//150+25=175
			d.setY(SIZE);//25
			name = "j";
		} else if (block < 30) { //
			a.setX(XMAX / 2 + SIZE);// 175 y = 25
			b.setX(XMAX / 2 - SIZE);// 125
			b.setY(SIZE);//25
			c.setX(XMAX / 2);//150
			c.setY(SIZE);//25
			d.setX(XMAX / 2 + SIZE);//
			d.setY(SIZE);//25
			name = "l";
		} else if (block < 45) { 
			a.setX(XMAX / 2 - SIZE);//125, y =25
			b.setX(XMAX / 2);//150
			c.setX(XMAX / 2 - SIZE);//125
			c.setY(SIZE);//25
			d.setX(XMAX / 2);//150
			d.setY(SIZE);//25
			name = "o";
		} else if (block < 60) { 
			a.setX(XMAX / 2 + SIZE);//175
			b.setX(XMAX / 2);//150
			c.setX(XMAX / 2);//150
			c.setY(SIZE);
			d.setX(XMAX / 2 - SIZE);
			d.setY(SIZE);
			name = "s";
		} else if (block < 75) { 
			a.setX(XMAX / 2 - SIZE);
			b.setX(XMAX / 2);
			c.setX(XMAX / 2);
			c.setY(SIZE);
			d.setX(XMAX / 2 + SIZE);
			name = "t";
		} else if (block < 90) { 
			a.setX(XMAX / 2 + SIZE);
			b.setX(XMAX / 2);
			c.setX(XMAX / 2 + SIZE);
			c.setY(SIZE);
			d.setX(XMAX / 2 + SIZE + SIZE);
			d.setY(SIZE);
			name = "z";
		} else { 
			a.setX(XMAX / 2 - SIZE - SIZE);
			b.setX(XMAX / 2 - SIZE);
			c.setX(XMAX / 2);
			d.setX(XMAX / 2 + SIZE);
			name = "i";
		}
		return new Form(a, b, c, d, name);
	}
}