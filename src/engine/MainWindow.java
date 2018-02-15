package engine;

import javax.swing.*;

public class MainWindow extends JFrame {
	private final int SIZE = 376;

	public MainWindow(){
		setTitle("Змейка");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(SIZE, SIZE);
		setResizable(false);
		setLocation(400, 400);
		add(new GameField());
		setVisible(true);
	}

	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
	}
}
