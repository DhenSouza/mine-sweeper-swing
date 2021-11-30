package br.com.dhentech.cm.view;

import javax.swing.JFrame;

import br.com.dhentech.cm.model.Board;

public class MainScreen extends JFrame {
	private static final long serialVersionUID = 1L;

	public MainScreen() {

		Board board = new Board(16, 30, 50);
		add(new PanelBoard(board));

		setTitle("Campo Minado");
		setSize(690, 438);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainScreen();
	}

}
