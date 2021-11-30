package br.com.dhentech.cm.view;

import br.com.dhentech.cm.model.Board;

public class Temp {

	public static void main(String[] args) {

		Board board = new Board(3, 3, 9);
		
		board.addObservers(e -> {
			if(e.isWinner()) {
				System.out.println("Ganhou");
			} else {
				System.out.println("Perdeu");
			}
		});

		board.changeMarking(0, 0);
		board.changeMarking(0, 1);
		board.changeMarking(0, 2);
		board.changeMarking(1, 0);
		board.changeMarking(1, 1);
		board.changeMarking(1, 2);
		board.changeMarking(2, 0);
		board.changeMarking(2, 1);
		board.open(2, 2);
//		board.changeMarking(2, 2);

	}

}
