package br.com.dhentech.cm.model;

public class ResultEvent {

	private final Boolean winner;

	public ResultEvent(Boolean winner) {
		this.winner = winner;
	}

	public Boolean isWinner() {
		return winner;
	}

}
