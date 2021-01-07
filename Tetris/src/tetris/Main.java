package tetris;

import javax.swing.JFrame;

public class Main {
    public static final int WIDTH = 445, HEIGHT = 645;
    private Board board;
    private Title title;
    private JFrame main;
    public Main() {
        main = new JFrame("Tetris");
        main.setSize(WIDTH, HEIGHT);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        main.setResizable(false);
        board = new Board();
        title = new Title(this);
        main.addKeyListener(board);
        main.addKeyListener(title);
        main.add(title);
        main.setVisible(true);
    }
    public void startTetris() {
        main.remove(title);
        main.addMouseMotionListener(board);
        main.addMouseListener(board);
        main.add(board);
        board.startGame();
        main.revalidate();
    }

    public static void main(String[] args) {
        new Main();
    }

}
