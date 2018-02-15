package engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
	private final int SIZE = 320;
	private final int DOT_SIZE = 16;
	private final int ALL_DOTS = 400;
	private Image dot;
	private Image apple;
	private int appleX;
	private int appleY;
	private int[] x = new int[ALL_DOTS];
	private int[] y = new int[ALL_DOTS];
	private int dots;
	private Timer timer;
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;

	public GameField(){
		setBackground(Color.BLACK);
		loadImages();
		initGame();
		addKeyListener(new FieldKeyListener());
		setFocusable(true);
	}

	public void initGame(){
		dots = 3;
		for (int i = 0; i < dots; i++) {
			x[i] = 48 - i * DOT_SIZE;
			y[i] = 48;
		}
		timer = new Timer(250, this);
		timer.start();
		createApple();
	}

	private void createApple() {
		appleX = new Random().nextInt(20)*DOT_SIZE;
		appleY = new Random().nextInt(20)*DOT_SIZE;
	}

	public void loadImages(){
		ImageIcon imageIconApple = new ImageIcon("apple.png");
		apple = imageIconApple.getImage();
		ImageIcon imageIconDot = new ImageIcon("dot.png");
		dot = imageIconDot.getImage();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (inGame){
			g.drawImage(apple, appleX, appleY, this);
			for (int i = 0; i < dots; i++) {
				g.drawImage(dot, x[i], y[i], this);
			}
		}else {
			String gameOver = "Game Over!!!";
			g.setColor(Color.WHITE);
			g.drawString(gameOver, 150, SIZE/2);
		}
	}

	public void moveSnack(){
		for (int i = dots; i >0 ; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}

		if (left){
			x[0] -= DOT_SIZE;
		}
		if (right){
			x[0] += DOT_SIZE;
		}
		if (up){
			y[0] -= DOT_SIZE;
		}
		if (down) {
			y[0] += DOT_SIZE;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(inGame){
			checkApple();
			checkCollisionsBorder();
			moveSnack();
		}
		repaint();
	}

	private void checkCollisionsBorder() {

		// столкновение с собой
		for (int i = dots; i > 0 ; i--) {
			if (i > 4 && x[0] == x[i] && y[0] == y[i]){
				inGame = false;
			}
		}

		// столкновение с бордюром поля
		if(x[0] > SIZE){
			inGame = false;
		}
		if(x[0] < 0 ){
			inGame = false;
		}
		if(y[0] > SIZE){
			inGame = false;
		}
		if(y[0] < 0){
			inGame = false;
		}

	}

	private void checkApple() {
		if (x[0] == appleX && y[0] == appleY){
			dots++;
			createApple();
		}
	}

	class FieldKeyListener extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_LEFT && !right){
				left = true;
				up = false;
				down = false;
			}

			if (key == KeyEvent.VK_RIGHT && !left){
				right = true;
				up = false;
				down = false;
			}

			if (key == KeyEvent.VK_UP && !down){
				left = false;
				right = false;
				up = true;
			}

			if (key == KeyEvent.VK_DOWN && !up){
				left = false;
				right = false;
				down = true;
			}


		}
	}
}