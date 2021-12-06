package br.com.dhentech.cm.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import br.com.dhentech.cm.model.Board;

public class PanelBoard extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelBoard(Board board) {
		setLayout(new GridLayout(board.getLines(), board.getColumns()));
		
		board.forEachFields( f -> add(new ButtonField(f)));
		
		board.addObservers(e -> {
			
			SwingUtilities.invokeLater(() -> {
				if(e.isWinner()) {
					JOptionPane.showMessageDialog(this, "You win, loser!!");
				} else {
					JOptionPane.showMessageDialog(this, "You loser, loser!!");
				}
				
				board.resetGame();
			});
		});
	}

}
